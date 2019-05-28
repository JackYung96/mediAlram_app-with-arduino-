package com.example.jaegyengjo.medino;


import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;



import android.os.AsyncTask;

import com.example.jaegyengjo.medino.AlarmSet.Result;


public class arduinoSend extends AppCompatActivity {

    private final static int DEVICES_DIALOG = 1;
    private final static int ERROR_DIALOG = 2;

    public static Context mContext;
    public static AppCompatActivity activity;

    TextView myLabel, mRecv;
    EditText myTextbox;
    static BluetoothAdapter mBluetoothAdapter;
    BluetoothSocket mmSocket;
    BluetoothDevice mmDevice;
    OutputStream mmOutputStream;
    InputStream mmInputStream;
    Thread workerThread;
    byte[] readBuffer;
    int readBufferPosition;
    int counter;
    volatile boolean stopWorker;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.arduino_send);

        Button sendButton = (Button) findViewById(R.id.send);
        Button home = (Button)findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(arduinoSend.this, MainActivity.class);
                startActivity(intent);
            }
        });

        myLabel = (TextView) findViewById(R.id.label);
        myTextbox = (EditText) findViewById(R.id.entry);
        mRecv = (TextView) findViewById(R.id.recv);

        mContext = this;
        activity = this;

        myTextbox.setText(getPreferences());


//1.블루투스 사용 가능한지 검사합니다.
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            ErrorDialog("This device is not implement Bluetooth.");
            return;
        }

        if (!mBluetoothAdapter.isEnabled()) {
            ErrorDialog("This device is disabled Bluetooth.");
            return;
        } else
//2. 페어링 되어 있는 블루투스 장치들의 목록을 보여줍니다.
//3. 목록에서 블루투스 장치를 선택하면 선택한 디바이스를 인자로 하여 doConnect 함수가 호출됩니다.
            DeviceDialog();


//11. Send 버튼을 누르면 sendData함수가 호출됩니다.
        sendButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    sendData();
                } catch (IOException ex) {
                }
            }
        });

    }

    static public Set<BluetoothDevice> getPairedDevices() {
        return mBluetoothAdapter.getBondedDevices();
    }

    @Override
    public void onBackPressed() {
        doClose();
        super.onBackPressed();
    }


    //13. 백버튼이 눌러지거나, ConnectTask에서 예외발생시
