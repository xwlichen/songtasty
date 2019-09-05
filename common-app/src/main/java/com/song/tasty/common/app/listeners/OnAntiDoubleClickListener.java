package com.song.tasty.common.app.listeners;

import android.view.View;

/**
 * @date : 2019-09-05 18:24
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description : 防多次点击，替换系统的click事件
 */
public abstract class OnAntiDoubleClickListener implements View.OnClickListener {
    // 两次点击按钮之间的点击间隔不能少于1000毫秒
    private static final int MIN_CLICK_DELAY_TIME = 700;
    private static long lastClickTime;

    public abstract void onAntiDoubleClick(View v);

    @Override
    public void onClick(View v) {
        long curClickTime = System.currentTimeMillis();
        if ((curClickTime - lastClickTime) >= MIN_CLICK_DELAY_TIME) {
            // 超过点击间隔后再将lastClickTime重置为当前点击时间
            lastClickTime = curClickTime;
            onAntiDoubleClick(v);
        }
    }
}