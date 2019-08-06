package com.song.tasty.common.core;

import android.os.Environment;

/**
 * @author lichen
 * @date ：2019-07-03 22:17
 * @email : 196003945@qq.com
 * @description :
 */
public interface Constants {
    String LOCAL_PATH = Environment.getExternalStorageDirectory().getAbsolutePath()
            + "/Android/data/com.song.tasty/";

    String CACHE_DIR = LOCAL_PATH + "cache/";


}
