package com.example.sungshin.huddle.DetailPage.WhenShow;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by sungshin on 2017-01-02.
 */

public class MonthItemView1 extends LinearLayout {//extends TextView {

    ArrayList<String> temp1;

    private MonthItem1 item;
    TextView text3;
    TextView text4;
    public Boolean clickCheck = false;
    public int count=0;

    public MonthItemView1(Context context) {
        super(context);

        init();
    }
//
//    public MonthItemView1(Context context, AttributeSet attrs) {
//        super(context, attrs);
//
//        init();
//    }

    private void init() {
        text3 = new TextView(getContext());
        text4 = new TextView(getContext());
        setBackgroundColor(Color.WHITE);


        this.setOrientation(VERTICAL);

        text3.setLayoutParams(new LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT));

        this.addView(text3);


        text4.setLayoutParams(new LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT));

        text4.setGravity(Gravity.RIGHT);
        this.addView(text4);

    }


    public MonthItem1 getItem() {
        return item;
    }

    public void setItem(MonthItem1 item) {
        this.item = item;




        int day1 = item.getDay1();
        if (day1 != 0) {

            text3.setText(String.valueOf(day1));
            if(count==0) text4.setText("");
            else text4.setText(String.valueOf(count));

        } else {
            text3.setText("");
            text4.setText("");
        }

    }

    public void setText1ChangeTextColor(int color) {
        text3.setTextColor(color);
    }

    public void setText2ChangeTextColor(int color) {
        text4.setTextColor(color);
    }
}

