package com.example.sungshin.huddle.MakingAppointment.MapActivity;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sungshin.huddle.DateObject;
import com.example.sungshin.huddle.FriendObject;
import com.example.sungshin.huddle.MainActivity;
import com.example.sungshin.huddle.MakingAppointment.CalendarActivity.MakingAppointmentActivity2;
import com.example.sungshin.huddle.MakingAppointment.MakingAppointmentActivity4;
import com.huddle.huddle.R;

import net.daum.mf.map.api.CameraUpdateFactory;
import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapPointBounds;
import net.daum.mf.map.api.MapReverseGeoCoder;
import net.daum.mf.map.api.MapView;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

//지도 API implements
public class MakingAppointmentActivity3 extends AppCompatActivity implements MapView.MapViewEventListener, MapView.POIItemEventListener, MapReverseGeoCoder.ReverseGeoCodingResultListener {
    //지도 API 관련 변수
    String pointedAddressString = null;
    MapPoint currentPoint;
    MapView mapView;    // map 띄울 view
    GPSActivity gps;    //gps activity의 객체
    double nowlat, nowlon;
    String addressString = null;
    EditText inputAddress;
    Button search;
    double convertedLat, convertedLon; //위,경도 합쳐진 좌표 스트링 분리해서 받은 것

    // 상단버튼 변수
    Button btnwherebefore, btn3Cancle, btn3Complete, btnwherenext;

    DateObject datas;
    FriendObject friendObject;

    boolean select = false; //지도 선택 여부 _ 초기 미선택
    //#
    Button btnma3loc;

    boolean locCheck = false; // 위치 확정 체크 여부
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_making_appointment3);
        //상단 버튼 이동, 하단 버튼
        btnwherebefore = (Button) findViewById(R.id.btnwherebefore);
        btnwherenext = (Button) findViewById(R.id.btnwherenext);
        btn3Cancle = (Button) findViewById(R.id.btn3Cancle);
        //#
        btnma3loc = (Button) findViewById(R.id.btnma3loc);

        //지도 API 함수
        inputAddress = (EditText) findViewById(R.id.inputAddress);  //주소 검색 바
        search = (Button) findViewById(R.id.search);

        //#
        datas = (DateObject) getIntent().getSerializableExtra("store");
        friendObject = (FriendObject) getIntent().getSerializableExtra("storefriend");

        //지도 생성
        final MapView mapView = new MapView(this);
        mapView.setDaumMapApiKey("80f6c16c393194af886d9b904b9c7acf");

        mapView.setMapViewEventListener(this);
        mapView.setPOIItemEventListener(this);

        final ViewGroup mapViewContainer = (ViewGroup) findViewById(R.id.map_view);
        mapViewContainer.addView(mapView);

        Toast.makeText(getApplicationContext(), "만나고 싶은 장소를 검색 혹은 꾹 눌러주세요!", Toast.LENGTH_LONG).show();

        btnwherebefore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MakingAppointmentActivity3.this, MakingAppointmentActivity2.class);
                startActivity(intent);
                mapViewContainer.removeView(mapView);
                finish();

            }
        });

        btnwherenext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MakingAppointmentActivity3.this, MakingAppointmentActivity4.class);
                intent.putExtra("locSelect", select);
                intent.putExtra("store", datas);
                intent.putExtra("storefriend", friendObject);
                intent.putExtra("address", addressString);
                intent.putExtra("lat", convertedLat);
                intent.putExtra("lon", convertedLon);

                //선택했던 안선택했던, 앞에서부터 쌓아온 데이터들 보내주기
                startActivity(intent);
                mapViewContainer.removeView(mapView);
                finish();
            }
        });


        btn3Cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MakingAppointmentActivity3.this, MainActivity.class);
                startActivity(intent);
                mapViewContainer.removeView(mapView);
                finish();
            }
        });

