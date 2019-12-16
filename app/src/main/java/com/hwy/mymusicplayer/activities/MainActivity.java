package com.hwy.mymusicplayer.activities;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.hwy.mymusicplayer.R;
import com.hwy.mymusicplayer.adapters.MusicGridAdapter;
import com.hwy.mymusicplayer.adapters.MusicListAdapter;
import com.hwy.mymusicplayer.helps.RealmHelper;
import com.hwy.mymusicplayer.models.LocalMusicModel;
import com.hwy.mymusicplayer.models.MusicSourceModel;
import com.hwy.mymusicplayer.views.GridSpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private RecyclerView mRvGrid, mRvList, mRvLocalList;

    private MusicGridAdapter mGridAdapter;

    private MusicListAdapter mListAdapter;

    private RealmHelper mRealmHelper;

    private MusicSourceModel mMusicSourceModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();
        initView();
    }

    private void initData() {
        mRealmHelper = new RealmHelper();
        mMusicSourceModel = mRealmHelper.getMusicSource();
    }

    private void initView() {
        initNavBar(false, "Robot Music", true);

        mRvGrid = findViewById(R.id.rv_grid);
        mRvGrid.setLayoutManager(new GridLayoutManager(this, 3));
        mRvGrid.addItemDecoration(new GridSpaceItemDecoration(getResources().getDimensionPixelSize(R.dimen.albumMarginSize), mRvGrid));
        mRvGrid.setNestedScrollingEnabled(false);
        mGridAdapter = new MusicGridAdapter(this, mMusicSourceModel.getAlbum());
        mRvGrid.setAdapter(mGridAdapter);

        mRvList = findViewById(R.id.rv_list);
        mRvList.setLayoutManager(new LinearLayoutManager(this));
        mRvList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mRvList.setNestedScrollingEnabled(false);
        mListAdapter = new MusicListAdapter(this, mRvList, mMusicSourceModel.getHot());
        mRvList.setAdapter(mListAdapter);

    }

    public void onLocalClick(View view) {
        Intent intent = new Intent(this, LocalMusicReActivity.class);
        startActivity(intent);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRealmHelper.close();
    }

}
