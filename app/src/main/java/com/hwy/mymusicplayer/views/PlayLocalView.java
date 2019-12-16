package com.hwy.mymusicplayer.views;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.IBinder;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.hwy.mymusicplayer.R;
import com.hwy.mymusicplayer.services.MusicService;

public class PlayLocalView extends FrameLayout {

    private Context mContext;

    private View mView;

    private FrameLayout mFlPlayMusic;

    private ImageView mIvIcon , mIvNeedle, mIvPlay;

    private Animation mPlayMusicAnim, mPlayNeedleAnim, mStopNeedleAnim;

    private boolean isPlaying, isBindService;

    private String mPath, mName, mAuthor;

    private Intent mServiceIntent;

    private MusicService.MusicBind mMusicBinder;


    public PlayLocalView(@NonNull Context context) {
        super(context);
        init(context);
    }

    public PlayLocalView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PlayLocalView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public PlayLocalView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init (Context context) {
        //MediaPlayer
        mContext = context;

        mView = LayoutInflater.from(mContext).inflate(R.layout.play_local, this, false);

        mFlPlayMusic = mView.findViewById(R.id.fl_play_music);
        mFlPlayMusic.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                trigger();
            }
        });
        mIvIcon = mView.findViewById(R.id.iv_icon);
        mIvNeedle = mView.findViewById(R.id.iv_needle);
        mIvPlay = mView.findViewById(R.id.iv_play);

        /**
         * 1、定义所需要执行的动画
         *      1、光盘转动的动画
         *      2、指针指向光盘的动画
         *      3、指针离开光盘的动画
         * 2、startAnimation
         */

        mPlayMusicAnim = AnimationUtils.loadAnimation(mContext, R.anim.play_music_anim);
        mPlayNeedleAnim = AnimationUtils.loadAnimation(mContext, R.anim.play_needle_anim);
        mStopNeedleAnim = AnimationUtils.loadAnimation(mContext, R.anim.stop_needle_anim);

        addView(mView);
    }

    public void setMusic(String path, String name, String author) {
        mPath = path;
        mName = name;
        mAuthor = author;
    }

    /**
     * 切换播放状态
     */
    private void trigger () {
        if (isPlaying) {
            stopMusic();
        } else {
            playMusic();
        }
    }

    /**
     * 播放音乐
     */
    public void playMusic () {
        isPlaying = true;
        mIvPlay.setVisibility(View.GONE);
        mFlPlayMusic.startAnimation(mPlayMusicAnim);
        mIvNeedle.startAnimation(mPlayNeedleAnim);

        //启动服务
        startMusicService();

    }

    /**
     * 停止播放
     */
    public void stopMusic () {
        isPlaying = false;
        mIvPlay.setVisibility(View.VISIBLE);
        mFlPlayMusic.clearAnimation();
        mIvNeedle.startAnimation(mStopNeedleAnim);
        if (mMusicBinder != null)
            mMusicBinder.stopMusic();
    }

    /**
     * 启动音乐服务
     */
    private void startMusicService () {
        if (mServiceIntent == null) {
            mServiceIntent = new Intent(mContext, MusicService.class);
            mContext.startService(mServiceIntent);
        } else {
            mMusicBinder.playMusic(mPath);
        }


        //当前未绑定，绑定服务，同时修改绑定状态
        if (!isBindService) {
            isBindService = true;
            mContext.bindService(mServiceIntent, conn, Context.BIND_AUTO_CREATE);
        }
    }

    /**
     * 销毁方法，需要在 activity 被销毁的时候调用
     */
    public void destroy () {
        //如果已绑定服务，则解除绑定，同时修改绑定状态
        if (isBindService) {
            isBindService = false;
            mContext.unbindService(conn);
        }

    }

    ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            mMusicBinder = (MusicService.MusicBind) iBinder;
            mMusicBinder.setMusic(mPath, mName, mAuthor);
            mMusicBinder.playMusic(mPath);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };
}
