package com.hwy.mymusicplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class RegisterActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
    }

    /**
     * 初始化view
     */
    private void initView() {
        initNavBar(true, "Register", false);
    }


}
