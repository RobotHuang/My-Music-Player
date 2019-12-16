package com.hwy.mymusicplayer.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.hwy.mymusicplayer.R;
import com.hwy.mymusicplayer.views.PlayLocalView;
import com.hwy.mymusicplayer.views.PlayMusicView;

public class PlayLocalActivity extends BaseActivity {

    private PlayLocalView mPlayLocalView;

    private TextView mTvName, mTvAuthor;

    private String mName, mAuthor, mPath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_local);

        //隐藏statusBar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        initData();
        initView();
    }

    private void initData() {
        Intent intent = getIntent();
        mName = intent.getStringExtra("name");
        mAuthor = intent.getStringExtra("author");
        mPath = intent.getStringExtra("path");
    }

    private void initView() {
        mTvName = findViewById(R.id.tv_name);
        mTvAuthor = findViewById(R.id.tv_author);

        mTvName.setText(mName);
        mTvAuthor.setText(mAuthor);

        mPlayLocalView = findViewById(R.id.play_local_view);
        mPlayLocalView.setMusic(mPath, mName, mAuthor);
        mPlayLocalView.playMusic();

    }

    /**
     * 后退按钮点击事件
     * @param view
     */
    public void onBackClick(View view) {
        onBackPressed();
    }
}
