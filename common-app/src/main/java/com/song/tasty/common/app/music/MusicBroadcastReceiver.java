package com.song.tasty.common.app.music;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * @date : 2020-01-14 10:42
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description :
 */
public class MusicBroadcastReceiver extends BroadcastReceiver {

    private MusicService service;


    public MusicBroadcastReceiver(MusicService service) {
        this.service = service;
    }

    @Override
    public void onReceive(Context context, Intent intent) {

    }
}
