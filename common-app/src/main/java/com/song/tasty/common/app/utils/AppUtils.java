package com.song.tasty.common.app.utils;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.annotation.Nullable;

/**
 * @date : 2019-07-23 14:49
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description :
 */
public final class AppUtils {

    @SuppressLint("StaticFieldLeak")
    private static Context context;

    private AppUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }


    /**
     * 初始化context ，一般放在application 中初始化
     * @param context
     */
    public static void init(@Nullable final Context context) {
        AppUtils.context = context.getApplicationContext();
    }


    /**
     * 获取 applicationcontext
     * @return
     */
    public static Context getContext() {
        return context;
    }
}
