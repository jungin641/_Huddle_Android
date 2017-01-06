package com.example.sungshin.huddle.Splash;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.sungshin.huddle.LoginJoin.LoginActivity;
import com.huddle.huddle.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Handler hd = new Handler();
        hd.postDelayed(new splashhandler() , 1000);

//        Handler handler = new Handler(){
//            @Override
//            public void handleMessage(Message msg) {
//                finish();
//            }
//        };
//        handler.sendEmptyMessageDelayed(0, 3000);
    }
    private class splashhandler implements Runnable{
        @Override
        public void run(){
            startActivity(new Intent(getApplicationContext() , LoginActivity.class));
            SplashActivity.this.finish();
        }
    }
}
