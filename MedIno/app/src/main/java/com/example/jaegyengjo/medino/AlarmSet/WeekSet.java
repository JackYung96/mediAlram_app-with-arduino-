package com.example.jaegyengjo.medino.AlarmSet;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.jaegyengjo.medino.R;

public class WeekSet extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week_set);


        // 시간 설정으로 이동
        Button next = (Button)findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WeekSet.this, HourSet.class);
                startActivity(intent);
            }
        });


        // 모든 요일 선택
        Button All = (Button)findViewById(R.id.All);
        All.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                ((WhichMedi)WhichMedi.mContext).btnStringSave("week", "All");
                Toast.makeText(getApplicationContext(), "모든 요일 선택", Toast.LENGTH_SHORT).show();
            }

        });



        //요일 선택
        Button Sun = (Button)findViewById(R.id.Sun);
        Sun.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                ((WhichMedi)WhichMedi.mContext).btnStringSave("week", "Sun");
                Toast.makeText(getApplicationContext(), "일요일 선택", Toast.LENGTH_SHORT).show();
            }

        });

        Button Mon = (Button)findViewById(R.id.Mon);
        Mon.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                ((WhichMedi)WhichMedi.mContext).btnStringSave("week", "Mon");
                Toast.makeText(getApplicationContext(), "월요일 선택", Toast.LENGTH_SHORT).show();
            }

        });

        Button Tue = (Button)findViewById(R.id.Tue);
        Tue.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                ((WhichMedi)WhichMedi.mContext).btnStringSave("week", "Tue");
                Toast.makeText(getApplicationContext(), "화요일 선택", Toast.LENGTH_SHORT).show();
            }

        });

        Button Wed = (Button)findViewById(R.id.Wed);
        Wed.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                ((WhichMedi)WhichMedi.mContext).btnStringSave("week", "Wed");
                Toast.makeText(getApplicationContext(), "수요일 선택", Toast.LENGTH_SHORT).show();
            }

        });

        Button Thu = (Button)findViewById(R.id.Thu);
        Thu.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                ((WhichMedi)WhichMedi.mContext).btnStringSave("week", "Thu");
                Toast.makeText(getApplicationContext(), "목요일 선택", Toast.LENGTH_SHORT).show();
            }

        });

        Button Fri = (Button)findViewById(R.id.Fri);
        Fri.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                ((WhichMedi)WhichMedi.mContext).btnStringSave("week", "Fri");
                Toast.makeText(getApplicationContext(), "금요일 선택", Toast.LENGTH_SHORT).show();
            }

        });

        Button Sat = (Button)findViewById(R.id.Sat);
        Sat.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                ((WhichMedi)WhichMedi.mContext).btnStringSave("week", "Sat");
                Toast.makeText(getApplicationContext(), "토요일 선택", Toast.LENGTH_SHORT).show();
            }

        });




    }


}
