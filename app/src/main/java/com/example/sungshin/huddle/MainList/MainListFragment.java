package com.example.sungshin.huddle.MainList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.sungshin.huddle.Application.ApplicationController;
import com.example.sungshin.huddle.DetailPage.MainDetailActivity;
import com.example.sungshin.huddle.MakingAppointment.FriendListActivity.MakingAppointmentActivity1;
import com.huddle.huddle.R;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by sungshin on 2016-12-27.
 */
public class MainListFragment extends Fragment {

    //floating 버튼
    FloatingActionMenu materialDesignFAM;
    FloatingActionButton floatingActionButton1, floatingActionButton2;


    //recyclerView 사용하기 위해 선언
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    ArrayList<MainListData> dataSet;

    Adapter adapter;

    String user_id = "";

    public MainListFragment() {
        this.user_id = ApplicationController.getInstance().myInfo.id;
    }


    //혜민 0103 추가
    @Override
    public void onResume() {
        super.onResume();

        recyclerView.refreshDrawableState();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_main_list, container, false);
        //floating 버튼 선언
        materialDesignFAM = (FloatingActionMenu) view.findViewById(R.id.material_design_android_floating_action_menu);
        floatingActionButton1 = (FloatingActionButton) view.findViewById(R.id.material_design_floating_action_menu_item1);
        floatingActionButton2 = (FloatingActionButton) view.findViewById(R.id.material_design_floating_action_menu_item2);


        recyclerView = (RecyclerView) view.findViewById(R.id.mainRecyclerView);
        recyclerView.setHasFixedSize(true); //아이템들의 크기를 고정시켜주는 옵션
        //초기화

        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayout.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);


        dataSet = new ArrayList<MainListData>();

        Call<MainListResult> requestMainDatas = ApplicationController.getInstance().getNetworkService().requestMainList(new MainRequestData(user_id));

        requestMainDatas.enqueue(new Callback<MainListResult>() {
            @Override
            public void onResponse(Call<MainListResult> call, Response<MainListResult> response) {
                if (response.isSuccessful()) {
//                    Log.i("myTag", "request success" +response.body().result.size() );
//                    Log.i("myTag", "request success" +response.body().result.get(0).title );
                    dataSet = response.body().result;

                    adapter = new Adapter(dataSet, clickEvent);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<MainListResult> call, Throwable t) {
                Log.i("myTag", "failed" + t.toString());
            }
        });


        //floating 버튼 onclickListener 함수 (1= 공개 ,2= 비공개)
        floatingActionButton1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), MakingAppointmentActivity1.class);
                startActivity(intent);
                //TODO something when floating action menu first item clicked

            }
        });
        floatingActionButton2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO something when floating action menu second item clicked

            }
        });

        return view;
    }

    public View.OnClickListener clickEvent = new View.OnClickListener() {
        public void onClick(View v) {
            int itemPosition = recyclerView.getChildPosition(v); //어떤 view가 클릭됬는지 알아주는거

            String temp = dataSet.get(itemPosition).host_name;
            Toast.makeText(getView().getContext(), temp, Toast.LENGTH_SHORT).show();

            Intent intentResult = new Intent(getActivity(), MainDetailActivity.class);
            intentResult.putExtra("meeting_id", String.valueOf(dataSet.get(itemPosition).meeting_id));
            startActivity(intentResult);
        }
    };
}
