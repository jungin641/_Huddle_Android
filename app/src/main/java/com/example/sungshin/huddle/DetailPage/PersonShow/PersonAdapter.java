package com.example.sungshin.huddle.DetailPage.PersonShow;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sungshin.huddle.DetailPage.ParticipantsDataList;
import com.huddle.huddle.R;

import java.util.ArrayList;

/**
 * Created by 손영호 on 2016-12-31.
 */
public class PersonAdapter extends RecyclerView.Adapter<PersonViewHolder> {

    ArrayList<ParticipantsDataList> mDatas;
    View.OnClickListener mOnClickListener;
    private ArrayList listItem; // 체크된 아이템들을 저장할 리스트
    //private ArrayList<StorePhoneNum> nameStore;

    public PersonAdapter(ArrayList<ParticipantsDataList> mDatas, View.OnClickListener mOnClickListener) {
        this.mDatas = mDatas;
        this.mOnClickListener = mOnClickListener;
        listItem = new ArrayList();
        // nameStore = new ArrayList<>();

    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.person_showlist, parent, false);
        PersonViewHolder viewHolder = new PersonViewHolder(itemView);
        itemView.setOnClickListener(mOnClickListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(PersonViewHolder holder, int position) {

        holder.titleView.setText(mDatas.get(position).name);
        if(Integer.parseInt(mDatas.get(position).is_input) == 1){//1이 입력
            holder.cb.setImageResource(R.drawable.turnon);
        }else{//0이 미입력
            holder.cb.setImageResource(R.drawable.turnoff);
        }
    }

    @Override
    public int getItemCount() {
        return (mDatas != null) ? mDatas.size() : 0;
    }


}