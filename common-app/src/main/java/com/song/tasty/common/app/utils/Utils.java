package com.song.tasty.common.app.utils;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityOptions;
import android.os.Build;
import android.os.Looper;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.smart.ui.LogUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @author lichen
 * @date ：2019-08-26 21:55
 * @email : 196003945@qq.com
 * @description :
 */
public class Utils {

    private Utils() {
    }

    /**
     * Convert a translucent themed Activity
     * {@link android.R.attr#windowIsTranslucent} to a fullscreen opaque
     * Activity.
     * <p>
     * Call this whenever the background of a translucent Activity has changed
     * to become opaque. Doing so will allow the {@link android.view.Surface} of
     * the Activity behind to be released.
     * <p>
     * This call has no effect on non-translucent activities or on activities
     * with the {@link android.R.attr#windowIsFloating} attribute.
     */
    public static void convertActivityFromTranslucent(Activity activity) {
        try {
            @SuppressLint("PrivateApi") Method method = Activity.class.getDeclaredMethod("convertFromTranslucent");
            method.setAccessible(true);
            method.invoke(activity);
        } catch (Throwable ignore) {
        }
    }

    /**
     * Convert a translucent themed Activity
     * {@link android.R.attr#windowIsTranslucent} back from opaque to
     * translucent following a call to
     * {@link #convertActivityFromTranslucent(android.app.Activity)} .
     * <p>
     * Calling this allows the Activity behind this one to be seen again. Once
     * all such Activities have been redrawn
     * <p>
     * This call has no effect on non-translucent activities or on activities
     * with the {@link android.R.attr#windowIsFloating} attribute.
     */
    public static void convertActivityToTranslucent(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            convertActivityToTranslucentAfterL(activity);
        } else {
            convertActivityToTranslucentBeforeL(activity);
        }
    }

    /**
     * Calling the convertToTranslucent method on platforms before Android 5.0
     */
    private static void convertActivityToTranslucentBeforeL(Activity activity) {
        try {
            Class<?>[] classes = Activity.class.getDeclaredClasses();
            Class<?> translucentConversionListenerClazz = null;
            for (Class clazz : classes) {
                if (clazz.getSimpleName().contains("TranslucentConversionListener")) {
                    translucentConversionListenerClazz = clazz;
                }
            }
            @SuppressLint("PrivateApi") Method method = Activity.class.getDeclaredMethod("convertToTranslucent",
                    translucentConversionListenerClazz);
            method.setAccessible(true);
            method.invoke(activity, new Object[]{
                    null
            });
        } catch (Throwable ignore) {
        }
    }

    /**
     * Calling the convertToTranslucent method on platforms after Android 5.0
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private static void convertActivityToTranslucentAfterL(Activity activity) {
        try {
            @SuppressLint("PrivateApi") Method getActivityOptions = Activity.class.getDeclaredMethod("getActivityOptions");
            getActivityOptions.setAccessible(true);
            Object options = getActivityOptions.invoke(activity);

            Class<?>[] classes = Activity.class.getDeclaredClasses();
            Class<?> translucentConversionListenerClazz = null;
            for (Class clazz : classes) {
                if (clazz.getSimpleName().contains("TranslucentConversionListener")) {
                    translucentConversionListenerClazz = clazz;
                }
            }
            @SuppressLint("PrivateApi") Method convertToTranslucent = Activity.class.getDeclaredMethod("convertToTranslucent",
                    translucentConversionListenerClazz, ActivityOptions.class);
            convertToTranslucent.setAccessible(true);
            convertToTranslucent.invoke(activity, null, options);
        } catch (Throwable ignore) {
        }
    }

    public static void assertInMainThread() {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            StackTraceElement[] elements = Thread.currentThread().getStackTrace();
            String methodMsg = null;
            if (elements != null && elements.length >= 4) {
                methodMsg = elements[3].toString();
            }
            throw new IllegalStateException("Call the method must be in main thread: " + methodMsg);
        }
    }

    public static void findAndModifyOpInBackStackRecord(FragmentManager fragmentManager, int backStackIndex, OpHandler handler) {
        if (fragmentManager == null || handler == null) {
            return;
        }
        int backStackCount = fragmentManager.getBackStackEntryCount();
        if (backStackCount > 0) {
            if (backStackIndex >= backStackCount || backStackIndex < -backStackCount) {
                LogUtils.d("findAndModifyOpInBackStackRecord", "backStackIndex error: " +
                        "backStackIndex = " + backStackIndex + " ; backStackCount = " + backStackCount);
                return;
            }
            if (backStackIndex < 0) {
                backStackIndex = backStackCount + backStackIndex;
            }
            try {
                FragmentManager.BackStackEntry backStackEntry = fragmentManager.getBackStackEntryAt(backStackIndex);

                if (handler.needReNameTag()) {
                    Field nameField = Utils.getNameField(backStackEntry);
                    if (nameField != null) {
                        nameField.setAccessible(true);
                        nameField.set(backStackEntry, handler.newTagName());
                    }
                }


                Field opsField = Utils.getOpsField(backStackEntry);
                opsField.setAccessible(true);
                Object opsObj = opsField.get(backStackEntry);
                if (opsObj instanceof List<?>) {
                    List<?> ops = (List<?>) opsObj;
                    for (Object op : ops) {
                        if (handler.handle(op)) {
                            return;
                        }
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    private static boolean sOldBackStackEntryImpl = false;

    public static Field getBackStackEntryField(FragmentManager.BackStackEntry backStackEntry, String name) {
        Field opsField = null;
        if (!sOldBackStackEntryImpl) {
            try {
                opsField = FragmentTransaction.class.getDeclaredField(name);
            } catch (NoSuchFieldException ignore) {
            }
        }

        if (opsField == null) {
            sOldBackStackEntryImpl = true;
            try {
                opsField = backStackEntry.getClass().getDeclaredField(name);
            } catch (NoSuchFieldException ignore) {
            }
        }
        return opsField;
    }

    public static Field getOpsField(FragmentManager.BackStackEntry backStackEntry) {
        return getBackStackEntryField(backStackEntry, "mOps");
    }

    public static Field getNameField(FragmentManager.BackStackEntry backStackEntry) {
        return getBackStackEntryField(backStackEntry, "mName");
    }

    private static boolean sOldOpImpl = false;

    private static Field getOpField(Object op, String fieldNameNew, String fieldNameOld) {
        Field field = null;
        if (!sOldOpImpl) {
            try {
                field = op.getClass().getDeclaredField(fieldNameNew);
            } catch (NoSuchFieldException ignore) {

            }
        }

        if (field == null) {
            sOldOpImpl = true;
            try {
                field = op.getClass().getDeclaredField(fieldNameOld);
            } catch (NoSuchFieldException ignore) {
            }
        }
        return field;
    }

    public static Field getOpCmdField(Object op) {
        return getOpField(op, "mCmd", "cmd");
    }

    public static Field getOpFragmentField(Object op) {
        return getOpField(op, "mFragment", "fragment");
    }

    public static Field getOpPopEnterAnimField(Object op) {
        return getOpField(op, "mPopEnterAnim", "popEnterAnim");
    }

    public static Field getOpPopExitAnimField(Object op) {
        return getOpField(op, "mPopExitAnim", "popExitAnim");
    }

    public interface OpHandler {
        boolean handle(Object op);

        boolean needReNameTag();

        String newTagName();
    }
}
