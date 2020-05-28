package com.song.tasty.common.core.utils;

import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.content.ContextCompat;

/**
 * @author lichen
 * @date ：2020/4/27 下午3:06
 * @email : 196003945@qq.com
 * @description : 权限检验
 */
public final class PermissionsUtils {


    public static boolean isGranted(final String... permissions) {
        for (String permission : permissions) {
            if (!isGranted(permission)) {
                return false;
            }
        }
        return true;
    }


    private static boolean isGranted(final String permission) {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M
                || PackageManager.PERMISSION_GRANTED
                == ContextCompat.checkSelfPermission(AppUtils.getApp(), permission);
    }
}
