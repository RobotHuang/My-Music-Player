package com.hwy.mymusicplayer.activities;

import android.os.Bundle;

import com.hwy.mymusicplayer.R;

public class ChangePasswordActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        initView();
    }

    private void initView() {
        initNavBar(true, "password modification", false);
    }

}
