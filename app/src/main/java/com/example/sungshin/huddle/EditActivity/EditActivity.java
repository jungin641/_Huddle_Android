package com.example.sungshin.huddle.EditActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.sungshin.huddle.Application.ApplicationController;
import com.example.sungshin.huddle.DetailPage.MainDetailActivity;
import com.example.sungshin.huddle.EditActivity.EditFragment.WhenEditFragment.StoreDate2;
import com.example.sungshin.huddle.EditActivity.EditFragment.WhenEditFragment.WhenEditFragment;
import com.example.sungshin.huddle.EditActivity.EditFragment.WhereEditFragment.WhereEditFragment;
import com.example.sungshin.huddle.EditActivity.EditFragment.WhoEditFragment.WhoEditFragment;
import com.example.sungshin.huddle.MainActivity;
import com.example.sungshin.huddle.MakingAppointment.FriendListActivity.RealFriendList;
import com.example.sungshin.huddle.Network.NetworkService;
import com.huddle.huddle.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter1;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager1;
    private TabLayout tabLayout1;

    //유리 20170105추가
    Button btneditComplete, btneditcancel;

//    InfoDataList convertJson;

    // 지은 레트로핏 관련 변수
    Uri data;
    NetworkService service;
    EditMyOpinionResult editMyOpinionResult;

    //혜민 : 2017 01 05 켈린더 관련 변수
    ArrayList<String> days;
    ArrayList<StoreDate2> storeDates2;
    //유리 : 20170105
    //@@@ 유리 fragment로부터의 전송해야할 값들
    String place, longitude, latitude;
    public static position mposition;
    //영호 who 때문에 필요한 변수들
    ArrayList<RealFriendList> friendListArrayList;
    //지은 12번통신
    voteMyOpinionDataList voteMyOpinionDataList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        //유리20170106
        mposition = new position();
        // Toolbar toolbar1 = (Toolbar) findViewById(R.id.toolbar1);
        //  setSupportActionBar(toolbar1);

        //  getSupportActionBar().setDisplayShowTitleEnabled(false);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        // mSectionsPagerAdapter1 = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        // mViewPager1 = (ViewPager) findViewById(R.id.mViewPager1);
        // mViewPager1.setAdapter(mSectionsPagerAdapter1);
