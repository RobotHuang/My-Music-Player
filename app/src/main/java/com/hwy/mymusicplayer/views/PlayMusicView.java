package com.hwy.mymusicplayer.views;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Build;
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

import com.bumptech.glide.Glide;
import com.hwy.mymusicplayer.R;
import com.hwy.mymusicplayer.helps.MediaPlayerHelp;

public class PlayMusicView extends FrameLayout {

    private Context context;

    private View view;

    private boolean isPlaying;

    private String path;

    private FrameLayout flPlayMusic;

    private ImageView ivNeedle;

    private ImageView ivIcon;

    private ImageView ivPlay;

    private Animation playMusicAnim;

    private Animation playNeedleAnim;

    private Animation stopNeedleAnim;

    private MediaPlayerHelp mediaPlayerHelp;

    public PlayMusicView(@NonNull Context context) {
        super(context);
        init(context);
    }

    public PlayMusicView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PlayMusicView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public PlayMusicView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        view = LayoutInflater.from(context).inflate(R.layout.play_music, this, false);
        ivIcon = view.findViewById(R.id.iv_icon);
        flPlayMusic = view.findViewById(R.id.fl_play_music);
        flPlayMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trigger();
            }
        });
        ivNeedle = view.findViewById(R.id.iv_needle);
        ivPlay = view.findViewById(R.id.iv_play);

        /**
         * 定义动画
         * 1、指针指向光盘的动画
         * 2、光盘转动
         * 3、指针离开光盘
         */
        playMusicAnim = AnimationUtils.loadAnimation(context, R.anim.play_music_anim);
        playNeedleAnim = AnimationUtils.loadAnimation(context, R.anim.play_needle_anim);
        stopNeedleAnim = AnimationUtils.loadAnimation(context, R.anim.stop_needle_anim);

        addView(view);

        mediaPlayerHelp = MediaPlayerHelp.getInstance(context);
    }

    /**
     *播放音乐
     */
    public void playMusic(String path) {
        this.path = path;
        isPlaying = true;
        ivPlay.setVisibility(View.GONE);
        flPlayMusic.startAnimation(playMusicAnim);
        ivNeedle.startAnimation(playNeedleAnim);

        if (mediaPlayerHelp.getPath() != null && mediaPlayerHelp.getPath().equals(path)) {
            mediaPlayerHelp.start();
        } else {
            mediaPlayerHelp.setPath(path);
            mediaPlayerHelp.setOnMediaPlayerHelperListener(new MediaPlayerHelp.OnMediaPlayerHelperListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    mediaPlayer.start();
                }
            });
        }
    }

    /**
     * 切换播放状态
     */
    public void trigger() {
        if (isPlaying) {
            stopMusic();
        } else {
            playMusic(path);
        }
    }

    /**
     * 停止音乐
     */
    public void stopMusic() {
        isPlaying = false;
        flPlayMusic.clearAnimation();
        ivNeedle.startAnimation(stopNeedleAnim);
        ivPlay.setVisibility(View.VISIBLE);

        mediaPlayerHelp.pause();
    }

    /**
     * 显示光盘中的图片
     * @param icon 光盘中图片的Url
     */
    public void setMusicIcon(String icon) {
        Glide.with(context)
                .load(icon)
                .into(ivIcon);
    }
}
