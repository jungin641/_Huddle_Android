package com.example.sungshin.huddle.MakingAppointment.FriendListActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.sungshin.huddle.Application.ApplicationController;
import com.example.sungshin.huddle.DateObject;
import com.example.sungshin.huddle.FriendObject;
import com.example.sungshin.huddle.MainActivity;
import com.example.sungshin.huddle.MakingAppointment.CalendarActivity.MakingAppointmentActivity2;
import com.example.sungshin.huddle.MakingAppointment.DateModel.StoreDate;
import com.huddle.huddle.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MakingAppointmentActivity1 extends AppCompatActivity {

    Button btnwhonext ,btn1Complete,btn1Cancel;
    //친구목록 가져오기
    RecyclerView recyclerView;

    LinearLayoutManager mLayoutManager;
    FriendAdapter adapter;
    ArrayList<RealFriendList> mDatas;
    ArrayList<StorePhoneNum> datas;
    FriendObject friendObject;
    DateObject object;
    ArrayList<StoreDate> storeDates;
    String user_id = " ";
    RealFriendResult realFriendResult;
    String lat = null, lon = null, address = null;

    boolean select = false; // 지도 부분 미선택

    public MakingAppointmentActivity1(){
        this.user_id = ApplicationController.getInstance().myInfo.id;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_making_appointment1);

        btnwhonext = (Button) findViewById(R.id.btnwhonext);
        btn1Cancel = (Button) findViewById(R.id.btn1Cancel);
        mDatas = new ArrayList<RealFriendList>();
        System.out.print(user_id);

        //


        //친구 정보 넘기는 버튼 구현 - 영호
        btnwhonext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datas = adapter.getDatas();
                friendObject = new FriendObject();
                friendObject.setStoreDates(datas);
                Intent intent = new Intent(MakingAppointmentActivity1.this, MakingAppointmentActivity2.class);
                intent.putExtra("storefriend", friendObject);
                startActivity(intent);
                finish();
            }
        });

        //친구목록 가져오기
        recyclerView = (RecyclerView) findViewById(R.id.recylerView);

        //각 item의 크기가 일정할 경우 고정
        recyclerView.setHasFixedSize(true);

        //TODO LayoutManager 초기화
        // layoutManager 설정
        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);

        //userid 넘겼을 때 서버에서 친구 목록 받아오는 부분 - 영호
        Call<RealFriendResult> requestRealList = ApplicationController.getInstance().getNetworkService().requestReallist(user_id);
        requestRealList.enqueue(new Callback<RealFriendResult>() {
            @Override
            public void onResponse(Call<RealFriendResult> call, Response<RealFriendResult> response) {
                if(response.isSuccessful()){
                    System.out.println(response.body());
                    realFriendResult = response.body();
                    for(int i=0; i<realFriendResult.friend_list.size();i++) {
                        mDatas.add(new RealFriendList(response.body().friend_list.get(i).name, response.body().friend_list.get(i).profile, response.body().friend_list.get(i).id));
                        System.out.println(mDatas);
                    }
                    adapter = new FriendAdapter(mDatas, clickEvent);
                    recyclerView.setAdapter(adapter);
                }
            }
            @Override
            public void onFailure(Call<RealFriendResult> call, Throwable t) {
                Log.i("myTag", "failed" + t.toString());
            }
        });

        //showContacts(); <-있었음 옛날코드

//        btn1Complete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MakingAppointmentActivity1.this, MakingAppointmentActivity4.class);
//                datas = adapter.getDatas();
//                friendObject = new FriendObject();
//                friendObject.setStoreDates(datas);
//                object = new DateObject();
//                object.setStoreDates(storeDates);
//
//                datas = adapter.getDatas();
//                intent.putExtra("storefriend", friendObject);
//                Log.i("myTag", "sfriend == " + friendObject);
//                intent.putExtra("store", object);
//
//                intent.putExtra("address", address);
//                intent.putExtra("lat", lat);
//                intent.putExtra("lon", lon);
//                intent.putExtra("locSelect", select);
//
//                startActivity(intent);
//                finish();
//
//            }
//        });
        btn1Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MakingAppointmentActivity1.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public View.OnClickListener clickEvent = new View.OnClickListener() {
        public void onClick(View v) {
            int itemPosition = recyclerView.getChildPosition(v);
            String temp = mDatas.get(itemPosition).name;
            Toast.makeText(getApplicationContext(), temp, Toast.LENGTH_SHORT).show();

        }
    };

}

