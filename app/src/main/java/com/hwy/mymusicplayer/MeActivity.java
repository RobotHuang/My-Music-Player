package com.hwy.mymusicplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.hwy.mymusicplayer.utils.UserUtils;

public class MeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me);

        initView();
    }

    private void initView() {
        initNavBar(true, "Personal Center", false);

    }

    /**
     * 修改密码
     * @param view
     */
    public void onChangePasswordClick(View view) {
        startActivity(new Intent(this, ChangePasswordActivity.class));
    }

    /**
     * 退出登录
     * @param view
     */
    public void onLogoutClick(View view) {
        UserUtils.logout(this);
    }


}
