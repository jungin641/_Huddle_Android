package com.example.sungshin.huddle.DetailPage.PlaceShow;

import java.io.Serializable;

/**
 * Created by SAMSUNG on 2017-01-02.
 */

public class detailLocationData implements Serializable{

    String place;
    Double lat, lon;

    public detailLocationData(String p, String Slat, String Slon){
        if(Slat == null)
            this.lat = null;
        else
            this.lat = Double.parseDouble(Slat);

        if(Slon == null)
            this.lon = null;
        else
            this.lon = Double.parseDouble(Slon);

        place = p;
    }

}
