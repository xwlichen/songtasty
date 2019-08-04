package com.song.tasty.common.core.utils;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Rect;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;

/**
 * @author lichen
 * @date ：2018/9/19 下午7:52
 * @email : 196003945@qq.com
 * @description :
 */
public class KeyBoardUtils {

    protected static KeyBoardUtils instance;
    private ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener;

    public synchronized static KeyBoardUtils getInstance() {
        if (instance == null) {
            instance = new KeyBoardUtils();
        }
        return instance;

    }


    //监听键盘弹出
    private Activity activity;
    private OnKeyboardStatusChangeListener onKeyboardStatusChangeListener;
    private int windowBottom = -1;
    private int keyboardHeight = 0;


    public void initKeyBorad(Activity activity) {
        this.activity = activity;
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        if (activity.getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        initGlobalLayoutListener();
        View content = activity.findViewById(android.R.id.content);
        // content.addOnLayoutChangeListener(listener); 这个方法有时会出现一些问题
        content.getViewTreeObserver().addOnGlobalLayoutListener(onGlobalLayoutListener);

    }


    public void release() {
        if (activity != null) {
            View content = activity.findViewById(android.R.id.content);
            removeOnGlobalLayoutListener(content, onGlobalLayoutListener);
            activity = null;
        }
        if (onGlobalLayoutListener != null) {
            onGlobalLayoutListener = null;
        }

    }


    //监听视图树布局繁盛变化
    private void initGlobalLayoutListener() {
        onGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect rect = new Rect();
                //获取windown的可见区域
                activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
                Log.d("KeyboardHelper", "onGlobalLayout: " + rect + ", " + windowBottom);
                int newBottom = rect.bottom;
                if (windowBottom != -1 && windowBottom != newBottom) {
                    if (newBottom < windowBottom) {
                        // keyboard pop
                        keyboardHeight = windowBottom - newBottom;
                        if (onKeyboardStatusChangeListener != null) {
                            onKeyboardStatusChangeListener.onKeyboardPop(keyboardHeight);
                        }
                    } else {
                        // keyboard close
                        if (onKeyboardStatusChangeListener != null) {
                            onKeyboardStatusChangeListener.onKeyboardClose(keyboardHeight);
                        }
                    }
                }
                windowBottom = newBottom;
            }
        };

    }

    public void setOnKeyboardStatusChangeListener(Activity activity,
                                                  OnKeyboardStatusChangeListener onKeyboardStatusChangeListener) {
        this.onKeyboardStatusChangeListener = onKeyboardStatusChangeListener;
        initKeyBorad(activity);
    }

    public interface OnKeyboardStatusChangeListener {

        void onKeyboardPop(int keyboardHeight);

        void onKeyboardClose(int keyboardHeight);

    }


    /**
     * 删除
     *
     * @param view
     * @param onGlobalLayoutListener
     */
    public void removeOnGlobalLayoutListener(View view, ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener) {
        if (Build.VERSION.SDK_INT < 16) {
            view.getViewTreeObserver().removeGlobalOnLayoutListener(onGlobalLayoutListener);
        } else {
            view.getViewTreeObserver().removeOnGlobalLayoutListener(onGlobalLayoutListener);
        }
    }


}
