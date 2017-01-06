package com.example.sungshin.huddle.DetailPage;

/**
 * Created by sungshin on 2017-01-02.
 */
public class DatesDataList {

    String date;
    int count;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public DatesDataList(String date, int count) {
        this.date = date;
        this.count = count;
    }
}
