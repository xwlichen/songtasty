package com.song.tasty.common.core.utils;

/**
 * @date : 2019-07-04 15:05
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description :
 */
public class LogUtils {
    public interface QMUILogDelegate {
        void e(final String tag, final String msg, final Object... obj);
        void w(final String tag, final String msg, final Object... obj);
        void i(final String tag, final String msg, final Object... obj);
        void d(final String tag, final String msg, final Object... obj);
        void printErrStackTrace(String tag, Throwable tr, final String format, final Object... obj);
    }

    private static QMUILogDelegate logDelegate = null;

    public static void setDelegete(QMUILogDelegate delegete) {
        logDelegate = delegete;
    }

    public static void e(final String tag, final String msg, final Object ... obj) {
        if (logDelegate != null) {
            logDelegate.e(tag, msg, obj);
        }
    }

    public static void w(final String tag, final String msg, final Object ... obj) {
        if (logDelegate != null) {
            logDelegate.w(tag, msg, obj);
        }
    }

    public static void i(final String tag, final String msg, final Object ... obj) {
        if (logDelegate != null) {
            logDelegate.i(tag, msg, obj);
        }
    }

    public static void d(final String tag, final String msg, final Object ... obj) {
        if (logDelegate != null) {
            logDelegate.d(tag, msg, obj);
        }
    }

    public static void printErrStackTrace(String tag, Throwable tr, final String format, final Object ... obj) {
        if (logDelegate != null) {
            logDelegate.printErrStackTrace(tag, tr, format, obj);
        }
    }
}
