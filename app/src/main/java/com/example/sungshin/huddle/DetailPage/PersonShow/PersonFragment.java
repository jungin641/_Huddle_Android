package com.example.sungshin.huddle.DetailPage.PersonShow;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.sungshin.huddle.DetailPage.ParticipantsDataList;
import com.huddle.huddle.R;

import java.util.ArrayList;


public class PersonFragment extends Fragment {

    RecyclerView recyclerView;
    LinearLayoutManager mLayoutManager;

    ArrayList<ParticipantsDataList> mDatas;

    public PersonFragment() {

    }

    public PersonFragment(ArrayList<ParticipantsDataList> mDatas) {
        this.mDatas = mDatas;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_person, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.mainRecyclerView);

        //각 item의 크기가 일정할 경우 고정
        recyclerView.setHasFixedSize(true);

        //TODO LayoutManager 초기화
        // layoutManager 설정
        mLayoutManager = new LinearLayoutManager(this.getContext());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);

        getList();
        return view;

    }
    public void getList() {
        PersonAdapter adapter;

        adapter = new PersonAdapter(mDatas, clickEvent);
        recyclerView.setAdapter(adapter);
    }

    public View.OnClickListener clickEvent = new View.OnClickListener() {
        public void onClick(View v) {
            int itemPosition = recyclerView.getChildPosition(v);
            String temp = mDatas.get(itemPosition).name;
            Toast.makeText(getView().getContext(), temp, Toast.LENGTH_SHORT).show();
        }
    };

}