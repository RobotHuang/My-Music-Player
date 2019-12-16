package com.hwy.mymusicplayer.activities;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Toast;

import com.hwy.mymusicplayer.R;
import com.hwy.mymusicplayer.adapters.LocalListAdapter;
import com.hwy.mymusicplayer.models.LocalMusicModel;

import java.util.ArrayList;
import java.util.List;

public class LocalMusicReActivity extends BaseActivity {

    private RecyclerView mRv;

    private LocalListAdapter mListAdapter;

    private List<LocalMusicModel> mLocalMusicModelList = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_music_re);

        initData();
        initView();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void initData() {
        //初始化歌曲信息，获取前申请读取存储运行时权限
        if (ContextCompat.checkSelfPermission(LocalMusicReActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(LocalMusicReActivity.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        } else {
            initSongs();
        }
    }

    private void initView() {
        initNavBar(true, "Local Music", false);
        mRv = findViewById(R.id.rv_local_music);
        mRv.setLayoutManager(new LinearLayoutManager(this));
        mRv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mRv.setNestedScrollingEnabled(false);
        mListAdapter = new LocalListAdapter(this, mRv, mLocalMusicModelList);
        mRv.setAdapter(mListAdapter);
    }

    /**
     * 用ContentProvider从媒体库中拿歌曲信息
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void initSongs() {
        Cursor cursor = null;
        try {
            cursor = getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                    null, null, null);
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    String songName = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore
                            .Audio.Media.TITLE));
                    String singer = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio
                            .Media.ARTIST));
                    String filePath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.
                            Audio.Media.DATA));
                    mLocalMusicModelList.add(new LocalMusicModel(songName, singer, filePath));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    /**
     * 权限请求回调函数
     * @param requestCode 请求码
     * @param permissions 请求的权限列表
     * @param grantResults 返回的结果
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0]
                        == PackageManager.PERMISSION_GRANTED) {
                    initSongs();
                } else {
                    Toast.makeText(this, "You denied the permission",
                            Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }
}
