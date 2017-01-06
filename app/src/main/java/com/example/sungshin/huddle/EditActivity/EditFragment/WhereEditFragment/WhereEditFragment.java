package com.example.sungshin.huddle.EditActivity.EditFragment.WhereEditFragment;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sungshin.huddle.EditActivity.EditMyOpinionResult;
import com.example.sungshin.huddle.EditActivity.position;
import com.huddle.huddle.R;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapReverseGeoCoder;
import net.daum.mf.map.api.MapView;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;


/**
 * Created by sungshin on 2017-01-02.
 */
public class WhereEditFragment extends Fragment implements MapView.MapViewEventListener, MapView.POIItemEventListener, MapReverseGeoCoder.ReverseGeoCodingResultListener{

    Button btnEditWhere, btnEditLocConfirm;
    EditText editWhereSearch;

    View view;
    MapView mapView;

    String place, stringLon, stringLat; //먼저 받아올 스트링 변수들
    Double lat, lon ;

    EditMyOpinionResult editMyOpinionResult;
    //  EditMyOpinionResult.position pos;

    MapPOIItem markerEdit, markerPre;
    MapPoint editPoint;

    //@@@
    position mposition;
    String[] arr_Input;

    public WhereEditFragment(EditMyOpinionResult editMyOpinionResult, position mposition){
        this.editMyOpinionResult = editMyOpinionResult;
        this.mposition = mposition;
        //pos = editMyOpinionResult.position;
        if(editMyOpinionResult == null)
            System.out.println("받아오는 EditMyOpinionResult가 null");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_edit_where, container, false);

//        place = pos.place;
//        stringLon = pos.longitude;
//        stringLat = pos.latitude;


        Toast.makeText(getActivity().getApplicationContext(), "T0T" + stringLat, Toast.LENGTH_SHORT).show();
        if(stringLat != null)
            lat = Double.parseDouble(stringLat);
        else
            lat = 37.0;

        if(stringLon != null)
            lon = Double.parseDouble(stringLon);
        else
            lon = 126.0;


        //지도생성
        mapView = new MapView(getActivity());
        mapView.setDaumMapApiKey("80f6c16c393194af886d9b904b9c7acf");

        mapView.setMapViewEventListener(this);
        mapView.setPOIItemEventListener(this);

        final ViewGroup mapViewContainer = (ViewGroup) view.findViewById(R.id.map_view_edit);
        mapViewContainer.addView(mapView);

        btnEditLocConfirm = (Button) view.findViewById(R.id.btnEditLocConfirm);
        btnEditWhere = (Button) view.findViewById(R.id.btnEditWhere);
        editWhereSearch = (EditText) view.findViewById(R.id.editWhereSearch);

        Toast.makeText(getActivity().getApplicationContext(), "위치를 변경할 곳을 검색 혹은 눌러주세요", Toast.LENGTH_SHORT).show();

        btnEditWhere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mapView.removeAllPOIItems();

                final String query = editWhereSearch.getText().toString();

                if (query == null || query.length() == 0) {
                    Toast.makeText(getActivity().getApplicationContext(), "주소를 입력하세요.", Toast.LENGTH_SHORT).show();
                    return;
                }


                // editText에 입력받아온 주소를 기반으로 스트링으로 변환, 지도에 다시 마커로 표시
                String lat_lon_Input = getAddress(getActivity().getApplicationContext(), query);

                //place = lat_lon_Input; //위도 경도 합쳐져서 나온 스트링
                String[] arr_Input = lat_lon_Input.split("\\,");
                lat = Double.parseDouble(arr_Input[0]);
                lon = Double.parseDouble(arr_Input[1]);

                place = getAddress(getActivity().getApplicationContext(), lat, lon);

                mapView.setMapCenterPointAndZoomLevel(MapPoint.mapPointWithGeoCoord(lat, lon), 1, true);
                mapView.zoomIn(true);
                MapPOIItem marker = new MapPOIItem();
                marker.setItemName("New Marker");
                marker.setTag(1);
                marker.setMapPoint(MapPoint.mapPointWithGeoCoord(lat, lon));
                //기본제공 선택되었을 시 마커모양 및 색깔
                marker.setMarkerType(MapPOIItem.MarkerType.RedPin);

                mapView.addPOIItem(marker);
            }
        });

        btnEditLocConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity().getApplicationContext(), "선택 위도 : " + lat +"\n선택 경도 : " + lon + "\n선택 주소 " + place, Toast.LENGTH_SHORT).show();

