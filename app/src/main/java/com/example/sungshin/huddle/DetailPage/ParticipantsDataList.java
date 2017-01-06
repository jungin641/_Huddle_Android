package com.example.sungshin.huddle.DetailPage;

/**
 * Created by LG on 2016-12-31.
 */

public class ParticipantsDataList {
    public String name;
    public String is_input;
    public String place; //한글주소명
    public String longitude;
    public String latitude;

    public ParticipantsDataList(String name, String is_input, String place, String longitude, String latitude) {
        this.name = name;
        this.is_input = is_input;
        this.place = place;
        this.longitude = longitude;
        this.latitude = latitude;
    }
}
