package com.example.sungshin.huddle.MainList;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.huddle.huddle.R;

import java.util.ArrayList;

/**
 * Created by LG on 2016-12-27.
 */

public class Adapter extends RecyclerView.Adapter<MainViewHolder> {
    ArrayList<MainListData> MainListData;
    View.OnClickListener clickEvent;

    ViewGroup parent;


    public Adapter(ArrayList<MainListData> MainListData, View.OnClickListener clickEvent) {
        this.MainListData = MainListData;
        this.clickEvent = clickEvent;

//        Log.i("myTag", String.valueOf(MainListData.size()));
    }

    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        this.parent = parent;

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        MainViewHolder mainViewHolder = new MainViewHolder(itemView);

        itemView.setOnClickListener(clickEvent);

        return mainViewHolder;
    }

    //리스트 항목을 뿌려주는 메소드
    @Override
    public void onBindViewHolder(MainViewHolder holder, int position) {



//        Log.i("myTag",MainListData.get(position).host_profile);
        Glide.with(parent.getContext())
                .load(MainListData.get(position).host_profile)
                .error(R.drawable.bighuman)
                .into(holder.getImgViewList());

        holder.txtTitle.setText(MainListData.get(position).title);
        holder.txtHost.setText(MainListData.get(position).host_name);
        holder.txtNum.setText(String.valueOf(MainListData.get(position).member));

        if (MainListData.get(position).when_fix == 0) {
            holder.isWhenFixImg.setImageResource(R.drawable.mhj);
        } else if (MainListData.get(position).when_fix == 1) {
            holder.isWhenFixImg.setImageResource(R.drawable.hj);
        }
        if (MainListData.get(position).where_fix == 0) {
            holder.isWhereFixImg.setImageResource(R.drawable.mhj);
        } else if (MainListData.get(position).where_fix == 1) {
            holder.isWhereFixImg.setImageResource(R.drawable.hj);
        }

    }

    @Override
    public int getItemCount() {
        return (MainListData != null) ? MainListData.size() : 0;
    }
}
