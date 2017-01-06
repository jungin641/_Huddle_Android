package com.example.sungshin.huddle.EditActivity.EditFragment.WhoEditFragment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.bumptech.glide.Glide;
import com.example.sungshin.huddle.MakingAppointment.FriendListActivity.RealFriendList;
import com.huddle.huddle.R;

import java.util.ArrayList;

/**
 * Created by LG on 2016-12-30.
 */

public class FriendAdapter2 extends RecyclerView.Adapter<FriendViewHolder2> {

    ArrayList<RealFriendList> mDatas;
    View.OnClickListener mOnClickListener;

    public ArrayList listItem; // 체크된 아이템들을 저장할 리스트
    public ArrayList<StorePhoneNum2> nameStore2;
    Context context ; //컨텍스트 추가
    ArrayList<String> nameStore22;

    public FriendAdapter2(ArrayList<RealFriendList> mDatas, View.OnClickListener mOnClickListener) {
        this.mDatas = mDatas;
        this.mOnClickListener = mOnClickListener;
        listItem = new ArrayList();
        nameStore2 = new ArrayList<>();
        nameStore22 = new ArrayList<>();
    }

    @Override
    public FriendViewHolder2 onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();//컨텍스트 추가
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.friendlist_recyclerview, parent, false);
        FriendViewHolder2 viewHolder = new FriendViewHolder2(itemView);
        itemView.setOnClickListener(mOnClickListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(FriendViewHolder2 holder, int position) {
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
                    nameStore2.add(new StorePhoneNum2(mDatas.get(checkBoxPosition).id));
                } else //check 해제 될 때
                {
                    for (int i = 0; i < listItem.size(); i++) {
                        if (Integer.parseInt(listItem.get(i).toString()) == checkBoxPosition) {
                            listItem.remove(i);
                            nameStore2.remove(i);
                            System.out.println(nameStore2);
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
    //생성된 배열 edit에서 사용하기
    public ArrayList<String> getDatas2() {
        for(int i=0;i<nameStore2.size();i++){
            nameStore22.add(nameStore2.get(i).id);
        }
        return nameStore22;
    }
}
