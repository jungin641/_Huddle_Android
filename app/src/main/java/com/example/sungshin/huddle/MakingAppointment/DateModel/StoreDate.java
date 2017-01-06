package com.example.sungshin.huddle.MakingAppointment.DateModel;

import java.io.Serializable;

/**
 * Created by LG on 2016-12-30.
 */

public class StoreDate implements Serializable {
    public int year;
    public int month;
    public int day;

    public StoreDate(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;


    }

}
