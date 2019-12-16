package com.hwy.mymusicplayer.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.hwy.mymusicplayer.R;
import com.hwy.mymusicplayer.helps.UserHelper;
import com.hwy.mymusicplayer.utils.UserUtils;

public class MeActivity extends BaseActivity {

    private TextView mTvUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me);

        initView();
    }

    private void initView() {
        initNavBar(true, "Personal Center", false);
        mTvUser = findViewById(R.id.tv_user);
        mTvUser.setText("用户名：" + UserHelper.getInstance().getPhone());
    }


    /**
     * 退出登录
     * @param view
     */
    public void onLogoutClick(View view) {
        UserUtils.logout(this);
    }


}
