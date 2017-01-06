package com.example.sungshin.huddle.EditActivity;

import android.app.Application;
import android.util.Log;

/**
 * Created by SAMSUNG on 2017-01-05.
 */

public class position extends Application {

    private static position instance = null;

    public String place;
    public String longitude;
    public String latitude;

    public position()
    {
        this.place = "";
        this.longitude = "";
        this.latitude = "";
    }

    public void setPosition(String place, String longitude, String latitude){

        Log.i("Jinsub", place);
        Log.i("Jinsub", longitude);
        Log.i("Jinsub", latitude);

        this.place = place;
        this.longitude = longitude;
        this.latitude = latitude;
    }
    public String getplace(){
        return place;
    }
    public String getlongitude(){
        return longitude;
    }
    public String getlatitude(){
        return latitude;
    }

    public static synchronized position getInstance(){
        if(null == instance){
            instance = new position();
        }
        return instance;
    }

}