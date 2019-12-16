package com.hwy.mymusicplayer.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.Toast;

import com.blankj.utilcode.util.RegexUtils;
import com.hwy.mymusicplayer.activities.LoginActivity;
import com.hwy.mymusicplayer.R;
import com.hwy.mymusicplayer.helps.RealmHelper;
import com.hwy.mymusicplayer.helps.UserHelper;

public class UserUtils {


    /**
     * 验证用户输入的合法性
     * @param context
     * @param phone
     * @param password
     * @return
     */
    public static boolean validateLogin(Context context, String phone, String password) {
        if (!RegexUtils.isMobileExact(phone)) {
            Toast.makeText(context, "无效手机号", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(context, "请输入密码", Toast.LENGTH_SHORT).show();
            return false;
        }

        UserHelper.getInstance().setPhone(phone);

        RealmHelper realmHelper = new RealmHelper();
        realmHelper.setMusicSource(context);
        realmHelper.close();
        return true;
    }

    public static void logout(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        //定义跳转动画
        ((Activity)context).overridePendingTransition(R.anim.open_enter, R.anim.open_exit);
    }
}