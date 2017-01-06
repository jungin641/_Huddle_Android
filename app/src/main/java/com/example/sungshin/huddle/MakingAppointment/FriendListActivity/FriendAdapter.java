package com.example.sungshin.huddle.MakingAppointment.FriendListActivity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.bumptech.glide.Glide;
import com.huddle.huddle.R;

import java.util.ArrayList;

/**
 * Created by LG on 2016-12-30.
 */

public class FriendAdapter extends RecyclerView.Adapter<FriendViewHolder> {

    ArrayList<RealFriendList> mDatas;
    View.OnClickListener mOnClickListener;

    public ArrayList listItem; // 체크된 아이템들을 저장할 리스트
    public ArrayList<StorePhoneNum> nameStore;
    Context context ; //컨텍스트 추가

    public FriendAdapter(ArrayList<RealFriendList> mDatas, View.OnClickListener mOnClickListener) {
        this.mDatas = mDatas;
        this.mOnClickListener = mOnClickListener;
        listItem = new ArrayList();
        nameStore = new ArrayList<>();
    }

    @Override
    public FriendViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();//컨텍스트 추가
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.friendlist_recyclerview, parent, false);
        FriendViewHolder viewHolder = new FriendViewHolder(itemView);
        itemView.setOnClickListener(mOnClickListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(FriendViewHolder holder, int position) {
        final int checkBoxPosition = position;
        //프로필 사진 입력
        if(mDatas.get(position).profile != ""){
            Glide.with(context).load(mDatas.get(position).profile)
                    .error(R.drawable.ic_hobby_mypage)
                    .into(holder.imageView);
        }
        //이름과 id값
        holder.titleView.setText(mDatas.get(position).name);
        holder.cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override//checkbox 상태 변화 체크하는 부분
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) { //check 할때
                    for (int i = 0; i < listItem.size(); i++) {
                        if (Integer.parseInt(listItem.get(i).toString()) == checkBoxPosition) {
                            return;
                        }
                    }
                    listItem.add(checkBoxPosition);
                    nameStore.add(new StorePhoneNum(mDatas.get(checkBoxPosition).id));
                    System.out.println(nameStore);
                } else //check 해제 될 때
                {
                    for (int i = 0; i < listItem.size(); i++) {
                        if (Integer.parseInt(listItem.get(i).toString()) == checkBoxPosition) {
                            listItem.remove(i);
                            nameStore.remove(i);
                            System.out.println(nameStore);
                            break;
                        }
                    }
                }
            }
        });
        //체크된 아이템인지 판단할 boolean 함수
        boolean isChecked = false;
        for (int i = 0; i < listItem.size(); i++) {
            //만약 체크되었던 아이템이라면
            if (Integer.parseInt(listItem.get(i).toString()) == checkBoxPosition) {
                //체크를 한다.
                holder.cb.setChecked(true);
                isChecked = true;
                break;
            }
        }
        //아니면 체크 안한다!
        if (!isChecked) {
            holder.cb.setChecked(false);
        }
    }

    @Override
    public int getItemCount() {
        return (mDatas != null) ? mDatas.size() : 0;
    }

    //영호 : 생선된배열을 받기
    public ArrayList<StorePhoneNum> getDatas() {
        System.out.println(nameStore);
        return nameStore;
    }

}

