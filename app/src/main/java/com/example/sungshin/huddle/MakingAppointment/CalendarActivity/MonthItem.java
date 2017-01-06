package com.example.sungshin.huddle.MakingAppointment.CalendarActivity;


//일자정보를 담기위한 클래스 정의

public class MonthItem {
    private int dayValue;

    public MonthItem() {

    }
    public MonthItem(int day) {
        dayValue = day;
    }

    public int getDay() {
        return dayValue;
    }
    public void setDay(int day) {
        this.dayValue = day;
    }

}