package com.hwy.mymusicplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

public class WelcomeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        init();
    }

    public void init() {
        Timer mytime = new Timer();
        mytime.schedule(new TimerTask(){

            @Override
            public void run() {
                toMain();
            }
        }, 2*1000);
    }

    public void toMain() {
        Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}
