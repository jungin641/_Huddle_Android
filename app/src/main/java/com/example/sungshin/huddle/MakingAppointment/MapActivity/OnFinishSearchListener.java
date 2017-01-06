package com.example.sungshin.huddle.MakingAppointment.MapActivity;

import java.util.List;

/**
 * Created by LG on 2016-12-30.
 */
public interface OnFinishSearchListener {
    public void onSuccess(List<Item> itemList);
    public void onFail();
}