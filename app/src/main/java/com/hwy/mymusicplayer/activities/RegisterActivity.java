package com.hwy.mymusicplayer.activities;

import android.os.Bundle;

import com.hwy.mymusicplayer.R;

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
