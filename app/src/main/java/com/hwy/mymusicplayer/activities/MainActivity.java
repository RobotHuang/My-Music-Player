package com.hwy.mymusicplayer.activities;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.hwy.mymusicplayer.R;
import com.hwy.mymusicplayer.adapters.MusicGridAdapter;
import com.hwy.mymusicplayer.adapters.MusicListAdapter;
import com.hwy.mymusicplayer.views.GridSpaceItemDecoration;

public class MainActivity extends BaseActivity {

    private RecyclerView rvGrid, rvList;

    private MusicGridAdapter musicGridAdapter;

    private MusicListAdapter musicListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        initNavBar(false, "Robot Music", true);
        rvGrid = findViewById(R.id.rv_grid);
        rvGrid.setLayoutManager(new GridLayoutManager(this, 3));
        rvGrid.addItemDecoration(new GridSpaceItemDecoration(getResources()
                .getDimensionPixelOffset(R.dimen.albumMarginSize), rvGrid));
        rvGrid.setNestedScrollingEnabled(false);
        musicGridAdapter = new MusicGridAdapter(this);
        rvGrid.setAdapter(musicGridAdapter);

        rvList = findViewById(R.id.rv_list);
        rvList.setLayoutManager(new LinearLayoutManager(this));
        rvList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        rvList.setNestedScrollingEnabled(false);
        musicListAdapter = new MusicListAdapter(this, rvList);
        rvList.setAdapter(musicListAdapter);
    }

}
