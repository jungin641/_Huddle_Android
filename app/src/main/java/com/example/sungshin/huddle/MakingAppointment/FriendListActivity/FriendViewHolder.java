package com.example.sungshin.huddle.MakingAppointment.FriendListActivity;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.TextView;

import com.huddle.huddle.R;

/**
 * Created by LG on 2016-12-30.
 */

public class FriendViewHolder extends RecyclerView.ViewHolder implements Checkable {

    ImageView imageView;
    TextView titleView;
    CheckBox cb;

    //여기 수정함 - 영호 / id 부분 삭제함 -> 연결된 레이아웃도 같이 삭제해줘야 함.
    public FriendViewHolder(View itemView) {
        super(itemView);

        imageView = (ImageView) itemView.findViewById(R.id.imageView1);
        titleView = (TextView) itemView.findViewById(R.id.titleTextView);
        cb = (CheckBox) itemView.findViewById(R.id.checkbox1);
    }

    @Override
    public void setChecked(boolean checked) {
        if (cb.isChecked() != checked) {
            cb.setChecked(checked);
        }
    }

    @Override
    public boolean isChecked() {
        return cb.isChecked();
    }

    @Override
    public void toggle() {
        setChecked(cb.isChecked() ? false : true);
    }

}