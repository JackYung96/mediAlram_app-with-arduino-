package com.example.jaegyengjo.medino.AlarmSet;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.jaegyengjo.medino.R;

public class WhichMedi extends AppCompatActivity {

    public static Context mContext;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_which_medi);

        // 다른 액티비티에서 필요한 함수 호출 가능하게 함
        mContext = this;




        // 요일 설정으로 넘어가기
        Button next = (Button)findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WhichMedi.this, WeekSet.class);
                startActivity(intent);
            }
        });

        // 약병 선택
        Button bottle = (Button)findViewById(R.id.bottle);
        bottle.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                btnStringSave("type", "1");
                Toast.makeText(getApplicationContext(), "약병 타입 선택", Toast.LENGTH_SHORT).show();
            }

        });

        // 일반 약 선택
        Button pill = (Button)findViewById(R.id.pill);
        pill.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                btnStringSave("type", "2");
                Toast.makeText(getApplicationContext(), "일반 타입 선택", Toast.LENGTH_SHORT).show();
            }

        });










    }


    // 버튼 내용 저장
    public void btnStringSave(String type, String value){

        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(type, value);
        editor.commit();

    }

}

