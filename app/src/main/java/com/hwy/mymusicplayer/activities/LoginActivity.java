package com.hwy.mymusicplayer.activities;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.hwy.mymusicplayer.R;
import com.hwy.mymusicplayer.models.MusicModel;
import com.hwy.mymusicplayer.utils.UserUtils;
import com.hwy.mymusicplayer.views.InputView;

import java.util.List;

public class LoginActivity extends BaseActivity {

    private InputView mInputUser, mInputPassword;

    private CheckBox mCheckBox;

    private SharedPreferences pref;

    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
    }

    private void initView() {
        initNavBar(false, "Login", false);
        mInputUser = findViewById(R.id.input_username);
        mInputPassword = findViewById(R.id.input_password);
        mCheckBox = findViewById(R.id.ok);
        pref = getSharedPreferences("data", MODE_PRIVATE);

        //获取sharedPreference中的内容，如果已记住就填充用户名
        boolean rememberUser = pref.getBoolean("remember", false);
        if (rememberUser) {
            mInputUser.setInputStr(pref.getString("username", ""));
            mInputPassword.setInputStr(pref.getString("password", ""));
            mCheckBox.setChecked(true);
        }
    }


    /**
     * 登录
     * @param view
     */
    public void onCommitClick(View view) {

        String phone = mInputUser.getInputStr();

        String password = mInputPassword.getInputStr();

        /*验证用户输入是否合法*/
        if (!UserUtils.validateLogin(this, phone, password)) {
            return;
        }

        editor = pref.edit();
        if (mCheckBox.isChecked()) {
            editor.putString("username", phone);
            editor.putString("password", password);
            editor.putBoolean("remember", true);
        } else {
            editor.clear();
        }
        editor.apply();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}
