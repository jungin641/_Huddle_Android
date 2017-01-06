package com.example.sungshin.huddle.MainList;

/**
 * Created by LG on 2016-12-27.
 */

public class
MainListData {
    public int meeting_id;
    public String host_name;
    public String host_profile;
    public String title;
    public int is_open;
    public int when_fix;
    public int where_fix;
    public int member;


    public MainListData(int meeting_id, String host_name, String host_profile, String title, int is_open, int when_fix, int where_fix, int member) {
        this.meeting_id = meeting_id;
        this.host_name = host_name;
        this.host_profile = host_profile;
        this.title = title;
        this.is_open = is_open;
        this.when_fix = when_fix;
        this.where_fix = where_fix;
        this.member = member;
    }
}