//        setupViewPager(mViewPager);
        // tabLayout1 = (TabLayout) findViewById(R.id.tabs1);

        // tabLayout1.setupWithViewPager(mViewPager1);

        //  tabLayout1.getTabAt(1).select();

        //영호 체크된 아이디 받아오는 부분
        friendListArrayList = new ArrayList<>();

        //지은 2017 01 04 추가 : 미리 retrofit를 bulid 한 것을 가져온다.
        service = ApplicationController.getInstance().getNetworkService();
        Intent intent = getIntent();
        final String my_meeting_id = intent.getStringExtra("my_meeting_id");

        //지은 레트로핏 11번 통신 입력하러가기 버튼 을 눌렀을 경우 정보를 가져오는것
        Call<EditMyOpinionResult> requestEditMyOpinion = service.requestEditMyOpinion(my_meeting_id);
        requestEditMyOpinion.enqueue(new Callback<EditMyOpinionResult>() {
            @Override
            public void onResponse(Call<EditMyOpinionResult> call, Response<EditMyOpinionResult> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "입력 하러가기 성공", Toast.LENGTH_SHORT).show();
                    editMyOpinionResult = response.body();


                    //영호 부분 리스트 받는부분
                    for (int i = 0; i < editMyOpinionResult.friend_list.size(); i++) {
                        friendListArrayList.add(new RealFriendList(editMyOpinionResult.friend_list.get(i).name, editMyOpinionResult.friend_list.get(i).profile, editMyOpinionResult.friend_list.get(i).id));
                    }
                    //혜민 :2017 01 05 추가

                    mSectionsPagerAdapter1 = new SectionsPagerAdapter(getSupportFragmentManager());
                    // Set up the ViewPager with the sections adapter.
                    mViewPager1 = (ViewPager) findViewById(R.id.mViewPager1);
                    mViewPager1.setAdapter(mSectionsPagerAdapter1);
                    //        setupViewPager(mViewPager);
                    tabLayout1 = (TabLayout) findViewById(R.id.tabs1);

                    tabLayout1.setupWithViewPager(mViewPager1);

                    tabLayout1.getTabAt(1).select();

                }
            }

            @Override
            public void onFailure(Call<EditMyOpinionResult> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "입력 하러가기 서버실패 ", Toast.LENGTH_SHORT).show();

            }
        });

        btneditComplete = (Button) findViewById(R.id.btneditComplete);
        btneditComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 혜민: 2017 01 05 추가
                Log.i("myTag", "버튼 클릭 시작");
                Log.i("myTag", "버튼 클릭 시작");
                int WhenSize = WhenEditFragment.geteditDates().size();
                Log.i("myTag", "int" + WhenSize);
                ArrayList<String> tempWhen = new ArrayList<String>();
                Log.i("myTag", "for문 이전");
                for (int i = 0; i < WhenSize; i++) {

                    tempWhen.add(i, WhenEditFragment.geteditDates().get(i));
                    Log.i("myTag", "천재지은" + tempWhen);
                }
                Log.i("myTag", "for문 이후");


                Log.i("myTag", "  WhenEditFragment.geteditDates(); 후 ");
                Log.i("myTag", "tempWhen" + tempWhen);
                Log.i("myTag", "String.valueOf(WhenEditFragment.geteditDates()" + String.valueOf(WhenEditFragment.geteditDates()));
                //유리 :
                position posi = position.getInstance();
                if (posi == null) {
                    Log.i("Log_Jinsub", "posi is null");
                } else {
                    Log.i("Log_Jinsub_onclick", posi.getplace());
                    Log.i("Log_Jinsub_onclick", posi.getlatitude());
                    Log.i("Log_Jinsub_onclick", posi.getlongitude());
                }

                //영호:
                Log.i("myTag", " WhoEditFragment.geteditPhone();  영호 전 ");
                int WhoSize = WhoEditFragment.geteditPhone().size();
                ArrayList<String> tempWho = new ArrayList<String>();

                for (int i = 0; i < WhoSize; i++) {
                    tempWho.add(i, WhoEditFragment.geteditPhone().get(i));
                    Log.i("myTag", "천재지은~~" + tempWho);
                }

                Log.i("myTag", " WhoEditFragment.geteditPhone(); 영호 후");

                //레트로핏

                voteMyOpinionDataList data = new voteMyOpinionDataList();

                data.days = new ArrayList<String>();
                data.participant = new ArrayList<String>();
                data.position = data.new position();
                ArrayList<String> nullArr = new ArrayList<String>();


                Log.i("myTag", "null처리 확인" + tempWhen.size());
                if (tempWhen.size() == 0) {
                    data.days = null;
                } else {
                    for (int i = 0; i < tempWhen.size(); i++) {
                        data.days.add(i, tempWhen.get(i));
                    }
                }

                Log.i("myTag", "null처리 확인" + tempWho.size());

                if (tempWho.size() == 0) {
                    data.participant = null;
                } else {
                    for (int i = 0; i < tempWho.size(); i++) {
                        data.participant.add(i, tempWho.get(i));
                    }
                }


                if (posi == null) {
                    data.position = null;
                } else {

                    data.position.place = posi.getplace();
                    data.position.latitude = posi.getlongitude();
                    data.position.longitude = posi.getlongitude();

//                    Log.i("Log_Jinsub_onclick", data.position.place);
  //                  Log.i("Log_Jinsub_onclick", data.position.latitude);
    //                Log.i("Log_Jinsub_onclick", data.position.longitude);
                }

                data.my_meeting_id = Integer.parseInt(my_meeting_id);
                Log.i("myTag", "인텐트전");
                Call<voteMyOpinionResult> requestVoteOpinion = service.requestVoteOpinion(data);
                requestVoteOpinion.enqueue(new Callback<voteMyOpinionResult>() {
                    @Override
                    public void onResponse(Call<voteMyOpinionResult> call, Response<voteMyOpinionResult> response) {
                        if (response.isSuccessful()) {
                            if (response.body().result.equals("SUCCESS")) {
                                Toast.makeText(getApplicationContext(), "입력 성공 ", Toast.LENGTH_SHORT).show();
                                Intent intent1 = new Intent(EditActivity.this, MainActivity.class);
                                startActivity(intent1);
                                finish();

                            } else if (response.body().result.equals("FAIL")) {
                                Toast.makeText(getApplicationContext(), "입력 실패 ", Toast.LENGTH_SHORT).show();


                            } else {
                                Toast.makeText(getApplicationContext(), "엘스문 ", Toast.LENGTH_SHORT).show();

                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<voteMyOpinionResult> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "입력 서버실패 ", Toast.LENGTH_SHORT).show();

                    }
                });


            }
        });


//유리20170104
        btneditcancel = (Button)
                findViewById(R.id.btneditcancel);

        btneditcancel.setOnClickListener(new View.OnClickListener()

                                         {
                                             @Override
                                             public void onClick(View v) {


                                                 Intent intent1 = new Intent(EditActivity.this, MainDetailActivity.class);
                                                 startActivity(intent1);
                                                 finish();

                                             }
                                         }

        );


        //startActivity(new Intent(this, SplashActivity.class));
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        Log.i("myTag", "restart");
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
                    return new WhoEditFragment(friendListArrayList);
                case 1:
                    return new WhenEditFragment(editMyOpinionResult.days);
                case 2:
                    return new WhereEditFragment(editMyOpinionResult, mposition);
            }
            //유리 20170105
            longitude = mposition.getlongitude();
            latitude = mposition.getlatitude();
            place = mposition.getplace();
//            Log.d("SHIT", )
            Toast.makeText(getApplicationContext(), "Edit Activity" + longitude + "//" + place, Toast.LENGTH_SHORT).show();

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
                    return " ";
                case 1:
                    return " ";
                case 2:
                    return " ";
            }
            return null;
        }

        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }
    }
}