//        btn3Complete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                if(locCheck){
//                    select = false;
//                    addressString = "";
//                    convertedLat = 0;
//                    convertedLon = 0;
//                }
//                Intent intent = new Intent(MakingAppointmentActivity3.this, MakingAppointmentActivity4.class);
//
//                intent.putExtra("locSelect", select);
//                intent.putExtra("store", datas);
//                intent.putExtra("storefriend", friendObject);
//                intent.putExtra("address", addressString);
//                intent.putExtra("lat", convertedLat);
//                intent.putExtra("lon", convertedLon);
//
//                //선택했던 안선택했던, 앞에서부터 쌓아온 데이터들 보내주기
//
//                mapViewContainer.removeView(mapView);
//                startActivity(intent);
//                finish();
//            }
//        });


        search.setOnClickListener(new OnClickListener() {     // 검색버튼 클릭 이벤트 리스너
            //아래 OnClickListener !
        });

        btnma3loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "선택 위도 : " + convertedLat + "\n선택 경도 : " + convertedLon + "\n선택 주소 " + addressString, Toast.LENGTH_SHORT).show();
                locCheck = true;
            }
        });

    }

    private class OnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            mapView.removeAllPOIItems();
            final String query = inputAddress.getText().toString();
            Log.d("MyTag", "qurey" + query);
            if (query == null || query.length() == 0) {
                showToast("주소를 입력하세요.");
                return;
            }
            hideSoftKeyboard(); // 키보드 숨김


            // editText에 입력받아온 주소를 기반으로 스트링으로 변환, 지도에 다시 마커로 표시
            String lat_lon_Input = getAddress(MakingAppointmentActivity3.this, query);

            addressString = lat_lon_Input; //위도 경도 합쳐져서 나온 스트링
            String[] arr_Input = lat_lon_Input.split("\\,");
            convertedLat = Double.parseDouble(arr_Input[0]);
            convertedLon = Double.parseDouble(arr_Input[1]);

            mapView.setMapCenterPointAndZoomLevel(MapPoint.mapPointWithGeoCoord(convertedLat, convertedLon), 1, true);
            mapView.zoomIn(true);
            MapPOIItem marker = new MapPOIItem();
            marker.setItemName("New Marker");
            marker.setTag(1);
            marker.setMapPoint(MapPoint.mapPointWithGeoCoord(convertedLat, convertedLon));
            //기본제공 선택되었을 시 마커모양 및 색깔
            marker.setMarkerType(MapPOIItem.MarkerType.RedPin);

            mapView.addPOIItem(marker);

            if (select == false)
                select = true;
            else
                select = false;

        }

        private void showToast(final String text) {

            runOnUiThread(new Runnable() {

                String query = inputAddress.getText().toString();

                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), query, Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    private void showResult(List<Item> itemList) {
        MapPointBounds mapPointBounds = new MapPointBounds();
        Log.d("MyTag", "show Result 실행");

        for (int i = 0; i < itemList.size(); i++) {
            Item item = itemList.get(i);

            MapPOIItem poiItem = new MapPOIItem();
            poiItem.setItemName(item.title);
            poiItem.setTag(i);
            MapPoint mapPoint = MapPoint.mapPointWithGeoCoord(item.latitude, item.longitude);
            poiItem.setMapPoint(mapPoint);
            mapPointBounds.add(mapPoint);
            poiItem.setMarkerType(MapPOIItem.MarkerType.CustomImage);
            // poiItem.setCustomImageResourceId(R.drawable.map_pin_blue);
            poiItem.setSelectedMarkerType(MapPOIItem.MarkerType.CustomImage);
            //  poiItem.setCustomSelectedImageResourceId(R.drawable.map_pin_red);
            poiItem.setCustomImageAutoscale(false);
            poiItem.setCustomImageAnchor(0.5f, 1.0f);

            mapView.addPOIItem(poiItem); /////////////////////////


        }

        mapView.moveCamera(CameraUpdateFactory.newMapPointBounds(mapPointBounds));

        MapPOIItem[] poiItems = mapView.getPOIItems();
        if (poiItems.length > 0) {
            mapView.selectPOIItem(poiItems[0], false);
        }
    }

    private void hideSoftKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(inputAddress.getWindowToken(), 0);
    }

    @Override
    public void onPause() {
        super.onPause();
        gps.stopUsingGPS();
    }


    public void onClick(View view) {

        mapView.removeAllPOIItems(); //기존 검색결과 삭제
        onMapViewInitialized(mapView);

    }


    public void initGPS(boolean init) {
        gps = new GPSActivity(MakingAppointmentActivity3.this, 1); //????

        nowlat = gps.getLatitude();
        nowlon = gps.getLongitude();

        convertedLat = nowlat;
        convertedLon = nowlon;
        addressString = getAddress(this, convertedLat, convertedLon);
    }


////////////////////// MapView EventListener

    @Override
    public void onMapViewInitialized(MapView mapView) {
        this.mapView = mapView;
        initGPS(true);
        //지도 내 중심점 변경 + 줌 레벨 변경 <- 중심점에 현재 위치 넣어주면 좋겠지
        mapView.setMapCenterPointAndZoomLevel(MapPoint.mapPointWithGeoCoord(nowlat, nowlon), 1, true);
        mapView.zoomIn(true);

        MapPOIItem defaultMarker = new MapPOIItem();
        defaultMarker.setItemName("First Marker");
        defaultMarker.setTag(0);
//        defaultMarker.setMapPoint(MapPoint.mapPointWithGeoCoord(nowlat, nowlon));
        defaultMarker.setMapPoint(MapPoint.mapPointWithGeoCoord(convertedLat, convertedLon));

        //기본제공 선택되었을 시 마커모양 및 색깔
        defaultMarker.setMarkerType(MapPOIItem.MarkerType.RedPin);

        mapView.addPOIItem(defaultMarker);

        currentPoint = MapPoint.mapPointWithGeoCoord(nowlat, nowlon);


    }

    @Override
    public void onMapViewCenterPointMoved(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewZoomLevelChanged(MapView mapView, int i) {

    }

    @Override
    public void onMapViewSingleTapped(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewDoubleTapped(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewLongPressed(MapView mapView, MapPoint mapPoint) {

        if (select == false)
            select = true;
        else
            select = false;

        this.mapView = mapView;

        mapView.removeAllPOIItems(); // 기존 결과 삭제

        MapPOIItem marker = new MapPOIItem();
        marker.setItemName("난 여기서 만나고 싶어! 눌러주세요~");
        marker.setTag(0);
        marker.setMapPoint(mapPoint);
        marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
        mapView.addPOIItem(marker);
        currentPoint = mapPoint;

        MapReverseGeoCoder mapGeoCoder = new MapReverseGeoCoder("80f6c16c393194af886d9b904b9c7acf", currentPoint, this, this);
        mapGeoCoder.startFindingAddress();

        convertedLat = currentPoint.getMapPointGeoCoord().latitude;
        convertedLon = currentPoint.getMapPointGeoCoord().longitude;
        addressString = getAddress(MakingAppointmentActivity3.this, convertedLat, convertedLon);


    }

    @Override
    public void onMapViewDragStarted(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewDragEnded(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewMoveFinished(MapView mapView, MapPoint mapPoint) {

    }

    /////////////////////////////////////// POI Item Event Listener
    @Override
    public void onPOIItemSelected(MapView mapView, MapPOIItem mapPOIItem) {

    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem) {
        // 말풍선 눌렀을 때

//        this.mapView = mapView;
//        MapReverseGeoCoder mapGeoCoder = new MapReverseGeoCoder("80f6c16c393194af886d9b904b9c7acf", currentPoint, this, this);
//        mapGeoCoder.startFindingAddress();
//        Double latitude = currentPoint.getMapPointGeoCoord().latitude;
//        Double longitude = currentPoint.getMapPointGeoCoord().longitude;
//
//        addressString = getAddress(MakingAppointmentActivity3.this, latitude, longitude);
//        //addressString = getAddress(MapActivity.this, nowlat, nowlon);
//
//        String lat_lon = getAddress(MakingAppointmentActivity3.this, addressString);
//        // Toast.makeText(this, lat_lon, Toast.LENGTH_LONG).show();
//
//        String[] arr = lat_lon.split("\\,");
//        convertedLat = Double.parseDouble(arr[0]);
//        convertedLon = Double.parseDouble(arr[1]);

        //
        // Toast.makeText(this, addressString+"A" + convertedLat +"\t"+ convertedLon, Toast.LENGTH_LONG).show();


    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem, MapPOIItem.CalloutBalloonButtonType calloutBalloonButtonType) {

    }

    @Override
    public void onDraggablePOIItemMoved(MapView mapView, MapPOIItem mapPOIItem, MapPoint mapPoint) {

    }


    public String getAddress(Context mContext, double lat, double lon) {
        String address = null;
        Geocoder geocoder = new Geocoder(mContext);
        List<Address> list = null;

        try {
            list = geocoder.getFromLocation(lat, lon, 1); //위도, 경도, 얻어올 값의 개수
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("getAddress", "입출력 오류 - 서버에서 주소변환시 에러 발생");
        }

        if (list == null) {
            Log.e("getAddress", "주소 데이터 얻기 실패");
            Toast.makeText(getApplicationContext(), "해당되는 주소 정보는 없습니다", Toast.LENGTH_LONG).show();
            return null;
        }

        if (list.size() > 0) {
            Address addr = list.get(0);
            //address = String.valueOf(addr);
            address = addr.getCountryName() + " " + addr.getAdminArea() + " " +
                    addr.getLocality() + " " + addr.getThoroughfare() + " " + addr.getFeatureName();

        }

        return address;
    }

    public String getAddress(Context mContext, String address) {
        String xy = null;
        Geocoder coder = new Geocoder(mContext);
        try {
            List<Address> addrList = coder.getFromLocationName(address, 3);
            Iterator<Address> addrs = addrList.iterator();

            String infoAddr = null;

            double lat = 0.0;
            double lon = 0f;

            while (addrs.hasNext()) {
                Address loc = addrs.next();
                //infoAddr += String.format("Coord : %f. %f", loc.getLatitude(), loc.getLongitude());
                lat = loc.getLatitude();
                lon = loc.getLongitude();
                xy = lat + "," + lon;
            }

            final String gURIForm = String.format("geo: %f. %f", nowlat, nowlon);
            Uri gURI = Uri.parse(gURIForm);

        } catch (IOException e) {

        }
        Toast.makeText(this, xy, Toast.LENGTH_LONG).show();

        return xy;
    }


    //Reverse Geo-Coding Interface
    @Override
    public void onReverseGeoCoderFoundAddress(MapReverseGeoCoder mapReverseGeoCoder, String addressString) {
        // 주소를 찾은 경우
        pointedAddressString = addressString;
    }

    @Override
    public void onReverseGeoCoderFailedToFindAddress(MapReverseGeoCoder mapReverseGeoCoder) {
        pointedAddressString = "못 찾음";
    }

}