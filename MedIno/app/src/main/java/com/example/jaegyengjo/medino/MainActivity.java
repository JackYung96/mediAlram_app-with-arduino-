package com.example.jaegyengjo.medino;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.jaegyengjo.medino.AlarmSet.Result;
import com.example.jaegyengjo.medino.AlarmSet.WhichMedi;

public class MainActivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        Button alarmSet = (Button) findViewById(R.id.alarmSetB);
        alarmSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, WhichMedi.class);
                startActivity(intent);
            }
        });

        Button alarmR = (Button) findViewById(R.id.alarmR);
        alarmR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Result.class);
                startActivity(intent);
            }
        });







    }






}
