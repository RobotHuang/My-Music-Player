package com.hwy.mymusicplayer.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.hwy.mymusicplayer.R;
import com.hwy.mymusicplayer.utils.UserUtils;
import com.hwy.mymusicplayer.views.InputView;

public class LoginActivity extends BaseActivity {

    private InputView inputUser, inputPassword;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
    }

    private void initView() {
        initNavBar(false, "Login", false);
        inputUser = findViewById(R.id.input_username);
        inputPassword = findViewById(R.id.input_password);
    }

    /**
     * 跳转注册页面点击事件
     * @param view
     */
    public void onRegisterClick(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    /**
     * 登录
     * @param view
     */
    public void onCommitClick(View view) {

        String phone = inputUser.getInputStr();

        String password = inputPassword.getInputStr();

        /*验证用户输入是否合法*/
        if (!UserUtils.validateLogin(this, phone, password)) {
            return;
        }

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
