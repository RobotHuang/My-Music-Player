package com.hwy.mymusicplayer.activities;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.hwy.mymusicplayer.R;
import com.hwy.mymusicplayer.views.PlayMusicView;

import jp.wasabeef.glide.transformations.BlurTransformation;

public class PlayMusicActivity extends BaseActivity {

    private ImageView ivBg;

    private PlayMusicView playMusicView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_music);

        //隐藏statusBar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        initView();

    }

    /**
     * 后退按钮点击事件
     * @param view
     */
    public void onBackClick(View view) {
        onBackPressed();
    }

    private void initView() {
        ivBg = findViewById(R.id.iv_bg);

        Glide.with(this)
                .load("https://y.gtimg.cn/music/photo_new/T002R300x300M000003eE8gA3TfuKc.jpg?max_age=2592000")
                .apply(RequestOptions.bitmapTransform(new BlurTransformation(25, 10)))
                .into(ivBg);

        playMusicView = findViewById(R.id.play_music_view);
        playMusicView.setMusicIcon("https://y.gtimg.cn/music/photo_new/T002R300x300M000003eE8gA3TfuKc.jpg?max_age=2592000");
        playMusicView.playMusic("http://isure.stream.qqmusic.qq.com/C400000lv3Zi13dSVA.m4a?guid=4914839700&vkey=A063F44589627C7C329CFDE888CFB8494C35B6CCAA614E704500D97A2EB4C7B04FFFCD685CAC57B3DA23418ADA13BDD7B319FC1B3EF2FC5A&uin=0&fromtag=66");
    }

}
