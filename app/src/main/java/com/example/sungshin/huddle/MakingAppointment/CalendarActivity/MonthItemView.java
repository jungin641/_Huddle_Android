package com.example.sungshin.huddle.MakingAppointment.CalendarActivity;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;


//일자에 표시하는 텍스트 뷰 정의
// public class MonthItemView extends TextView {
//
//    private MonthItem item;
//
//    public MonthItemView(Context context) {
//        super(context);
//
//        init();
//    }
//
//    public MonthItemView(Context context, AttributeSet attrs) {
//        super(context, attrs);
//
//        init();
//    }
//
//    private void init() {
//        setBackgroundColor(Color.WHITE);
//    }
//
//
//    public MonthItem getItem() {
//        return item;
//    }
//
//    public void setItem(MonthItem item) {
//        this.item = item;
//
//        int day = item.getDay();
//        if (day != 0) {
//            setText(String.valueOf(day) + "\nhm");
//        } else {
//            setText("");
//        }
//
//    }
//}

public class MonthItemView extends LinearLayout {//extends TextView {

    ArrayList<String> temp1;

    private MonthItem item;
    TextView text1;
    TextView text2;
    public Boolean clickCheck = false;

    public MonthItemView(Context context) {
        super(context);

        init();
    }

    public MonthItemView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    private void init() {
        text1 = new TextView(getContext());
        text2 = new TextView(getContext());
        setBackgroundColor(Color.WHITE);


        this.setOrientation(VERTICAL);

        text1.setLayoutParams(new LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT));

        this.addView(text1);


        text2.setLayoutParams(new LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT));

        text2.setGravity(Gravity.RIGHT);
        this.addView(text2);

    }


    public MonthItem getItem() {
        return item;
    }

    public void setItem(MonthItem item) {
        this.item = item;


        int day = item.getDay();
        if (day != 0) {

            text1.setText(String.valueOf(day));
            text2.setText("");
//            for(int i = 0; i < temp1.size() ; i++) {
//                if(temp1.get(i) == checkdate && count != 0){
//                    text2.setText(count);
//                }
//            }



        } else {
            text1.setText("");
            text2.setText("");
        }

    }

    public void setText1ChangeTextColor(int color) {
        text1.setTextColor(color);
    }

    public void setText2ChangeTextColor(int color) {
        text2.setTextColor(color);
    }
}
