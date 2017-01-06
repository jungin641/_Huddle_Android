package com.example.sungshin.huddle.DetailPage;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.sungshin.huddle.Application.ApplicationController;
import com.example.sungshin.huddle.DetailPage.PersonShow.PersonFragment;
import com.example.sungshin.huddle.DetailPage.PlaceShow.PlaceFragment;
import com.example.sungshin.huddle.DetailPage.WhenShow.WhenFragment;
import com.example.sungshin.huddle.EditActivity.EditActivity;
import com.example.sungshin.huddle.MainActivity;
import com.example.sungshin.huddle.Network.NetworkService;
import com.huddle.huddle.R;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by 손영호 on 2016-12-30.
 */
public class MainDetailActivity extends AppCompatActivity {

    //영호 : 레이아웃 관련변수
    private SectionsPagerAdapter mrSectionsPagerAdapter;
    private ViewPager mViewPager;
    private TabLayout tabLayout;
    Button floatingActionButtonEnter;
    //영호 : 프사 사진 박는 부분
    ImageView imgViewList;

    //유리 : 카카오톡 링크 관련 변수
    private Button btnKakaoLink, btnnextViewpager;
    private TextView txtLinkRoom, txtLinkRoomContent;
    private String stringLinkRoom, stringLinkRoomContent;


    DetailResult meetingRoomInfo;

    //지은: 변수추가 입력하러 가기
    Button btnInputMy;

    //지은 : 방 사진 바꾸러가기 관련변수
    final int REQ_CODE_SELECT_IMAGE = 100;
    Button btnChangeImg;
    String imgUrl = "";
    Uri data;
    NetworkService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_result);
        mViewPager = (ViewPager) findViewById(R.id.mViewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        floatingActionButtonEnter = (Button) findViewById(R.id.floatingButtonEnter);

        //영호 : 프사 사진 박는 부분
        imgViewList = (ImageView) findViewById(R.id.imgViewList);

        //지은 20170105: 방 사진 변경하러 가기

        //지은 : 미리 retrofit를 bulid 한 것을 가져온다.
        service = ApplicationController.getInstance().getNetworkService();


        btnnextViewpager = (Button) findViewById(R.id.btnnextViewpager);
        btnnextViewpager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(String.valueOf(meetingRoomInfo.participants.get(0).latitude) == null || String.valueOf(meetingRoomInfo.participants.get(0).latitude) == "0"){
                    meetingRoomInfo.participants.get(0).latitude = "36";
                    meetingRoomInfo.participants.get(0).longitude = "127";
                    Toast.makeText(getApplicationContext(), String.valueOf(meetingRoomInfo.participants.get(0).latitude), Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent = new Intent(MainDetailActivity.this, EditActivity.class);
                    intent.putExtra("my_meeting_id", String.valueOf(meetingRoomInfo.my_meeting_id));
                    startActivity(intent);
                }
//                Intent intent = new Intent(MainDetailActivity.this, EditActivity.class);
//                intent.putExtra("my_meeting_id", String.valueOf(meetingRoomInfo.my_meeting_id));
//                startActivity(intent);
            }
        });

        floatingActionButtonEnter.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(MainDetailActivity.this)
                        .setMessage("정말 이 모임방을 나가시겠어요?")
                        .setTitle("방 만들기 성공")
                        // .setView(image)
                        .setPositiveButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        }).setNegativeButton("나가기", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //방 나가는 코드


                                Call<roomDeleteResult> requestDeleteRoom = service.requestDeleteRoom(Integer.parseInt(String.valueOf(meetingRoomInfo.my_meeting_id)));
                                requestDeleteRoom.enqueue(new Callback<roomDeleteResult>() {
                                    @Override
                                    public void onResponse(Call<roomDeleteResult> call, Response<roomDeleteResult> response) {
                                        if (response.isSuccessful()) {

                                            if (response.body().result.equals("SUCCESS")) {
                                                Toast.makeText(getApplicationContext(), "방 나가기 성공", Toast.LENGTH_SHORT).show();

                                                Intent intent = new Intent(MainDetailActivity.this, MainActivity.class);
                                                startActivity(intent);
                                            } else if (response.body().result.equals("FAIL")) {
                                                Toast.makeText(getApplicationContext(), "방 나가기 실패", Toast.LENGTH_SHORT).show();


                                            } else {
                                                Toast.makeText(getApplicationContext(), "방 나가기 완젼 실패", Toast.LENGTH_SHORT).show();

                                            }

                                        }

                                    }

                                    @Override
                                    public void onFailure(Call<roomDeleteResult> call, Throwable t) {
                                        Toast.makeText(getApplicationContext(), "방 나가기 서버 실패", Toast.LENGTH_SHORT).show();

                                    }
                                });

                            }
                        });
                builder.create().show();


                Toast.makeText(getApplicationContext(), "나가신다!!!", Toast.LENGTH_LONG).show();
                //TODO something when floating action menu first item clicked
            }
        });


        //유리: 카카오톡 링크관련 함수
        btnKakaoLink = (Button) findViewById(R.id.btnKakaoLink);
        txtLinkRoom = (TextView) findViewById(R.id.txtLinkRoom);
        txtLinkRoomContent = (TextView) findViewById(R.id.txtLinkRoomContent);

        stringLinkRoom = txtLinkRoom.getText().toString();
        stringLinkRoomContent = txtLinkRoomContent.getText().toString();

        //확인 차 넣어둠 지울게요~
        Toast.makeText(getApplicationContext(), stringLinkRoom + " AAA" + stringLinkRoomContent, Toast.LENGTH_LONG).show();


        /**
         * 지은 : 상세정보 가져오기(8번통신)네트워크
         */

        String my_id = ApplicationController.getInstance().myInfo.id;
        String meeting_id = getIntent().getStringExtra("meeting_id");

        Call<DetailResult> requestDetail = ApplicationController.getInstance().getNetworkService().requestDetailList(my_id, meeting_id);
        requestDetail.enqueue(new Callback<DetailResult>() {
            @Override
            public void onResponse(Call<DetailResult> call, Response<DetailResult> response) {

                if (response.isSuccessful()) {
                    meetingRoomInfo = response.body();

                    Log.i("myTag", String.valueOf(meetingRoomInfo.my_meeting_id));
                    //Log.i("myTag", "이거 " + String.valueOf(meetingRoomInfo.participants.get(1).is_input));

                    txtLinkRoom.setText(meetingRoomInfo.room_info.get(0).title);

                    //영호 : 프사 사진 박는 부분 -> test 해봐야함
                    Glide.with(getApplicationContext())
                            .load(meetingRoomInfo.room_info.get(0).host_profile)
                            .error(R.drawable.bighuman)
                            .into(imgViewList);

                    //영호 : 레이아웃관련
                    mrSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
                    mViewPager.setAdapter(mrSectionsPagerAdapter);
                    tabLayout.setupWithViewPager(mViewPager);

                }

            }

            @Override
            public void onFailure(Call<DetailResult> call, Throwable t) {

            }
        });


    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch (position) {
                case 0:
                    return new PersonFragment(meetingRoomInfo.participants);
                case 1:
                    return new WhenFragment(meetingRoomInfo.dates);
                case 2:
                    return new PlaceFragment(meetingRoomInfo.participants);
            }
            return null;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }


        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "";
                case 1:
                    return "";
                case 2:
                    return "";
            }
            return null;
        }

        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }
    }

}

