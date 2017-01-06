package com.example.sungshin.huddle.MakingAppointment.CalendarActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import com.example.sungshin.huddle.DateObject;
import com.example.sungshin.huddle.FriendObject;
import com.example.sungshin.huddle.MakingAppointment.DateModel.StoreDate;
import com.example.sungshin.huddle.MakingAppointment.FriendListActivity.MakingAppointmentActivity1;
import com.example.sungshin.huddle.MakingAppointment.MapActivity.MakingAppointmentActivity3;
import com.huddle.huddle.R;

import java.util.ArrayList;


public class MakingAppointmentActivity2 extends AppCompatActivity {
    Button btnwhenbefore, btnwhennext;

    //월별 캘린더 뷰
    CalendarMonthView monthView;

   //월별 캘린더 어댑터 뷰
    CalendarMonthAdapter monthViewAdapter;

    //월을 표시하는 텍스트 뷰
    TextView monthText;

    //현재 년도
    int curYear;

   //현재 월
    int curMonth;

    ArrayList<StoreDate> storeDates;
    FriendObject friendObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_making_appointment2);

        storeDates = new ArrayList<>();

        btnwhenbefore = (Button) findViewById(R.id.btnwhenbefore);
        btnwhennext = (Button) findViewById(R.id.btnwhennext);

        btnwhenbefore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MakingAppointmentActivity2.this, MakingAppointmentActivity1.class);
                startActivity(intent);
                finish();

            }
        });

        btnwhennext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DateObject object = new DateObject();

                friendObject = (FriendObject)getIntent().getSerializableExtra("storefriend");
                object.setStoreDates(storeDates);

                Intent intent = new Intent(MakingAppointmentActivity2.this, MakingAppointmentActivity3.class);
                intent.putExtra("store", object);
                intent.putExtra("storefriend", friendObject);
                startActivity(intent);
                finish();

            }
        });

        // 월별 캘린더 뷰 객체 참조
        monthView = (CalendarMonthView) findViewById(R.id.monthView);
        monthViewAdapter = new CalendarMonthAdapter(this, storeDates);
        monthView.setAdapter(monthViewAdapter);

        // 리스너 설정
        monthView.setOnDataSelectionListener(new OnDataSelectionListener() {
            public void onDataSelected(AdapterView parent, View v, int position, long id) {
                // 현재 선택한 일자 정보 표시
                MonthItem curItem = (MonthItem) monthViewAdapter.getItem(position);
                int year = monthViewAdapter.getCurYear();
                int month = monthViewAdapter.getCurMonth() + 1;
                int day = curItem.getDay();

                Log.d("MainActivity", "Selected : " + year + ":" + month + ":" + day);

                boolean deleteCheck = false;

                if(day == 0)
                {
                    return;
                }

                int i;
                for (i = 0; i < storeDates.size(); i++) {
                    if (storeDates.get(i).year == year && storeDates.get(i).month == month && storeDates.get(i).day == day) {
                        storeDates.remove(i);
//                        Log.i("myTag", "삭제 Selected : " + day);
                        deleteCheck = true;
                        break;
                    }
                }

                if (!deleteCheck) {
//                    Log.i("myTag", "추가 Selected : " + day);
                    storeDates.add(new StoreDate(year, month, day));
                }


            }
        });

        monthText = (TextView) findViewById(R.id.monthText);
        setMonthText();


        // 이전 월로 넘어가는 이벤트 처리
        Button monthPrevious = (Button) findViewById(R.id.monthPrevious);
        monthPrevious.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                monthViewAdapter.changeMonthCheck = true;
                monthViewAdapter.setPreviousMonth();
                monthViewAdapter.notifyDataSetChanged();

                setMonthText();
            }
        });

        // 다음 월로 넘어가는 이벤트 처리
        Button monthNext = (Button) findViewById(R.id.monthNext);
        monthNext.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                monthViewAdapter.changeMonthCheck = true;
                monthViewAdapter.setNextMonth();
                monthViewAdapter.notifyDataSetChanged();

                setMonthText();
            }
        });


    }

  //월표시텍스트설정
    private void setMonthText() {
        curYear = monthViewAdapter.getCurYear();
        curMonth = monthViewAdapter.getCurMonth();

        monthText.setText(curYear + "년 " + (curMonth + 1) + "월");
    }

}
