package com.song.tasty.common.player;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.song.tasty.common.player.bean.MusicBean;

import static com.song.tasty.common.player.MusicNotification.MUSIC_NOTIFICATION_ACTION_CLOSE;
import static com.song.tasty.common.player.MusicNotification.MUSIC_NOTIFICATION_ACTION_NEXT;
import static com.song.tasty.common.player.MusicNotification.MUSIC_NOTIFICATION_ACTION_PLAY;
import static com.song.tasty.common.player.MusicNotification.MUSIC_NOTIFICATION_ACTION_PRE;


/**
 * @date : 2020-01-14 10:44
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description :
 */
public class MusicService extends Service {

    MusicNotification musicNotification;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        MusicBroadcastReceiver receiver = new MusicBroadcastReceiver(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(MUSIC_NOTIFICATION_ACTION_PLAY);
        intentFilter.addAction(MUSIC_NOTIFICATION_ACTION_PRE);
        intentFilter.addAction(MUSIC_NOTIFICATION_ACTION_NEXT);
        intentFilter.addAction(MUSIC_NOTIFICATION_ACTION_CLOSE);
        registerReceiver(receiver, intentFilter);

        musicNotification = new MusicNotification(this);
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String url = "http://mpge.5nd.com/2015/2015-11-26/69708/1.mp3";
        url = "https://cdn.changguwen.com/cms/media/2020116/3b8a3f6b-1813-46d5-a381-75a69fb9d09d-1579143666508.mp3";
        MusicBean bean = new MusicBean();
        bean.setUrl(url);
        MusicManager.getInstance().playMusic(bean);
        return START_STICKY;
    }
}
