package com.example.sungshin.huddle.EditActivity.EditFragment.WhenEditFragment;

import android.view.View;
import android.widget.AdapterView;

/**
 * Created by sungshin on 2017-01-05.
 */

public interface OnDataSelectionListener2 {

    /**
     * 아이템이 선택되었을 때 호출되는 메소드
     *
     * @param parent Parent View
     * @param v Target View
     * @param row Row Index
     * @param column Column Index
     * @param id ID for the View
     */
    public void onDataSelected(AdapterView parent, View v, int position, long id);

}