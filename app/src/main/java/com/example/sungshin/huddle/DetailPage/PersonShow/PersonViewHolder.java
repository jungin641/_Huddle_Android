package com.example.sungshin.huddle.DetailPage.PersonShow;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.huddle.huddle.R;

/**
 * Created by 손영호 on 2016-12-31.
 */
public class PersonViewHolder extends RecyclerView.ViewHolder{

    TextView titleView;
    ImageView cb;

    public PersonViewHolder(View itemView) {
        super(itemView);

        titleView = (TextView) itemView.findViewById(R.id.titleTextView1);
        cb = (ImageView) itemView.findViewById(R.id.checkbox11);
    }


}