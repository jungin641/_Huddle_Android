package com.example.sungshin.huddle.MakingAppointment;

import java.util.ArrayList;

/**
 * Created by LG on 2017-01-03.
 */

public class roomCreateDataList {

    meeting meeting;
    ArrayList<String> days;
    position position;
    ArrayList<String> participant;


    public class meeting {
        public String host_id;//방장의 id(본인 id)
        public String title;
        public String text;
        public int is_open; // (0: 비공개방, 1: 공개방)
        public int when_fix;// (0:날짜 미정, 1: 날짜 확정)
        public int where_fix;  //( 0: 장소 미정, 1: 장소 확정)

//        public meeting(String host_id, String title, String text, int is_open, int when_fix, int where_fix) {
//            this.host_id = host_id;
//            this.title = title;
//            this.text = text;
//            this.is_open = is_open;
//            this.when_fix = when_fix;
//            this.where_fix = where_fix;
//        }
    }


    //방장이 가능한 위치를 표시함
    public class position {

        public String place;
        public String longitude;
        public String latitude;


//        public position(String place, String longitude, String latitude) {
//            this.place = place;
//            this.longitude = longitude;
//            this.latitude = latitude;
//        }
    }

}