package com.hwy.mymusicplayer.helps;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;

import com.hwy.mymusicplayer.activities.PlayMusicActivity;

import java.io.IOException;

public class MediaPlayerHelp {

    private static MediaPlayerHelp instance;

    private Context mContext;

    private MediaPlayer mMediaPlayer;

    private OnMediaPlayerHelperListener onMediaPlayerHelperListener;

    private String mPath;

    public void setOnMediaPlayerHelperListener(OnMediaPlayerHelperListener onMediaPlayerHelperListener) {
        this.onMediaPlayerHelperListener = onMediaPlayerHelperListener;
    }

    public static MediaPlayerHelp getInstance(Context context) {

        if (instance == null) {
            synchronized (MediaPlayerHelp.class) {
                if (instance == null) {
                    instance = new MediaPlayerHelp(context);
                }
            }
        }

        return instance;
    }


    private MediaPlayerHelp(Context context) {
        this.mContext = context;
        this.mMediaPlayer = new MediaPlayer();
    }

    /**
     * 获取当前需要播放的音乐
     * @param path 音乐播放的路径
     */
    public void setPath(String path) {

        //当前音乐正在播放，重置播放状态
        if (mMediaPlayer.isPlaying() || !path.equals(this.mPath)) {
            mMediaPlayer.reset();
        }
        this.mPath = path;

        //设置音乐播放路径
        try {
            mMediaPlayer.setDataSource(mContext, Uri.parse(path));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //准备播放
        mMediaPlayer.prepareAsync();
        mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                if (onMediaPlayerHelperListener != null) {
                    onMediaPlayerHelperListener.onPrepared(mp);
                }
            }
        });

        //监听音乐播放完成
        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if (onMediaPlayerHelperListener != null) {
                    onMediaPlayerHelperListener.onCompletion(mp);
                }
            }
        });
    }

    /**
     * 返回正在播放的音乐路径
     * @return
     */
    public String getPath() {
        return mPath;
    }

    public interface OnMediaPlayerHelperListener {
        void onPrepared(MediaPlayer mp);
        void onCompletion(MediaPlayer mp);
    }

    /**
     * 播放音乐
     */
    public void start() {
        if (mMediaPlayer.isPlaying()) return;
        mMediaPlayer.start();
    }

    /**
     * 暂停播放
     */
    public void pause() {
        mMediaPlayer.pause();
    }


}
