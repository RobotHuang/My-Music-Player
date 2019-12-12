package com.hwy.mymusicplayer.helps;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;

import java.io.IOException;

public class MediaPlayerHelp {

    private static MediaPlayerHelp instance;

    private Context context;

    private MediaPlayer mediaPlayer;

    private OnMediaPlayerHelperListener onMediaPlayerHelperListener;

    private String path;

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
        this.context = context;
        this.mediaPlayer = new MediaPlayer();
    }

    /**
     * 获取当前需要播放的音乐
     * @param path 音乐播放的路径
     */
    public void setPath(String path) {

        //当前音乐正在播放，重置播放状态
        if (mediaPlayer.isPlaying() || !path.equals(this.path)) {
            mediaPlayer.reset();
        }
        this.path = path;

        //设置音乐播放路径
        try {
            mediaPlayer.setDataSource(context, Uri.parse(path));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //准备播放
        mediaPlayer.prepareAsync();
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                if (onMediaPlayerHelperListener != null) {
                    onMediaPlayerHelperListener.onPrepared(mp);
                }
            }
        });
    }

    /**
     * 返回正在播放的音乐路径
     * @return
     */
    public String getPath() {
        return this.path;
    }

    public interface OnMediaPlayerHelperListener {
        void onPrepared(MediaPlayer mp);
    }

    /**
     * 播放音乐
     */
    public void start() {
        if (mediaPlayer.isPlaying()) return;
        mediaPlayer.start();
    }

    /**
     * 暂停播放
     */
    public void pause() {
        mediaPlayer.pause();
    }
}
