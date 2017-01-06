package com.example.sungshin.huddle.DetailPage;

/**
 * Created by LG on 2016-12-31.
 */

public class RoomInfoDataList {
    public String title;
    public String text;
    public int is_open;
    public int where_fix;
    public String image;
    public String host;
    public  String host_profile;

    public RoomInfoDataList(String title, String text, int is_open, int where_fix, String image, String host, String host_profile) {
        this.title = title;
        this.text = text;
        this.is_open = is_open;
        this.where_fix = where_fix;
        this.image = image;
        this.host = host;
        this.host_profile = host_profile;
    }
}
