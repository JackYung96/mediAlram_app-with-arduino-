package com.example.jaegyengjo.medino.AlarmSet;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jaegyengjo.medino.arduinoSend;
import com.example.jaegyengjo.medino.R;

import java.util.Calendar;

public class MemoSet extends AppCompatActivity {

    EditText et,et2,et3,et4;

    public static Context mContext3;

    int wData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo_set);

        mContext3 = this;

        Button next = (Button)findViewById(R.id.done);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MemoSet.this, arduinoSend.class);
                startActivity(intent);

                new AlarmHATT(getApplicationContext()).Alarm();

                et = (EditText)findViewById(R.id.memo1);
                et2 = (EditText)findViewById(R.id.memo2);
                et3 = (EditText)findViewById(R.id.memo3);
                et4 = (EditText)findViewById(R.id.memo4);



                String etStr = et.getText().toString();
                ((WhichMedi)WhichMedi.mContext).btnStringSave("pillname", etStr);

                String et2Str = et2.getText().toString();
                ((WhichMedi)WhichMedi.mContext).btnStringSave("pillnum", et2Str);

                String et3Str = et3.getText().toString();
                ((WhichMedi)WhichMedi.mContext).btnStringSave("pillef", et3Str);

                String et4Str = et4.getText().toString();
                ((WhichMedi)WhichMedi.mContext).btnStringSave("pilletc", et4Str);


            }
        });
    }


    public void stopAlarm(){
        new AlarmHATT(getApplicationContext()).Alarm().cancel();

    }


    public class AlarmHATT {
        private Context context;

        public AlarmHATT(Context context) {
            this.context = context;
        }

        public PendingIntent Alarm() {

            long period = 1000 * 5;

            AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(MemoSet.this, BroadcastD.class);

            PendingIntent sender = PendingIntent.getBroadcast(MemoSet.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            Calendar calendar = Calendar.getInstance();
            //알람시간 calendar에 set해주기

            calendar.set(Calendar.HOUR_OF_DAY, getHour());
            calendar.set(Calendar.MINUTE, getMin());
            calendar.set(Calendar.DAY_OF_WEEK, getWeek());


            //알람 예약
            am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), period, sender);

            return sender;
        }
    }



    private int getHour(){
        int hData;
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);

        hData =  Integer.parseInt(pref.getString("hour", ""));

        return hData;
    }

    private int getMin(){
        int mData;
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);

        mData =  Integer.parseInt(pref.getString("min", ""));

        return mData;
    }




    private int getWeek(){
        String temp;
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);

        temp =  pref.getString("week", "");

        switch(temp)
        {
            case "Sun":
                wData = 1;
                break;
            case "Mon":
                wData = 2;
                break;
            case "Tue":
                wData = 3;
                break;
            case "Wed":
                wData = 4;
                break;
            case "Thu":
                wData = 5;
                break;
            case "Fri":
                wData = 6;
                break;
            case "Sat":
                wData = 7;
                break;

        }



        return wData;
    }


}
