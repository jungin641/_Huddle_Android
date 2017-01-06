package com.example.sungshin.huddle.MainList;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.huddle.huddle.R;

/**
 * Created by LG on 2016-12-27.
 */

public class MainViewHolder extends RecyclerView.ViewHolder {
    ImageView imgViewList;
    TextView txtTitle;
    TextView txtHost;
    TextView txtNum;
    ImageView isWhenFixImg;
    ImageView isWhereFixImg;
    public MainViewHolder(View itemView) {
        super(itemView);
        imgViewList = (ImageView) itemView.findViewById(R.id.imgViewList);
        txtTitle = (TextView) itemView.findViewById(R.id.txtTitle);
        txtHost = (TextView) itemView.findViewById(R.id.txtHost);
        txtNum = (TextView) itemView.findViewById(R.id.txtNum);
        isWhenFixImg = (ImageView) itemView.findViewById(R.id.isWhenFixImg);
        isWhereFixImg = (ImageView) itemView.findViewById(R.id.isWhereFixImg);
    }

    public ImageView getImgViewList() {
        return imgViewList;
    }
}
