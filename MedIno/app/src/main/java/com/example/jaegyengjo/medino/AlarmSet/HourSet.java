package com.example.jaegyengjo.medino.AlarmSet;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.jaegyengjo.medino.R;





public class HourSet extends AppCompatActivity {

    public static Context mContext2;
    EditText et;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hour_set);

        mContext2 = this;
        et = (EditText)findViewById(R.id.editText1);






        // 분 설정으로 이동
        Button next = (Button)findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(HourSet.this, MinuteSet.class);
                startActivity(intent);

                // 시간 값 저장
                 String hourStr = et.getText().toString();
                ((WhichMedi)WhichMedi.mContext).btnStringSave("hour", hourStr);

                Toast.makeText(getApplicationContext(), hourStr + "시 설정", Toast.LENGTH_SHORT).show();



            }
        });


        Button one = (Button)findViewById(R.id.one);
        one.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                btnEvent("1");

            }

        });


        Button two = (Button)findViewById(R.id.two);
        two.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                btnEvent("2");

            }

        });

        Button three = (Button)findViewById(R.id.three);
        three.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                btnEvent("3");

            }

        });

        Button four = (Button)findViewById(R.id.four);
        four.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                btnEvent("4");
            }

        });

        Button five = (Button)findViewById(R.id.five);
        five.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                btnEvent("5");
            }

        });

        Button six = (Button)findViewById(R.id.six);
        six.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                btnEvent("6");

            }

        });

        Button seven = (Button)findViewById(R.id.seven);
        seven.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                btnEvent("7");

            }

        });

        Button eight = (Button)findViewById(R.id.eight);
        eight.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                btnEvent("8");

            }

        });
        Button nine = (Button)findViewById(R.id.nine);
        nine.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                btnEvent("9");

            }

        });

        Button zero = (Button)findViewById(R.id.zero);
        zero.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                btnEvent("0");

            }

        });








    }


    // 버튼 눌렀을 때의 이벤트
    public void btnEvent (String num){
        if(et.getText().toString() != ""){
            String temp = et.getText().toString();
            et.setText(temp + num);
        }
        else
            et.setText(num);

    }





}

