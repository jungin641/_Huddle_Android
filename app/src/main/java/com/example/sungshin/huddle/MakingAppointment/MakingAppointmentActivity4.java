package com.example.sungshin.huddle.MakingAppointment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sungshin.huddle.Application.ApplicationController;
import com.example.sungshin.huddle.DateObject;
import com.example.sungshin.huddle.Dialog.HelpDialog2;
import com.example.sungshin.huddle.FriendObject;
import com.example.sungshin.huddle.MainActivity;
import com.example.sungshin.huddle.MakingAppointment.CalendarActivity.CalendarMonthAdapter;
import com.example.sungshin.huddle.MakingAppointment.CalendarActivity.CalendarMonthView;
import com.example.sungshin.huddle.MakingAppointment.MapActivity.MakingAppointmentActivity3;
import com.example.sungshin.huddle.Network.NetworkService;
import com.huddle.huddle.R;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MakingAppointmentActivity4 extends AppCompatActivity implements MapView.MapViewEventListener, MapView.POIItemEventListener {
    Button btnconfirmbefore, btnwhenConfirm, btnwhereConfirm, btn4Cancel, btn4Complete, btnCompleteRoomCreate;
    EditText editRoomName, editRoomContent;
    ScrollView scrollView;
    CheckBox checkWhenFix, checkWhereFix;
    //달력 관련 변수
    ArrayList<String> temp1;
    String stringRoomName = "", stringRoomContent = "";
    //Button btnRoomNameConfirm;

    //유리: 2017/01/01 추가
    MapView mapView;    // map 띄울 view
    double lat, lon;
    String address;
    //레트로 핏 관련변수
    NetworkService service;

    //사용자 아이디 전역변수

    String user_id = "";
    TextView personText;

    public MakingAppointmentActivity4() {
        this.user_id = ApplicationController.getInstance().myInfo.id;
    }


    //혜민 :2017/01/01추가 켈린더 관련 변수
    CalendarMonthView monthView;
    // 월별 캘린더 어댑터
    CalendarMonthAdapter monthViewAdapter;
    //월을 표시하는 텍스트뷰
    TextView monthText;
    //현재 연도
    int curYear;
    //현재 월
    int curMonth;
    DateObject datas;
    Button monthNext;
    Button monthPrevious;

    //전화번호부 등록 시 필요한 변수
    FriendObject datasFriend;
    ArrayList<String> friendDatas;

    public String temp = "";

    int when_to_fix = 1; //default 확정
    int where_to_fix = 1; //default 확정
    int whenCheck = 0, whereCheck = 0; //확정 버튼 counter
    //유리 20170105
    boolean select;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_making_appointment4);

        temp1 = new ArrayList<>();
        scrollView = (ScrollView) findViewById(R.id.scrollView);

        btnconfirmbefore = (Button) findViewById(R.id.btnconfirmbefore);

        editRoomName = (EditText) findViewById(R.id.editRoomName);
        editRoomContent = (EditText) findViewById(R.id.editRoomContent);

        //   btnwhenConfirm = (Button) findViewById(R.id.btnwhenConfirm);
        //   btnwhereConfirm = (Button) findViewById(R.id.btnwhereConfirm);

        btn4Cancel = (Button) findViewById(R.id.btn4Cancel);
        //btn4Complete = (Button) findViewById(R.id.btn4Complete);


        //혜민 : 2017/01/01 켈린더 관련 변수

        monthNext = (Button) findViewById(R.id.monthNext);
        monthPrevious = (Button) findViewById(R.id.monthPrevious);
        monthView = (CalendarMonthView) findViewById(R.id.monthView);
        monthText = (TextView) findViewById(R.id.monthText);
        personText = (TextView) findViewById(R.id.persontext);

        //지은 레트로핏 방만들기 완료버튼
        btnCompleteRoomCreate = (Button) findViewById(R.id.btnCompleteRoomCreate);

        service = ApplicationController.getInstance().getNetworkService();

        //전화번호부 등록 -영호
        datasFriend = (FriendObject) getIntent().getSerializableExtra("storefriend");
        personText.setText(String.valueOf(datasFriend.storePhoneNums.size()) + " 명");
        friendDatas = new ArrayList<>();
        for (int i = 0; i < datasFriend.storePhoneNums.size(); i++) {
            friendDatas.add(datasFriend.storePhoneNums.get(i).id);
            Log.i("myTag", "id before" + friendDatas.get(i));
        }

        datas = (DateObject) getIntent().getSerializableExtra("store");


        for (int i = 0; i < datas.storeDates.size(); i++) {
            //String temp= "";

            temp = String.valueOf(datas.storeDates.get(i).year);

            if (datas.storeDates.get(i).month < 10)
                temp = temp + "0" + datas.storeDates.get(i).month;
            else
                temp = temp + datas.storeDates.get(i).month;


            if (datas.storeDates.get(i).day < 10)
                temp = temp + "0" + datas.storeDates.get(i).day;
            else
                temp = temp + datas.storeDates.get(i).day;

            temp1.add(i, temp);


            // Log.i("myTag" ,"temp " +  temp);
        }
        Log.i("myTag", "temp " + temp1);

        /**
         * 캘린더
         */

        // 월별 캘린더 뷰 객체 참조
        monthViewAdapter = new CalendarMonthAdapter(this, datas.storeDates);
        monthView.setAdapter(monthViewAdapter);

        monthView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //;
            }
        });

        setMonthText();

        monthView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //;
            }
        });

        // 이전 월로 넘어가는 이벤트 처리
        monthPrevious.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                monthViewAdapter.changeMonthCheck = true;
                monthViewAdapter.setPreviousMonth();
                monthViewAdapter.notifyDataSetChanged();

                setMonthText();
            }
        });

        // 다음 월로 넘어가는 이벤트 처리
        monthNext.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                monthViewAdapter.changeMonthCheck = true;
                monthViewAdapter.setNextMonth();
                monthViewAdapter.notifyDataSetChanged();

                setMonthText();
            }
        });


        //유리 :2017/01/01 추가
        Intent intent = getIntent();
        lat = intent.getExtras().getDouble("lat");
        lon = intent.getExtras().getDouble("lon");
        address = intent.getExtras().getString("address");
        select = intent.getExtras().getBoolean("locSelect");

        final MapView mapView = new MapView(MakingAppointmentActivity4.this);
        mapView.setDaumMapApiKey("80f6c16c393194af886d9b904b9c7acf");

        mapView.setMapViewEventListener(MakingAppointmentActivity4.this);
        mapView.setPOIItemEventListener(MakingAppointmentActivity4.this);

        final ViewGroup mapViewContainer = (ViewGroup) findViewById(R.id.map_view4);
        mapViewContainer.addView(mapView);
        onMapViewInitialized(mapView);


        // 버튼의 OnClickListener들
        btnconfirmbefore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MakingAppointmentActivity4.this, MakingAppointmentActivity3.class);
                mapViewContainer.removeView(mapView);
                startActivity(intent);
                HelpDialog2 help = new HelpDialog2();
                help.show(getFragmentManager(), "help_dialog");

            }
        });


        //지은 :20170103 추가 방 만들기 완료버튼
        btnCompleteRoomCreate.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {


                if (editRoomName.length() == 0 || editRoomName.toString() == "") {
                    Toast.makeText(getApplicationContext(), "방 제목을 입력하시지 않으셨습니다. 입력해주세요!", Toast.LENGTH_LONG).show();
                } else {


                    //지은: 20170105
                    checkWhenFix = ((CheckBox) findViewById(R.id.checkWhenFix));
                    checkWhereFix = ((CheckBox) findViewById(R.id.checkWhereFix));
                    final boolean isWhenFix = checkWhenFix.isChecked();
                    final boolean isWhereFix = checkWhereFix.isChecked();

                    Log.i("myTag", "실행됨1");
                    roomCreateDataList data = new roomCreateDataList();
                    data.days = new ArrayList<String>();
                    data.meeting = data.new meeting();
                    data.position = data.new position();
                    data.participant = new ArrayList<String>();


                    if (temp1.size() == 0) {
                        data.days = null;

                    } else {
                        for (int i = 0; i < temp1.size(); i++) {
                            data.days.add(temp1.get(i));
                        }
                    }

                    //meeting
                    data.meeting.host_id = user_id;
                    data.meeting.title = editRoomName.getText().toString();
                    data.meeting.text = editRoomContent.getText().toString();

                    Log.i("myTag", "editRoomContent.getText().toString()" + editRoomContent.getText().toString());
                    data.meeting.is_open = 0;
                    if (isWhenFix) {
                        data.meeting.when_fix = 1;
                    } else {
                        data.meeting.when_fix = 0;
                    }

                    Log.i("myTag", "isWhenFix" + isWhenFix);
                    if (isWhereFix) {
                        data.meeting.where_fix = 1;
                    } else {
                        data.meeting.where_fix = 0;
                    }

                    Log.i("myTag", "isWhenFix" + isWhereFix);
                    Log.i("myTag", "방정보 성공");
                    data.position.place = address;
                    data.position.longitude = Double.toString(lat);
                    data.position.latitude = Double.toString(lon);
                    Log.i("myTag", "data.position.place)" + data.position.place);

                    //영호 전화id 넘기느 부분
                    if (friendDatas.size() == 0) {
                        data.participant = null;

                    } else {
                        for (int i = 0; i < friendDatas.size(); i++) {
                            data.participant.add(friendDatas.get(i));
                            Log.i("myTag", "id" + data.participant.get(i));
                        }
                    }

                    Log.i("myTag", " Call<roomCreateResult> 이전");

                    Call<roomCreateResult> requestCreateResult = service.requestRoomCreate(data);
                    Log.i("myTag", " Call<roomCreateResult> 이후");

                    requestCreateResult.enqueue(new Callback<roomCreateResult>() {
                        @Override
                        public void onResponse(Call<roomCreateResult> call, Response<roomCreateResult> response) {

                            if (response.isSuccessful()) {

                                if (response.body().result.equals("SUCCESS")) {
                                    Log.i("myTag", response.body().result);

                                    Toast.makeText(getApplicationContext(), "방만들기 성공", Toast.LENGTH_SHORT).show();

                                    Intent intentMain = new Intent(MakingAppointmentActivity4.this, MainActivity.class);
                                    startActivity(intentMain);
                                    finish();



                            } else if (response.body().result.equals("FAIL")) {
                                //mProgressDialog.dismiss();
                                Toast.makeText(getApplicationContext(), "방 만들기 실패", Toast.LENGTH_SHORT).show();
                            } else {

                                Toast.makeText(getApplicationContext(), "예상치 못한 오류 발생", Toast.LENGTH_SHORT).show();
                            }
                        }

                    }

                    @Override
                    public void onFailure (Call < roomCreateResult > call, Throwable t){
                        Log.i("myTag", t.toString());

                        Toast.makeText(getApplicationContext(), "방만들기 완전 실패", Toast.LENGTH_SHORT).show();

                    }
                });
            }


        }
    }

    );

