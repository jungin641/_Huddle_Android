package com.example.sungshin.huddle.MakingAppointment.FriendListActivity;

import java.util.ArrayList;

/**
 * Created by 손영호 on 2017-01-02.
 */
//수정함...
public class FriendListRequest {
    public String id;
    ArrayList<friends_list> friends_list;

    public FriendListRequest(String id, ArrayList<friends_list> friends_list) {
        this.id = id;
        this.friends_list = friends_list;
    }
}