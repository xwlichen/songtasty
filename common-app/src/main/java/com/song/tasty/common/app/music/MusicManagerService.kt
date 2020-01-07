package com.song.tasty.common.app.music

import android.app.Service
import android.content.Intent
import android.os.IBinder

/**
 * @date : 2020-01-07 11:15
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description :
 */
open class MusicManagerService : Service() {
    override fun onBind(intent: Intent?): IBinder? {
        return null;
    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {


        return START_STICKY;
    }

}