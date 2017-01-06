package com.example.sungshin.huddle.Application;

import android.app.Application;
import android.support.multidex.MultiDex;

import com.example.sungshin.huddle.LoginJoin.InfoDataList;
import com.example.sungshin.huddle.Network.NetworkService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by LG on 2016-12-29.
 */

public class ApplicationController extends Application {


    private static ApplicationController instance;
    private static String baseUrl = "http://52.79.137.94:3000/";
    private NetworkService networkService;


    public InfoDataList myInfo;


    public static ApplicationController getInstance() {
        return instance;
    }

    public NetworkService getNetworkService() {
        return networkService;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
        ApplicationController.instance = this;

        // TODO: 2016. 11. 21. 어플리케이션 초기 실행 시, retrofit을 사전에 build한다.
        buildService();
    }

    public void buildService() {
        Retrofit.Builder builder = new Retrofit.Builder();
        Retrofit retrofit = builder
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        networkService = retrofit.create(NetworkService.class);

    }
}
