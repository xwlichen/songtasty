package com.song.tasty.common.app.music

import android.app.Service
import android.content.Intent
import android.content.IntentFilter
import android.os.IBinder

/**
 * @date : 2020-01-07 11:15
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description :
 */
open class MusicService : Service() {
    lateinit var receiver: MusicBroadcastReceiver
    lateinit var musicManager: MusicManager;
    var musicNotification: MusicNotification? = null

    override fun onBind(intent: Intent?): IBinder? {
        return null;
    }

    override fun onCreate() {
        super.onCreate()
        receiver = MusicBroadcastReceiver(this);
        var intentFilter = IntentFilter().apply {
            addAction(MUSIC_NOTIFICATION_ACTION_PLAY)
            addAction(MUSIC_NOTIFICATION_ACTION_PRE)
            addAction(MUSIC_NOTIFICATION_ACTION_NEXT)
            addAction(MUSIC_NOTIFICATION_ACTION_CLOSE)
        }
        registerReceiver(receiver, intentFilter)

        musicManager = MusicManager.instance;
    };


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        musicNotification = MusicNotification(this)
        return START_STICKY;
    }


}