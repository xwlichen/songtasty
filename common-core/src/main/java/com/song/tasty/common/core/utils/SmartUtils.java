package com.song.tasty.common.core.utils;

import android.content.Intent;

import com.song.tasty.common.core.AppManager;

/**
 * @author lichen
 * @date ：2019-07-22 22:33
 * @email : 196003945@qq.com
 * @description :
 */
public class SmartUtils {


    /**
     * 跳转界面 , 通过 {@link AppManager#startActivity(Intent)}
     *
     * @param
     */
    public static void startActivity(Intent content) {
        AppManager.getAppManager().startActivity(content);
    }
}
