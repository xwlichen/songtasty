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
class MusicBroadcastReceiver(musicService: MusicService) : BroadcastReceiver() {
    var service = musicService;
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent == null || intent.action.isNullOrEmpty()) {
            return
        }

        when (intent.action) {
            MUSIC_NOTIFICATION_ACTION_PLAY -> {
                MusicManager.instance.play()
//                service.musicNotification.pause()
            }
            MUSIC_NOTIFICATION_ACTION_PRE -> MusicManager.instance.pre()
            MUSIC_NOTIFICATION_ACTION_NEXT -> MusicManager.instance.next()
            MUSIC_NOTIFICATION_ACTION_CLOSE -> {
                MusicManager.instance.stop()
                service.musicNotification?.stopNotification() ?: return;
            }

        }
    }

}