package com.example.sungshin.huddle.EditActivity;

import java.util.ArrayList;

/**
 * Created by LG on 2017-01-04.
 */

public class EditMyOpinionResult {
    ArrayList<friend_list> friend_list;
    ArrayList<String> days;
    position position;
    int my_meeting_id;

    public class friend_list {
        public String name;
        public String profile;
        public String id;
    }

    public class position {
        public String palce;
        public String longitude;
        public String latitude;

    }
}
