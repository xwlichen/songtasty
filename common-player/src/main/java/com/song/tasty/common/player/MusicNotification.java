package com.song.tasty.common.player;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.widget.RemoteViews;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.song.tasty.common.app.R;
import com.song.tasty.common.player.bean.MusicBean;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * @date : 2020-01-14 10:46
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description :
 */
public class MusicNotification {
    public static final String MUSIC_NOTIFICATION_ACTION_PRE = "MUSIC_NOTIFICATION_ACTION_PRE";
    public static final String MUSIC_NOTIFICATION_ACTION_NEXT = "MUSIC_NOTIFICATION_ACTION_NEXT";
    public static final String MUSIC_NOTIFICATION_ACTION_PLAY = "MUSIC_NOTIFICATION_ACTION_PLAY";
    public static final String MUSIC_NOTIFICATION_ACTION_CLOSE = "MUSIC_NOTIFICATION_ACTION_CLOSE";


    private static final String CHANNEL_ID = "chanel_play_music";
    private static final int NOTIFICATION_ID = 0x1234;


    private MusicService service;
    private Context context;
    private Notification notification;
    private RemoteViews remoteViews;


    public MusicNotification(MusicService service) {
        this.service = service;
        this.context = service.getApplicationContext();
        this.remoteViews = new RemoteViews(context.getPackageName(), R.layout.layout_notification_music);
    }


    public void pause() {
        if (notification != null) {
            remoteViews.setImageViewResource(R.id.ivPlay, R.mipmap.ic_play);
            service.startForeground(NOTIFICATION_ID, notification);
        }
    }

    public void resume() {
        if (notification != null) {
            remoteViews.setImageViewResource(R.id.ivPlay, R.mipmap.ic_pause);
            service.startForeground(NOTIFICATION_ID, notification);
        }

    }


    public void stopNotification() {
        service.stopForeground(true);
        notification = null;
    }


    public void startNotofication(MusicBean musicBean) {
        if (musicBean == null) {
            return;
        }

        remoteViews.setImageViewResource(R.id.ivPlay, R.mipmap.ic_pause);
        remoteViews.setTextViewText(R.id.tvName, musicBean.getName());

        if (notification == null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                String appName = context.getString(R.string.app_name);

                //NotificationManager.IMPORTANCE_MIN: 静默;
                //NotificationManager.IMPORTANCE_HIGH:随系统使用声音或振动
                NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, appName, NotificationManager.IMPORTANCE_MIN);
                notificationChannel.setDescription(appName);
                NotificationManager manager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
                manager.createNotificationChannel(notificationChannel);

            }

            notification = new NotificationCompat.Builder(context, CHANNEL_ID)
                    .setSmallIcon(R.mipmap.ic_logo)
                    .setTicker("音频播放啦~~")
                    .setVibrate(null)
                    .setLights(0, 0, 0)
                    .setWhen(System.currentTimeMillis())
                    .setContent(remoteViews)//设置普通notification视图
//                    .setCustomBigContentView(bigView)//设置显示bigView的notification视图
//                    .setPriority(NotificationCompat.PRIORITY_MAX)//设置最大优先级
//                    .setOngoing(true) //true使notification变为ongoing，用户不能手动清除，类似QQ,false或者不设置则为普通的通知
                    .build();

            Intent preIntent = new Intent(MUSIC_NOTIFICATION_ACTION_PRE);
            Intent playIntent = new Intent(MUSIC_NOTIFICATION_ACTION_PLAY);
            Intent nextIntent = new Intent(MUSIC_NOTIFICATION_ACTION_NEXT);
            Intent closeIntent = new Intent(MUSIC_NOTIFICATION_ACTION_CLOSE);


            remoteViews.setOnClickPendingIntent(R.id.ivPre, PendingIntent.getBroadcast(context,
                    0, preIntent, PendingIntent.FLAG_UPDATE_CURRENT));
            remoteViews.setOnClickPendingIntent(R.id.ivPlay, PendingIntent.getBroadcast(context,
                    0, playIntent, PendingIntent.FLAG_UPDATE_CURRENT));
            remoteViews.setOnClickPendingIntent(R.id.ivNext, PendingIntent.getBroadcast(context,
                    0, nextIntent, PendingIntent.FLAG_UPDATE_CURRENT));
            remoteViews.setOnClickPendingIntent(R.id.ivClose, PendingIntent.getBroadcast(context,
                    0, closeIntent, PendingIntent.FLAG_UPDATE_CURRENT));

        }

//        val activityIntent = Intent(context, PlayerHandleActivity::class.java).apply {
//            data = Uri.parse("${BuildConfig.APP_SCHEME}://goto_play_audio/${musicBean.toJson()}")
//        }
//        remoteViews.setOnClickPendingIntent(R.id.root, PendingIntent.getActivity(context,
//                0, activityIntent, PendingIntent.FLAG_UPDATE_CURRENT))

        service.startForeground(NOTIFICATION_ID, notification);
        if (TextUtils.isEmpty(musicBean.getCover())) {
            loadBitmap(musicBean.getCover());
        }

    }


    private void loadBitmap(String url) {
        int wh = imgWH();
        CustomTarget target = new CustomTarget<Bitmap>(wh, wh) {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition transition) {
                if (notification != null) {
                    remoteViews.setBitmap(R.id.ivClose, "setImageBitmap", resource);
                    service.startForeground(NOTIFICATION_ID, notification);
                }

            }

            @Override
            public void onLoadCleared(@Nullable Drawable placeholder) {

            }
        };
        Glide.with(context)
                .asBitmap()
                .load(url)
                .into(target);
    }


    private int imgWH() {
        return (int) (64 * context.getResources().getDisplayMetrics().density);
    }
}
