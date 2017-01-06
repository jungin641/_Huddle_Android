package com.example.sungshin.huddle;

import com.example.sungshin.huddle.MakingAppointment.DateModel.StoreDate;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by kh on 2016. 12. 31..
 */

public class DateObject implements Serializable {

    public ArrayList<StoreDate> storeDates;

    public void setStoreDates(ArrayList<StoreDate> storeDates) {
        this.storeDates = storeDates;
    }

    public ArrayList<StoreDate> getStoreDates() {
        return storeDates;
    }
}
