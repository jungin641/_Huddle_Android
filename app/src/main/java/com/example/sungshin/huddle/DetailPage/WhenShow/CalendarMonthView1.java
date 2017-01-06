package com.example.sungshin.huddle.DetailPage.WhenShow;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;

import com.example.sungshin.huddle.MakingAppointment.CalendarActivity.OnDataSelectionListener;

/**
 * Created by sungshin on 2017-01-02.
 */

public class CalendarMonthView1 extends GridView {

    //일자 선택을 위해 직접 정의한 리스너 객체
    private OnDataSelectionListener selectionListener;

    //어댑터 객체
    CalendarMonthAdapter1 adapter1;

    /**
     * 생성자
     *
     * @param context
     */
    public CalendarMonthView1(Context context) {
        super(context);

        init();
    }

    /**
     * 생성자
     *
     * @param context
     * @param attrs
     */
    public CalendarMonthView1(Context context, AttributeSet attrs) {
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
        setOnItemClickListener(new CalendarMonthView1.OnItemClickAdapter());
    }
    //어댑터객체리턴
    public void setAdapter(BaseAdapter adapter1) {
        super.setAdapter(adapter1);

        this.adapter1 = (CalendarMonthAdapter1) adapter1;
    }

    //어댑터객체리턴
    public BaseAdapter getAdapter() {
        return (BaseAdapter) super.getAdapter();
    }

    //리스너설정
    public void setOnDataSelectionListener(OnDataSelectionListener listener) {
        this.selectionListener = listener;
    }

    //리스너객체리턴
    public OnDataSelectionListener getOnDataSelectionListener() {
        return selectionListener;
    }

    class OnItemClickAdapter implements OnItemClickListener {

        public OnItemClickAdapter() {

        }

        public void onItemClick(AdapterView parent, View v, int position, long id) {

            if (adapter1 != null) {
                adapter1.setSelectedPosition(position);
                adapter1.notifyDataSetInvalidated();
            }

            if (selectionListener != null) {
                selectionListener.onDataSelected(parent, v, position, id);
            }


        }

    }

}
