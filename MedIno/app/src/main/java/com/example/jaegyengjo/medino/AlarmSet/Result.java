package com.example.jaegyengjo.medino.AlarmSet;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jaegyengjo.medino.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

import java.util.Date;

public class Result extends AppCompatActivity {

    String resData;
    MaterialCalendarView mcv;
    TextView tv1,tv2,tv3,tv4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);


        mcv = (MaterialCalendarView)findViewById((R.id.calendarV));

        mcv.state().edit()
                .setFirstDayOfWeek(android.icu.util.Calendar.SUNDAY)
                .setMinimumDate(CalendarDay.from(1900, 1, 1))
                .setMaximumDate(CalendarDay.from(2100, 12, 31))
                .setCalendarDisplayMode(CalendarMode.WEEKS)

                .commit();


        tv1 = (TextView)findViewById(R.id.tv1);
        tv1.setText(getPreferences("pillname"));

        tv2 = (TextView)findViewById(R.id.tv2);
        tv2.setText(getPreferences("pillnum"));

        tv3 = (TextView)findViewById(R.id.tv3);
        tv3.setText(getPreferences("pillef"));

        tv4 = (TextView)findViewById(R.id.tv4);
        tv4.setText(getPreferences("pilletc"));





        Button button = (Button)findViewById(R.id.stop);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "약 복용 완료!", Toast.LENGTH_SHORT).show();

                ((MemoSet)MemoSet.mContext3).stopAlarm();

                mcv.addDecorator(new onDayDecorator());

            }
        });


    }


    public class onDayDecorator implements DayViewDecorator {

        private CalendarDay date;

        public onDayDecorator() {
            date = CalendarDay.today();
        }

        @Override
        public boolean shouldDecorate(CalendarDay day) {
            return date != null && day.equals(date);
        }

        @Override
        public void decorate(DayViewFacade view) {


            view.addSpan(new StyleSpan(Typeface.BOLD));
            view.addSpan(new RelativeSizeSpan(1.4f));
            view.addSpan(new ForegroundColorSpan(Color.GREEN));
            view.addSpan(new DotSpan(10, Color.CYAN));

        }


        public void setDate(Date date) {
            this.date = CalendarDay.from(date);
        }



    }


    // 알람 설정 불러오기
    private String getPreferences(String str){

        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);

        resData = pref.getString(str,"");

        return resData;
    }


}
