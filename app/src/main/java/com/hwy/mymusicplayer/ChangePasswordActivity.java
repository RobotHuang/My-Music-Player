package com.hwy.mymusicplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

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
