package com.example.sungshin.huddle.EditActivity.EditFragment.WhenEditFragment;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.util.ArrayList;

/**
 * Created by sungshin on 2017-01-05.
 */

public class MonthItemView2 extends LinearLayout {//extends TextView {

    ArrayList<String> temp1;

    private MonthItem2 item;
    TextView text5;
    TextView text6;
    public Boolean clickCheck = false;

    public MonthItemView2(Context context) {
        super(context);

        init();
    }

    public MonthItemView2(Context context, AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    private void init() {
        text5 = new TextView(getContext());
        text6 = new TextView(getContext());
        setBackgroundColor(Color.WHITE);


        this.setOrientation(VERTICAL);

        text5.setLayoutParams(new LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT));

        this.addView(text5);


        text6.setLayoutParams(new LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT));

        text6.setGravity(Gravity.RIGHT);
        this.addView(text6);

    }


    public MonthItem2 getItem() {
        return item;
    }

    public void setItem(MonthItem2 item) {
        this.item = item;


        int day2 = item.getDay2();
        if (day2 != 0) {

            text5.setText(String.valueOf(day2));
            text6.setText("");
//            for(int i = 0; i < temp1.size() ; i++) {
//                if(temp1.get(i) == checkdate && count != 0){
//                    text2.setText(count);
//                }
//            }



        } else {
            text5.setText("");
            text6.setText("");
        }

    }

    public void setText1ChangeTextColor(int color) {
        text5.setTextColor(color);
    }

    public void setText2ChangeTextColor(int color) {
        text6.setTextColor(color);
    }
}
