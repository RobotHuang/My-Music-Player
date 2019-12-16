package com.hwy.mymusicplayer.helps;

import android.content.Context;

import com.hwy.mymusicplayer.migration.Migration;
import com.hwy.mymusicplayer.models.AlbumModel;
import com.hwy.mymusicplayer.models.MusicModel;
import com.hwy.mymusicplayer.models.MusicSourceModel;
import com.hwy.mymusicplayer.utils.DataUtils;

import java.io.FileNotFoundException;

import io.realm.Realm;
import io.realm.RealmConfiguration;


public class RealmHelper {

    private Realm mRealm;

    public RealmHelper() {
        mRealm  = Realm.getDefaultInstance();
    }

    /**
     * Realm数据库发生结构性变化（模型或者模型中的字段发生了新增、修改、删除）
     * 的时候，我们就需要对数据库进行迁移。
     */

    /**
     * 告诉Realm数据需要迁移，并且为Realm设置最新的配置
     */
    public static void migration () {
        RealmConfiguration conf = getRealmConf();

//        Realm设置最新的配置
        Realm.setDefaultConfiguration(conf);
//        告诉Realm数据需要迁移
        try {
            Realm.migrateRealm(conf);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 返回 RealmConfiguration
     * @return
     */
    private static RealmConfiguration getRealmConf () {
        return new RealmConfiguration.Builder()
                .schemaVersion(1)
                .migration(new Migration())
                .build();
    }

    /**
     * 关闭数据库
     */
    public void close () {
        if (mRealm != null && !mRealm.isClosed()) {
            mRealm.close();
        }
    }


    /**
     * 保存音乐源数据
     */
    public void setMusicSource (Context context) {
//        拿到资源文件中的数据
        String musicSourceJson = DataUtils.getJsonFromAssets(context, "DataSource.json");
        mRealm.beginTransaction();
        mRealm.createObjectFromJson(MusicSourceModel.class, musicSourceJson);
        mRealm.commitTransaction();
    }

    /**
     * 删除音乐源数据
     * 1、RealmResult delete
     * 2、Realm delete 删除这个模型下所有的数据
     */
    public void removeMusicSource () {
        mRealm.beginTransaction();
        mRealm.delete(MusicSourceModel.class);
        mRealm.delete(MusicModel.class);
        mRealm.delete(AlbumModel.class);
        mRealm.commitTransaction();
    }

    /**
     * 返回音乐源数据
     */
    public MusicSourceModel getMusicSource () {
        return mRealm.where(MusicSourceModel.class).findFirst();
    }

    /**
     * 返回歌单
     */
    public AlbumModel getAlbum (String albumId) {
        return mRealm.where(AlbumModel.class).equalTo("albumId", albumId).findFirst();
    }

    /**
     * 返回音乐
     */
    public MusicModel getMusic (String musicId) {
        return mRealm.where(MusicModel.class).equalTo("musicId", musicId).findFirst();
    }
}
