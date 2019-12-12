package com.hwy.mymusicplayer.activities;

import android.os.Bundle;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hwy.mymusicplayer.R;
import com.hwy.mymusicplayer.adapters.MusicListAdapter;

public class AlbumListActivity extends BaseActivity {

    private RecyclerView rvList;

    private MusicListAdapter musicListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_list);

        initView();
    }

    public void initView() {
        initNavBar(true, "专辑音乐", false);

        rvList = findViewById(R.id.rv_list);
        rvList.setLayoutManager(new LinearLayoutManager(this));
        musicListAdapter = new MusicListAdapter(this, null);
        rvList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        rvList.setAdapter(musicListAdapter);
    }

}
