package com.example.sungshin.huddle.DetailPage.PlaceShow;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.sungshin.huddle.DetailPage.ParticipantsDataList;
import com.huddle.huddle.R;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapReverseGeoCoder;
import net.daum.mf.map.api.MapView;

import java.util.ArrayList;

/**
 * Created by 손영호 on 2016-12-30.
 */
public class PlaceFragment extends Fragment implements MapView.MapViewEventListener, MapView.POIItemEventListener, MapReverseGeoCoder.ReverseGeoCodingResultListener {

    View view;

    MapView mapView; //map 띄울 view
    String place = "", stringLon = "", stringLat = ""; //우선 먼저 받아올 스트링 변수들
    //double lat, lon; //받아온 위,경도 double로 넣어줄 곳

    ArrayList<ParticipantsDataList> participantsData;

    detailLocationData detailLocationData;
    ArrayList<detailLocationData> list = new ArrayList<detailLocationData>();
    ;
    //pin
    ArrayList<MapPOIItem> markers;

    public PlaceFragment(ArrayList<ParticipantsDataList> participantsData) {

        System.out.println(participantsData.get(0).latitude + " " + participantsData.get(0).longitude);

        this.participantsData = participantsData;

        if (participantsData.isEmpty()) {
            System.err.print("data set is empty");
        }

        markers = new ArrayList<MapPOIItem>(participantsData.size());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_place, container, false);
        //지도 생성
        mapView = new MapView(getActivity());
        mapView.setDaumMapApiKey("80f6c16c393194af886d9b904b9c7acf");
        mapView.setMapViewEventListener(this);
        mapView.setPOIItemEventListener(this);
        //지도 올리고, 띄움
        ViewGroup mapViewContainer = (ViewGroup) view.findViewById(R.id.map_view_detail);
        mapViewContainer.addView(mapView);
        //data 개수 파악
        int itemLength = getItemCount();
//        list = new ArrayList<detailLocationData>();

        if (itemLength == 0)
            Toast.makeText(getActivity().getApplicationContext(), "실패", Toast.LENGTH_SHORT).show();
        else {
            for (int i = 0; i < itemLength; i++) {
                getData(i); //위,경도 및 주소 받고, 객체 넣어서, arraylist 저장
                //      markers.add(new MapPOIItem());
                markingMapView(i); //list 저장된 개수 만큼 마킹
            }


        }


        return view;
    }

    public int getItemCount() {
        return (participantsData != null) ? participantsData.size() : 0;
    }

    @Override
    public void onStop() {
        super.onStop();
        view.invalidate();
        // 지도 지워줘야해
    }

    public void getData(int itemposition) {

        stringLat = participantsData.get(itemposition).latitude;
        stringLon = participantsData.get(itemposition).longitude;
        place = participantsData.get(itemposition).place;
//        Log.e("@@@@@@@@@@@@@@@@@@@@22",itemposition + "//" + stringLat);
//        Log.e("@@@@@@@@@@@@@@@@@@@@22",itemposition + "//" + participantsData.get(itemposition).latitude);

        if (stringLat == null || stringLon == null) {
            String latNull = null, lonNull = null;
            detailLocationData = new detailLocationData(place, latNull, lonNull);
            list.add(detailLocationData);

//            Toast.makeText(getActivity().getApplicationContext(), "stringLat or stringLon <- null", Toast.LENGTH_SHORT).show();
            Log.d("PlaceFragment", "stringLat  <- null");
        } else {
            detailLocationData = new detailLocationData(place, stringLat, stringLon);
            System.out.println("### 서버로부터" + detailLocationData.lat + "구분" + detailLocationData.lon);
            System.out.println("### 받아서 넣은" + stringLat + "구분" + stringLon);
            System.out.println("### detailLocationData" + detailLocationData.lat + "구분" + detailLocationData.lon);
            list.add(detailLocationData);
        }


        System.out.println("### list" + itemposition + "         " + list.get(itemposition).lat + "구분" + list.get(itemposition).lon);

    }


    private void markingMapView(int num) {
        //mapView.setMapCenterPointAndZoomLevel(MapPoint.mapPointWithGeoCoord(list.get(num).lat, list.get(num).lon), 1, true);
        //mapView.zoomIn(true);

        if (list.get(num).lat == null || list.get(num).lon == null) {
            MapPOIItem newItemNull = new MapPOIItem();
            newItemNull.setItemName(num + "번째 참여자");
            newItemNull.setTag(num);
        } else {
//            MapPOIItem newItem = new MapPOIItem();
//            newItem.setItemName(num + "번째 참여자");
//            newItem.setTag(num);
//
//            newItem.setMapPoint(MapPoint.mapPointWithGeoCoord(list.get(num).lat, list.get(num).lon));
//            newItem.setMarkerType(MapPOIItem.MarkerType.RedPin);
//
//            markers.add(newItem);
            MapPOIItem marker = new MapPOIItem();
            marker.setItemName(num + "번째 참여자");
            marker.setTag(num);
            marker.setMapPoint(MapPoint.mapPointWithGeoCoord(list.get(num).lat.doubleValue(), list.get(num).lon.doubleValue()));
            //    marker.setMapPoint(MapPoint.mapPointWithGeoCoord( 126.9606138,37.494805));
            Log.d("Debug", list.get(num).lon.toString() + " " + list.get(num).lat.toString());
            marker.setMarkerType(MapPOIItem.MarkerType.RedPin);

            //      mapView.addPOIItem(marker);
            markers.add(marker);
        }


    }

    @Override
    public void onReverseGeoCoderFoundAddress(MapReverseGeoCoder mapReverseGeoCoder, String s) {

    }

    @Override
    public void onReverseGeoCoderFailedToFindAddress(MapReverseGeoCoder mapReverseGeoCoder) {

    }

    @Override
    public void onMapViewInitialized(MapView mapView) {
        this.mapView = mapView;

        mapView.setMapCenterPointAndZoomLevel(MapPoint.mapPointWithGeoCoord(37.5061058, 127.0638233), 1, true);
        if (!markers.isEmpty()) {
            mapView.setMapCenterPointAndZoomLevel(MapPoint.mapPointWithGeoCoord(markers.get(0).getMapPoint().getMapPointGeoCoord().latitude, markers.get(0).getMapPoint().getMapPointGeoCoord().longitude), 1, true);
        }
        mapView.zoomIn(true);
        Log.d("Debug", "markers size is " + markers.size());
        for (MapPOIItem mapPOIItem : markers) {
            mapView.addPOIItem(mapPOIItem);
            Log.d("Debug", mapPOIItem.getItemName().toString());
        }
        mapView.fitMapViewAreaToShowAllPOIItems();
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

    @Override
    public void onPOIItemSelected(MapView mapView, MapPOIItem mapPOIItem) {

    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem) {

    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem, MapPOIItem.CalloutBalloonButtonType calloutBalloonButtonType) {

    }

    @Override
    public void onDraggablePOIItemMoved(MapView mapView, MapPOIItem mapPOIItem, MapPoint mapPoint) {

    }
}