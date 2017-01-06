package com.example.sungshin.huddle.EditActivity.EditFragment.WhenEditFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import com.example.sungshin.huddle.EditActivity.EditMyOpinionResult;
import com.example.sungshin.huddle.MakingAppointment.CalendarActivity.OnDataSelectionListener;
import com.huddle.huddle.R;

import java.util.ArrayList;

public class WhenEditFragment extends Fragment {
//    public String checkdate;
//    public int checkcount;

    //월별 캘린더 뷰
    CalendarMonthView2 monthView;

    //월별 캘린더 어댑터 뷰
    CalendarMonthAdapter2 monthViewAdapter;

    //월을 표시하는 텍스트 뷰
    TextView monthText;

    public String userbeforeselectdate = "";

    //현재 년도
    int curYear;

    //현재 월
    int curMonth;

    ArrayList<String> days;
    ArrayList<StoreDate2> storeDates2;
    TextView beforeuserselectdate;

    //static ArrayList<StoreDate2> static_days;
    static ArrayList<StoreDate2> static_days = new ArrayList<StoreDate2>();
    //static ArrayList<String> temp2;
    static ArrayList<String> temp2 = new ArrayList<String>();

    EditMyOpinionResult myOpinionResult;

    //    ArrayList<DatesDataList> datesDatas;
    // ArrayList<DetailDataList> detailDatesData;
//
//
    public WhenEditFragment(ArrayList<String> days) {

        this.days = days;

        for (int i = 0; i < days.size(); i++)

        {
            Log.i("myTag", "days " + days.get(i));

        }
    }


    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.activity_edit_when, container, false);

        beforeuserselectdate = (TextView) view.findViewById(R.id.beforeuserselectdate);

        storeDates2 = new ArrayList<>();
        // 월별 캘린더 뷰 객체 참조
        monthView = (CalendarMonthView2) view.findViewById(R.id.monthView2);
        monthViewAdapter = new CalendarMonthAdapter2(getActivity(),storeDates2);
        monthView.setAdapter(monthViewAdapter);

        static_days = storeDates2;




        // 리스너 설정
        monthView.setOnDataSelectionListener(new OnDataSelectionListener() {
            public void onDataSelected(AdapterView parent, View v, int position, long id) {
                // 현재 선택한 일자 정보 표시
                MonthItem2 curItem = (MonthItem2) monthViewAdapter.getItem(position);
                int year = monthViewAdapter.getCurYear();
                int month = monthViewAdapter.getCurMonth() + 1;
                int day = curItem.getDay2();

                Log.d("MainActivity", "Selected : " + year + ":" + month + ":" + day);

                boolean deleteCheck = false;

                if(day == 0)
                {
                    return;
                }

                int i;
                for (i = 0; i < storeDates2.size(); i++) {
                    if (storeDates2.get(i).year == year && storeDates2.get(i).month == month && storeDates2.get(i).day == day) {
                        storeDates2.remove(i);
//                        Log.i("myTag", "삭제 Selected : " + day);
                        deleteCheck = true;
                        break;
                    }
                }

                if (!deleteCheck) {
//                    Log.i("myTag", "추가 Selected : " + day);
                    storeDates2.add(new StoreDate2(year, month, day));
                }


            }
        });



        for (int i = 0; i < days.size(); i++)

        {

//                beforeuserselectdate.setText(days.get(i).charAt(1) +days.get(i).charAt(2) +days.get(i).charAt(2) +days.get(i).charAt(3) + "년"+days.get(i).charAt(4) +
//                        days.get(i).charAt(5) +"월"+days.get(i).charAt(6) +days.get(i).charAt(7)+"일");


            userbeforeselectdate = userbeforeselectdate + "\n"+ String.valueOf(days.get(i));
        }

            beforeuserselectdate.setText(" 사용자가 이전에 선택한 날짜는  " + userbeforeselectdate+ "  \n " + "입니다.");


//        Log.i("myTag", "temp " + temp1);
//            Log.i("myTag", "days " + days.get(i));
//            beforeuserselectdate.setText(days.get(i));
//





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


    public static ArrayList<String> geteditDates(){

        for (int i = 0; i < static_days.size(); i++) {
            String temps= "";

            temps = String.valueOf(static_days.get(i).year);

            if (static_days.get(i).month < 10)
                temps = temps + "0" + static_days.get(i).month;
            else
                temps = temps + static_days.get(i).month;


            if (static_days.get(i).day < 10)
                temps = temps + "0" + static_days.get(i).day;
            else
                temps = temps + static_days.get(i).day;

            temp2.add(i, temps);
        }

        Log.i("myTag", "Date tmp === " + temp2);
        return temp2;
    }
    //월표시텍스트설정
    private void setMonthText() {
        curYear = monthViewAdapter.getCurYear();
        curMonth = monthViewAdapter.getCurMonth();

        monthText.setText(curYear + "년 " + (curMonth + 1) + "월");
    }

}