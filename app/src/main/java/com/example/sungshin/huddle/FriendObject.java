package com.example.sungshin.huddle;

import com.example.sungshin.huddle.MakingAppointment.FriendListActivity.StorePhoneNum;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by 손영호 on 2016-12-31.
 */
public class FriendObject implements Serializable {

    public ArrayList<StorePhoneNum> storePhoneNums;

    public void setStoreDates(ArrayList<StorePhoneNum> storePhoneNums) {
        this.storePhoneNums = storePhoneNums;
    }

    public ArrayList<StorePhoneNum> getStorePhoneNums() {
        return storePhoneNums;
    }
}