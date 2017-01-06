package com.example.sungshin.huddle.MakingAppointment.MapActivity;


import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
/**
 * Created by LG on 2016-12-30.
 */

public class GPSActivity extends Service implements LocationListener {

    private final Context mContext;

    //현재 GPS 사용유무
    boolean isGPSEnabled = false;

    //네트워크 사용유무
    boolean isNetworkEnabled = false;

    //현재 GPS 상태값
    boolean isGetLocation = false;

    private Location location;

    //위도
    private double lat;
    //경도
    private double lon;

    int mFragmentID = 0;
    protected LocationManager locationManager;

    //최소 GPS 정보 업데이트 거리 10미터
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;
    // 최소 GPS 정보 업데이트 시간 밀리세컨이므로 1분
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1;

    public GPSActivity(Context context, int fragmentID) {
        this.mContext = context;
        this.mFragmentID = fragmentID;
        getLocation();
    }

    public Location getLocation() {

        try {
            locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);

            //GPS 정보 받아오기
            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            //현재 네트워크 상태 값 알아오기
            isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGPSEnabled && !isNetworkEnabled) {
                //GPS 및 네트워크 모두 이용불가 시 코드
            } else {
                this.isGetLocation = true; // 현재 GPS상태 값 true로 변경
                //네트워크 정보로부터 위치값 가져오기
                if (isNetworkEnabled) { //네트워크가 이용가능 하다면 location update 요청을 할 것
                    locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES,
                            this);


                    if (locationManager != null) {
                        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location != null) {
                            //위도 경도 재 저장
                            lat = location.getLatitude();
                            lon = location.getLongitude();
                        }
                    }
                }

                if (isGPSEnabled) { // GPS 이용 가능할 때
                    if (location == null) { //location 이 null (사용가능한 위치 정보 제공자가 없을 때)
                        locationManager.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER,
                                MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES,
                                this); //업데이트

                        if (locationManager != null) { //location 이 null 이고, 이전에 getSystemService가 있었다면(=최근 location 이 있었다면!)
                            //최근 location 정보를 불러온다
                            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                            if (location != null) { //최근께 쓰여지고 난 후
                                lat = location.getLatitude();
                                lon = location.getLongitude();

                            }
                        }
                    }

                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return location;
    }


    // GPS 종료
    public void stopUsingGPS() {
        if (locationManager != null)
            locationManager.removeUpdates(GPSActivity.this);
    }

    // 위도 값 받아오기
    public double getLatitude() {
        if (location != null) //이전에 location이 업로드 되었었다면
            lat = location.getLatitude();

        return lat;
    }

    // 경도 값 받아오기
    public double getLongitude() {
        if (location != null)
            lon = location.getLongitude();

        return lon;
    }

    // GPS 혹은 wifi 켜있는지 확인
    public boolean isGetLocation() {
        return this.isGetLocation;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
