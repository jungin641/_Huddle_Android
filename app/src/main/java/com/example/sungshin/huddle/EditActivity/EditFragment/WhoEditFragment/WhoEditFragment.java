package com.example.sungshin.huddle.EditActivity.EditFragment.WhoEditFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.sungshin.huddle.MakingAppointment.FriendListActivity.RealFriendList;
import com.huddle.huddle.R;

import java.util.ArrayList;

/**
 * Created by sungshin on 2017-01-02.
 */
public class WhoEditFragment extends Fragment {
    static FriendAdapter2 adapter;
    ArrayList<RealFriendList> mDatas;
    RecyclerView recyclerView;
    LinearLayoutManager mLayoutManager;
    static ArrayList<String> phoneTemp = new ArrayList<>();

    public WhoEditFragment(ArrayList<RealFriendList> mDatas){
        this.mDatas=mDatas;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_edit_who, container, false);
        //친구목록 가져오기
        recyclerView = (RecyclerView) view.findViewById(R.id.recylerView);
        //각 item의 크기가 일정할 경우 고정
        recyclerView.setHasFixedSize(true);

        //TODO LayoutManager 초기화
        // layoutManager 설정
        mLayoutManager = new LinearLayoutManager(getContext());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);

        adapter = new FriendAdapter2(mDatas, clickEvent);
        recyclerView.setAdapter(adapter);

        return view;
    }

    public View.OnClickListener clickEvent = new View.OnClickListener() {
        public void onClick(View v) {
            int itemPosition = recyclerView.getChildPosition(v);
            String temp = mDatas.get(itemPosition).name;
            Toast.makeText(getContext(), temp, Toast.LENGTH_SHORT).show();
        }
    };

    public static ArrayList<String> geteditPhone(){
        phoneTemp = adapter.getDatas2();
        Log.i("myTag", "phone tmp == " + phoneTemp);
        return phoneTemp;
    }

}