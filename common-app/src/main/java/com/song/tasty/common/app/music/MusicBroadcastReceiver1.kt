package com.song.tasty.common.app.music

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

/**
 * @date : 2020-01-08 11:29
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description :
 */
class MusicBroadcastReceiver1(musicService1: MusicService1) : BroadcastReceiver() {
    var service = musicService1;
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent == null || intent.action.isNullOrEmpty()) {
            return
        }

        when (intent.action) {
            MUSIC_NOTIFICATION_ACTION_PLAY -> {
                MusicManager1.instance.play()
//                service.musicNotification.pause()
            }
            MUSIC_NOTIFICATION_ACTION_PRE -> MusicManager1.instance.pre()
            MUSIC_NOTIFICATION_ACTION_NEXT -> MusicManager1.instance.next()
            MUSIC_NOTIFICATION_ACTION_CLOSE -> {
                MusicManager1.instance.stop()
                service.musicNotification?.stopNotification() ?: return;
            }

        }
    }

}