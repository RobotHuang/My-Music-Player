package com.hwy.mymusicplayer.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.RequiresApi;

import com.hwy.mymusicplayer.R;
import com.hwy.mymusicplayer.activities.MainActivity;
import com.hwy.mymusicplayer.activities.PlayLocalActivity;
import com.hwy.mymusicplayer.activities.PlayMusicActivity;
import com.hwy.mymusicplayer.activities.WelcomeActivity;
import com.hwy.mymusicplayer.helps.MediaPlayerHelp;
import com.hwy.mymusicplayer.models.MusicModel;

/**
 * 1、通过Service 连接 PlayMusicView 和 MediaPlayHelper
 * 2、PlayMusicView -- Service：
 *      1、播放音乐、暂停音乐
 *      2、启动Service、绑定Service、解除绑定Service
 * 3、MediaPlayHelper -- Service：
 *      1、播放音乐、暂停音乐
 *      2、监听音乐播放完成，停止 Service
 */
public class MusicService extends Service {

//    不可为 0
    public static final int NOTIFICATION_ID = 1;

    private MediaPlayerHelp mMediaPlayerHelp;
    private MusicModel mMusicModel;
    private String mMusicPath, mName, mAuthor;

    public MusicService() {
    }

    public class MusicBind extends Binder {
        /**
         * 设置音乐（MusicModel）
         */
        public void setMusic (MusicModel musicModel) {
            mMusicModel = musicModel;
            startForeground();
        }

        public void setMusic(String path, String name, String author) {
            mMusicPath = path;
            mName = name;
            mAuthor = author;
            startForeground(mName, mAuthor);
        }

        /**
         * 播放音乐
         */
        public void playMusic () {
            /**
             * 1、判断当前音乐是否是已经在播放的音乐
             * 2、如果当前的音乐是已经在播放的音乐的话，那么就直接执行start方法
             * 3、如果当前播放的音乐不是需要播放的音乐的话，那么就调用setPath的方法
             */
            if (mMediaPlayerHelp.getPath() != null
                    && mMediaPlayerHelp.getPath().equals(mMusicModel.getPath())) {
                mMediaPlayerHelp.start();
            } else {
                mMediaPlayerHelp.setPath(mMusicModel.getPath());
                mMediaPlayerHelp.setOnMediaPlayerHelperListener(new MediaPlayerHelp.OnMediaPlayerHelperListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mMediaPlayerHelp.start();
                    }

                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        stopSelf();
                    }
                });
            }
        }

        public void playMusic(String path) {
            if (mMediaPlayerHelp.getPath() != null
                    && mMediaPlayerHelp.getPath().equals(mMusicPath)) {
                mMediaPlayerHelp.start();
            } else {
                mMediaPlayerHelp.setPath(mMusicPath);
                mMediaPlayerHelp.setOnMediaPlayerHelperListener(new MediaPlayerHelp.OnMediaPlayerHelperListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mMediaPlayerHelp.start();
                    }

                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        stopSelf();
                    }
                });
            }
        }

        /**
         * 暂停播放
         */
        public void stopMusic () {
            mMediaPlayerHelp.pause();
        }

    }

    @Override
    public IBinder onBind(Intent intent) {

        return new MusicBind();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mMediaPlayerHelp = MediaPlayerHelp.getInstance(this);
    }

    /**
     * 设置服务在前台可见
     */
    private void startForeground () {

        /**
         * 通知栏点击跳转的intent
         */
        PendingIntent pendingIntent = PendingIntent
                .getActivity(this, 0, new Intent(this, MainActivity.class), PendingIntent.FLAG_CANCEL_CURRENT);


        /**
         * 创建Notification
         */
        Notification notification = null;
        /**
         * android API 26 以上 NotificationChannel 特性适配
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = createNotificationChannel();
            notification = new Notification.Builder(this, channel.getId())
                    .setContentTitle(mMusicModel.getName())
                    .setContentText(mMusicModel.getAuthor())
                    .setSmallIcon(R.mipmap.logo)
                    .setContentIntent(pendingIntent)
                    .build();
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(channel);
        } else {
            notification = new Notification.Builder(this)
                    .setContentTitle(mMusicModel.getName())
                    .setContentText(mMusicModel.getAuthor())
                    .setSmallIcon(R.mipmap.logo)
                    .setContentIntent(pendingIntent)
                    .build();
        }

        /**
         * 设置 notification 在前台展示
         */
        startForeground(NOTIFICATION_ID, notification);

    }

    /**
     * 设置服务在前台可见
     */
    private void startForeground (String name, String author) {

        /**
         * 通知栏点击跳转的intent
         */
        PendingIntent pendingIntent = PendingIntent
                .getActivity(this, 0, new Intent(this, MainActivity.class), PendingIntent.FLAG_CANCEL_CURRENT);


        /**
         * 创建Notification
         */
        Notification notification = null;
        /**
         * android API 26 以上 NotificationChannel 特性适配
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = createNotificationChannel();
            notification = new Notification.Builder(this, channel.getId())
                    .setContentTitle(name)
                    .setContentText(author)
                    .setSmallIcon(R.mipmap.logo)
                    .setContentIntent(pendingIntent)
                    .build();
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(channel);
        } else {
            notification = new Notification.Builder(this)
                    .setContentTitle(name)
                    .setContentText(author)
                    .setSmallIcon(R.mipmap.logo)
                    .setContentIntent(pendingIntent)
                    .build();
        }

        /**
         * 设置 notification 在前台展示
         */
        startForeground(NOTIFICATION_ID, notification);

    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private NotificationChannel createNotificationChannel () {
        String channelId = "robot";
        String channelName = "RobotMusicService";
        String Description = "RobotMusic";
        NotificationChannel channel = new NotificationChannel(channelId,
                channelName, NotificationManager.IMPORTANCE_HIGH);
        channel.setDescription(Description);
        channel.enableLights(true);
        channel.setLightColor(Color.RED);
        channel.enableVibration(true);
        channel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
        channel.setShowBadge(false);

        return channel;

    }
}