//데이터 수신을 위한 스레드를 종료시키고 CloseTask를 실행하여 입출력 스트림을 닫고,
//소켓을 닫아 통신을 종료합니다.
    public void doClose() {
        workerThread.interrupt();
        new CloseTask().execute();
    }


    public void doConnect(BluetoothDevice device) {
        mmDevice = device;

//Standard SerialPortService ID
        UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");

        try {
// 4. 지정한 블루투스 장치에 대한 특정 UUID 서비스를 하기 위한 소켓을 생성합니다.
// 여기선 시리얼 통신을 위한 UUID를 지정하고 있습니다.
            mmSocket = mmDevice.createRfcommSocketToServiceRecord(uuid);
// 5. 블루투스 장치 검색을 중단합니다.
            mBluetoothAdapter.cancelDiscovery();
// 6. ConnectTask를 시작합니다.
            new ConnectTask().execute();
        } catch (IOException e) {
            Log.e("", e.toString(), e);
            ErrorDialog("doConnect " + e.toString());
        }
    }

    private class ConnectTask extends AsyncTask<Void, Void, Object> {
        @Override
        protected void onPreExecute() {

        }

        @Override
        protected Object doInBackground(Void... params) {
            try {
//7. 블루투스 장치로 연결을 시도합니다.
                mmSocket.connect();

//8. 소켓에 대한 입출력 스트림을 가져옵니다.
                mmOutputStream = mmSocket.getOutputStream();
                mmInputStream = mmSocket.getInputStream();

//9. 데이터 수신을 대기하기 위한 스레드를 생성하여 입력스트림로부터의 데이터를 대기하다가
// 들어오기 시작하면 버퍼에 저장합니다.
// '\n' 문자가 들어오면 지금까지 버퍼에 저장한 데이터를 UI에 출력하기 위해 핸들러를 사용합니다.
                beginListenForData();


            } catch (Throwable t) {
                Log.e("", "connect? " + t.getMessage());
                doClose();
                return t;
            }
            return null;
        }


        @Override
        protected void onPostExecute(Object result) {
//10. 블루투스 통신이 연결되었음을 화면에 출력합니다.
            myLabel.setText("Bluetooth Opened");
            if (result instanceof Throwable) {
                Log.d("", "ConnectTask " + result.toString());
                ErrorDialog("ConnectTask " + result.toString());

            }
        }
    }

    private class CloseTask extends AsyncTask<Void, Void, Object> {
        @Override
        protected Object doInBackground(Void... params) {
            try {
                try {
                    mmOutputStream.close();
                } catch (Throwable t) {/*ignore*/}
                try {
                    mmInputStream.close();
                } catch (Throwable t) {/*ignore*/}
                mmSocket.close();
            } catch (Throwable t) {
                return t;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object result) {
            if (result instanceof Throwable) {
                Log.e("", result.toString(), (Throwable) result);
                ErrorDialog(result.toString());
            }
        }
    }


    public void DeviceDialog() {
        if (activity.isFinishing()) return;

        FragmentManager fm = arduinoSend.this.getSupportFragmentManager();
        MyDialogFragment alertDialog = MyDialogFragment.newInstance(DEVICES_DIALOG, "");
        alertDialog.show(fm, "");
    }


    public void ErrorDialog(String text) {
        if (activity.isFinishing()) return;

        FragmentManager fm = arduinoSend.this.getSupportFragmentManager();
        MyDialogFragment alertDialog = MyDialogFragment.newInstance(ERROR_DIALOG, text);
        alertDialog.show(fm, "");
    }


    void beginListenForData() {
        final Handler handler = new Handler(Looper.getMainLooper());

        stopWorker = false;
        readBufferPosition = 0;
        readBuffer = new byte[1024];
        workerThread = new Thread(new Runnable() {
            public void run() {
                while (!Thread.currentThread().isInterrupted() && !stopWorker) {
                    try {
                        int bytesAvailable = mmInputStream.available();
                        if (bytesAvailable > 0) {
                            byte[] packetBytes = new byte[bytesAvailable];
                            mmInputStream.read(packetBytes);
                            for (int i = 0; i < bytesAvailable; i++) {
                                byte b = packetBytes[i];
                                if (b == '\n') {
                                    byte[] encodedBytes = new byte[readBufferPosition];
                                    System.arraycopy(readBuffer, 0, encodedBytes, 0, encodedBytes.length);
                                    final String data = new String(encodedBytes, "US-ASCII");

                                    readBufferPosition = 0;

                                    handler.post(new Runnable() {
                                        public void run() {
                                            mRecv.setText(data);
                                        }
                                    });
                                } else {
                                    readBuffer[readBufferPosition++] = b;
                                }
                            }
                        }
                    } catch (IOException ex) {
                        stopWorker = true;
                    }
                }
            }
        });

        workerThread.start();
    }

    //12. UI에 입력된 문자열이 있다면 출력 스트림에 기록하고
//화면에 "Data Sent"를 출력해줍니다.
    void sendData() throws IOException {
        String msg = myTextbox.getText().toString();
        if (msg.length() == 0) return;

        msg += "\n";
        Log.d(msg, msg);
        mmOutputStream.write(msg.getBytes());
        myLabel.setText("Data Sent");
        myTextbox.setText(" ");
    }









    // 알람 설정 불러오기
    private StringBuffer getPreferences(){
        StringBuffer sendData = new StringBuffer();
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        sendData.append("<");
        sendData.append(pref.getString("type", ""));
        sendData.append(":");
        sendData.append(pref.getString("week",""));
        sendData.append(":");
        sendData.append(pref.getString("hour",""));
        sendData.append(":");
        sendData.append(pref.getString("min",""));
        sendData.append(">");



        return sendData;
    }
}