//                Log.i("Log_jinsub", place);
  //              Log.i("Log_jinsub", Double.toString(lon));
    //            Log.i("Log_jinsub", Double.toString(lat));
                mapViewContainer.removeAllViews();
                //여기서 세개 모아줄까?
                Toast.makeText(getActivity().getApplicationContext(), mposition.place + "//" + mposition.latitude, Toast.LENGTH_SHORT).show();

                //    Button btn_Complete = (Button)view.findViewById(R.id.btneditComplete);
                //    btn_Complete.setTag(mposition);

                position.getInstance().setPosition(place, Double.toString(lon), Double.toString(lat));
            }
        });

        return view;
    }


    @Override
    public void onResume(){
        super.onResume();
        return;
    }
    @Override
    public void onMapViewInitialized(MapView mapView) {
        this.mapView = mapView;

        if(lat == null || lon == null) //기존 것이랑 비교를 해주어야하나?
            mapView.setMapCenterPointAndZoomLevel(MapPoint.mapPointWithGeoCoord(37.5061058, 127.0638233), 3, true);
        else{
            mapView.removeAllPOIItems();
            mapView.setMapCenterPointAndZoomLevel(MapPoint.mapPointWithGeoCoord(lat, lon), 3, true);
            mapView.zoomIn(true);
            markerPre = new MapPOIItem();
            markerPre.setMapPoint(MapPoint.mapPointWithGeoCoord(lat, lon));
            markerPre.setItemName("기존 위치");
            markerPre.setTag(0);
            markerPre.setMarkerType(MapPOIItem.MarkerType.RedPin);
            mapView.addPOIItem(markerPre);
        }
    }

    @Override
    public void onMapViewCenterPointMoved(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewZoomLevelChanged(MapView mapView, int i) {

    }

    @Override
    public void onMapViewSingleTapped(MapView mapView, MapPoint mapPoint) {
        this.mapView = mapView;
        mapView.removeAllPOIItems(); // 기존 결과 삭제

        markerEdit = new MapPOIItem();
        markerEdit.setItemName("수정 위치");
        markerEdit.setTag(1);
        markerEdit.setMapPoint(mapPoint);
        markerEdit.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
        mapView.addPOIItem(markerEdit);
        editPoint = mapPoint;

        MapReverseGeoCoder mapGeoCoder = new MapReverseGeoCoder("80f6c16c393194af886d9b904b9c7acf", editPoint, this, getActivity());
        mapGeoCoder.startFindingAddress();

        lat = editPoint.getMapPointGeoCoord().latitude;
        lon = editPoint.getMapPointGeoCoord().longitude;
        place = getAddress(getActivity().getApplicationContext(), lat, lon);
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

    @Override
    public void onReverseGeoCoderFoundAddress(MapReverseGeoCoder mapReverseGeoCoder, String s) {
        place = s;
    }

    @Override
    public void onReverseGeoCoderFailedToFindAddress(MapReverseGeoCoder mapReverseGeoCoder) {

    }

    public String getAddress(Context mContext, Double lat, Double lon) {
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
            Toast.makeText(getActivity().getApplicationContext(), "해당되는 주소 정보는 없습니다", Toast.LENGTH_LONG).show();
            return null;
        }

        if (list.size() > 0) {
            Address addr = list.get(0);
            address = addr.getCountryName() + " " + addr.getAdminArea() + " " +
                    addr.getLocality() + " " + addr.getThoroughfare() + " " + addr.getFeatureName();
        }
        place = address;
        return place;
    }

    public String getAddress(Context mContext, String address) {
        String xy = null;
        Geocoder coder = new Geocoder(mContext);
        try {
            List<Address> addrList = coder.getFromLocationName(address, 3);
            Iterator<Address> addrs = addrList.iterator();

            String infoAddr = null;

            Double lat = 0.0;
            Double lon = 0.0;

            while (addrs.hasNext()) {
                Address loc = addrs.next();
                //infoAddr += String.format("Coord : %f. %f", loc.getLatitude(), loc.getLongitude());
                lat = loc.getLatitude();
                lon = loc.getLongitude();
                xy = lat + "," + lon;
            }

            final String gURIForm = String.format("geo: %f. %f", lat, lon);
            Uri gURI = Uri.parse(gURIForm);

        } catch (IOException e) {

        }
        Toast.makeText(getActivity().getApplicationContext(), xy, Toast.LENGTH_SHORT).show();

        return xy;
    }
}