//        btnwhenConfirm.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ++whenCheck;
//                if (whenCheck % 2 == 0) {
//                    when_to_fix = 0; //미확정으로 변경
//                } else {
//                    when_to_fix = 1; //확정으로 변경
//                }
//            }
//        });
//
//        btnwhereConfirm.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ++whereCheck;
//                if (whereCheck % 2 == 0) {
//                    where_to_fix = 0; //미확정으로 변경
//                } else {
//                    where_to_fix = 1; //확정으로 변경
//                }
//            }
//        });

//        btn4Cancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Intent intent = new Intent(MakingAppointmentActivity4.this, MakingAppointmentActivity3.class);
//                startActivity(intent);
//                finish();
//            }
//        });

//        btn4Complete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (editRoomName.length() == 0 || editRoomName.toString() == "") {
//                    Toast.makeText(getApplicationContext(), "방 제목을 입력하시지 않으셨습니다. 입력해주세요!", Toast.LENGTH_LONG).show();
//                }
//                stringRoomName = editRoomName.toString();
//                stringRoomContent = editRoomContent.toString();
//                Toast.makeText(getApplicationContext(), "RoomName, RoomContent 저장 성공!", Toast.LENGTH_LONG).show();
//                if (editRoomName.length() > 0) {
//                    Intent intent = new Intent(MakingAppointmentActivity4.this, MainActivity.class);
//                    startActivity(intent);
//                    finish();
//
//                    //meeting =>  host_id, RoomName(title), RoomContent(text), 공개/비공개 여부(is_open), 날짜/장소 확인여부(when_to_fix, where_to_fix)
//                    //days => 선택한 날짜들 <= string 배열
//                    //position=> 선택한 좌표 (where<-한글주소, longitute, latitute) <=모두 string
//                    //같이 넘겨줘야해
//                }
//            }
//        });


}


    @Override
    public void onMapViewInitialized(MapView mapView) {

        this.mapView = mapView;

        mapView.removeAllPOIItems();

        //지도 내 중심점 변경 + 줌 레벨 변경 <- 중심점에 현재 위치 넣어주면 좋겠지
        mapView.setMapCenterPointAndZoomLevel(MapPoint.mapPointWithGeoCoord(lat, lon), 1, true);
        mapView.zoomIn(true);


        MapPOIItem ConfirmMarker = new MapPOIItem();
        ConfirmMarker.setItemName("Confirm Marker");
        ConfirmMarker.setTag(2);
        ConfirmMarker.setMapPoint(MapPoint.mapPointWithGeoCoord(lat, lon));
        //기본 제공 마커모양 및 색깔
        ConfirmMarker.setMarkerType(MapPOIItem.MarkerType.RedPin);

        mapView.addPOIItem(ConfirmMarker);

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

    //혜민 :2017/01/01
    private void setMonthText() {
        curYear = monthViewAdapter.getCurYear();
        curMonth = monthViewAdapter.getCurMonth();

        monthText.setText(curYear + "년 " + (curMonth + 1) + "월");
    }

}