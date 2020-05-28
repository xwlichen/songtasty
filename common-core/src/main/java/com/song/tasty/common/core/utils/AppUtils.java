package com.song.tasty.common.core.utils;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import java.util.ArrayList;

/**
 * @date : 2020-01-13 15:12
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description :
 */
public class AppUtils {


    @SuppressLint("StaticFieldLeak")
    private static Application application;

    private AppUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * Init utils.
     * <p>Init it in the class of UtilsFileProvider.</p>
     *
     * @param app application
     */
    public static void init(final Application app) {
        if (application != null) {
            return;
        }
        application = app;
    }

    public static Application getApp() {
        return application;
    }

    /**
     * @param context
     * @param serviceName
     * @return
     */
    public static boolean isServiceRunning(Context context, String serviceName) {
        if (TextUtils.isEmpty(serviceName)) {
            return false;
        }
        ActivityManager myManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        ArrayList<ActivityManager.RunningServiceInfo> runningService = (ArrayList<ActivityManager.RunningServiceInfo>) myManager
                .getRunningServices(Integer.MAX_VALUE);
        for (int i = 0; i < runningService.size(); i++) {
            if (serviceName
                    .equals(runningService.get(i).service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
