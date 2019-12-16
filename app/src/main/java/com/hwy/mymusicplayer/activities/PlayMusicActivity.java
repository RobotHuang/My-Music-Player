package com.hwy.mymusicplayer.activities;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.hwy.mymusicplayer.R;
import com.hwy.mymusicplayer.helps.RealmHelper;
import com.hwy.mymusicplayer.models.MusicModel;
import com.hwy.mymusicplayer.views.PlayMusicView;

import jp.wasabeef.glide.transformations.BlurTransformation;

public class PlayMusicActivity extends BaseActivity {

    public static final String MUSIC_ID = "musicId";

    private ImageView mIvBg;

    private PlayMusicView mPlayMusicView;

    private TextView mTvName, mTvAuthor;

    private String mMusicId;

    private RealmHelper mRealmHelper;

    private MusicModel mMusicModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_music);

        //隐藏statusBar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        initData();
        initView();

    }

    /**
     * 后退按钮点击事件
     * @param view
     */
    public void onBackClick(View view) {
        onBackPressed();
    }

    private void initData() {
        mMusicId = getIntent().getStringExtra(MUSIC_ID);
        mRealmHelper = new RealmHelper();
        mMusicModel = mRealmHelper.getMusic(mMusicId);
    }

    private void initView() {
        mIvBg = findViewById(R.id.iv_bg);
        mTvName = findViewById(R.id.tv_name);
        mTvAuthor = findViewById(R.id.tv_author);

        //        glide-transformations
        Glide.with(this)
                .load(mMusicModel.getPoster())
                .apply(RequestOptions.bitmapTransform(new BlurTransformation(25, 10)))
                .into(mIvBg);
        mTvName.setText(mMusicModel.getName());
        mTvAuthor.setText(mMusicModel.getAuthor());

        mPlayMusicView = findViewById(R.id.play_music_view);
        mPlayMusicView.setMusic(mMusicModel);
        mPlayMusicView.playMusic();

    }

}
