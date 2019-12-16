package com.hwy.mymusicplayer;

import android.app.Application;

import com.blankj.utilcode.util.Utils;
import com.hwy.mymusicplayer.helps.RealmHelper;

import io.realm.Realm;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(MyApplication.this);
        Realm.init(this);

        RealmHelper.migration();
    }
}
