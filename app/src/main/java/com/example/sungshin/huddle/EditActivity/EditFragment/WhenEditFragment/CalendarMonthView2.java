package com.example.sungshin.huddle.EditActivity.EditFragment.WhenEditFragment;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;

/**
 * Created by sungshin on 2017-01-05.
 */

public class CalendarMonthView2 extends GridView {

    //일자 선택을 위해 직접 정의한 리스너 객체
    private com.example.sungshin.huddle.MakingAppointment.CalendarActivity.OnDataSelectionListener selectionListener;

    //어댑터 객체
    CalendarMonthAdapter2 adapter2;

    /**
     * 생성자
     *
     * @param context
     */
    public CalendarMonthView2(Context context) {
        super(context);

        init();
    }

    /**
     * 생성자
     *
     * @param context
     * @param attrs
     */
    public CalendarMonthView2(Context context, AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    //속성초기화
    private void init() {
        setBackgroundColor(Color.GRAY);
        setVerticalSpacing(1);
        setHorizontalSpacing(1);
        setStretchMode(GridView.STRETCH_COLUMN_WIDTH);

        // 칼럼의 갯수 설정
        setNumColumns(7);

        // 그리드뷰의 원래 이벤트 처리 리스너 설정
        setOnItemClickListener(new CalendarMonthView2.OnItemClickAdapter());
    }

    //어댑터객체리턴
    public void setAdapter(BaseAdapter adapter2) {
        super.setAdapter(adapter2);

        this.adapter2 = (CalendarMonthAdapter2) adapter2;
    }

    //어댑터객체리턴
    public BaseAdapter getAdapter() {
        return (BaseAdapter) super.getAdapter();
    }

    //리스너설정
    public void setOnDataSelectionListener(com.example.sungshin.huddle.MakingAppointment.CalendarActivity.OnDataSelectionListener listener) {
        this.selectionListener = listener;
    }

    //리스너객체리턴
    public com.example.sungshin.huddle.MakingAppointment.CalendarActivity.OnDataSelectionListener getOnDataSelectionListener() {
        return selectionListener;
    }

    class OnItemClickAdapter implements OnItemClickListener {

        public OnItemClickAdapter() {

        }

        public void onItemClick(AdapterView parent, View v, int position, long id) {

            if (adapter2 != null) {
                adapter2.setSelectedPosition(position);
                adapter2.notifyDataSetInvalidated();
            }

            if (selectionListener != null) {
                selectionListener.onDataSelected(parent, v, position, id);
            }


        }

    }

}