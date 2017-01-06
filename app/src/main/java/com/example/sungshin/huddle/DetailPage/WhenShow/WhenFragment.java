package com.example.sungshin.huddle.DetailPage.WhenShow;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import com.example.sungshin.huddle.DetailPage.DatesDataList;
import com.example.sungshin.huddle.MakingAppointment.CalendarActivity.OnDataSelectionListener;
import com.huddle.huddle.R;

import java.util.ArrayList;

public class WhenFragment extends Fragment {
    public String checkdate;
    public int checkcount;

    //월별 캘린더 뷰
    CalendarMonthView1 monthView;

    //월별 캘린더 어댑터 뷰
    CalendarMonthAdapter1 monthViewAdapter;

    //월을 표시하는 텍스트 뷰
    TextView monthText;

    //현재 년도
    int curYear;

    //현재 월
    int curMonth;

    ArrayList<DatesDataList> datesDatas;
   // ArrayList<DetailDataList> detailDatesData;


    public WhenFragment(ArrayList<DatesDataList> datesDatas) {

        this.datesDatas = datesDatas;

        for (int i = 0; i < datesDatas.size(); i++)

        {
            Log.i("myTag", "dateDatas0 " + datesDatas.get(i).getDate());
            Log.i("myTag", "dateDatas1 " + datesDatas.get(i).getCount());
            checkcount = datesDatas.get(i).getCount();
            checkdate = datesDatas.get(i).getDate();

        }
    }

    //
//        Log.i("myTag" , "dateDatas0 " + datesDatas.get(1).getDate());
//        Log.i("myTag" , "dateDatas1 " + datesDatas.get(1).getCount());
//





    // Log.d("myTag" , "dateDatas0" + detailDatesData.get(0).date1);
    // Log.d("myTag" , "dateDatas1" + Integer.parseInt(String.valueOf((datesDatas.get(1)))));


//    public WhenFragment(ArrayList<DetailDatesData> detailDatesData)
//    {
//        this.detailDatesData = detailDatesData;
//        Log.d("myTag" , "dateDatas0" + detailDatesData.get(0).date1);
//        Log.d("myTag" , "dateDatas1" + detailDatesData.get(1).count1);
//
//    }

    //DetailDatesData DetailDatesData;

    //detailDatesDataList detailDatesDataList;
    // ArrayList<detailDatesDataList> datelist;


    @Override
        public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
            View view = inflater.inflate(R.layout.activity_when, container, false);


        // 월별 캘린더 뷰 객체 참조
        monthView = (CalendarMonthView1) view.findViewById(R.id.monthView1);
        monthViewAdapter = new CalendarMonthAdapter1(getActivity());
        monthViewAdapter.datesDatas=datesDatas;
        monthView.setAdapter(monthViewAdapter);

        // 리스너 설정
        monthView.setOnDataSelectionListener(new OnDataSelectionListener() {
            public void onDataSelected(AdapterView parent, View v, int position, long id) {
                // 현재 선택한 일자 정보 표시
                MonthItem1 curItem = (MonthItem1) monthViewAdapter.getItem(position);
                int year = monthViewAdapter.getCurYear();
                int month = monthViewAdapter.getCurMonth() + 1;
                int day = curItem.getDay1();

                Log.d("MainActivity", "Selected : " + year + ":" + month + ":" + day);

                boolean deleteCheck = false;

//                if(day == 0)
//                {
//                    return;
//                }
//
//                int i;
//                for (i = 0; i < storeDates.size(); i++) {
//                    if (storeDates.get(i).year == year && storeDates.get(i).month == month && storeDates.get(i).day == day) {
//                        storeDates.remove(i);
////                        Log.i("myTag", "삭제 Selected : " + day);
//                        deleteCheck = true;
//                        break;
//                    }
//                }
//
//                if (!deleteCheck) {
////                    Log.i("myTag", "추가 Selected : " + day);
//                    storeDates.add(new StoreDate(year, month, day));
//                }
//
//
     }
        });

        monthText = (TextView) view.findViewById(R.id.monthText);
        setMonthText();


        // 이전 월로 넘어가는 이벤트 처리
        Button monthPrevious = (Button) view.findViewById(R.id.monthPrevious);
        monthPrevious.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                monthViewAdapter.changeMonthCheck = true;
                monthViewAdapter.setPreviousMonth();
                monthViewAdapter.notifyDataSetChanged();

                setMonthText();
            }
        });

        // 다음 월로 넘어가는 이벤트 처리
        Button monthNext = (Button) view.findViewById(R.id.monthNext);
        monthNext.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                monthViewAdapter.changeMonthCheck = true;
                monthViewAdapter.setNextMonth();
                monthViewAdapter.notifyDataSetChanged();

                setMonthText();
            }
        });


            return view;
        }

    //월표시텍스트설정
    private void setMonthText() {
        curYear = monthViewAdapter.getCurYear();
        curMonth = monthViewAdapter.getCurMonth();

        monthText.setText(curYear + "년 " + (curMonth + 1) + "월");
    }

}
