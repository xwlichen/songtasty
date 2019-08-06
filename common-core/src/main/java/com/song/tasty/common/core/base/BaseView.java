package com.song.tasty.common.core.base;

import android.os.Bundle;

import androidx.annotation.Nullable;

/**
 * @date : 2019-07-22 11:32
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description :
 */
public interface BaseView extends IView {


    /**
     * 显示toast
     *
     * @param msg
     */
    void toast(@Nullable String msg);


    /**
     * 显示loading
     */
    void showLoading();

    /**
     * 隐藏loading
     */
    void hideLoading();


    /**
     * 显示空数据布局
     */
    void showNoData();


    /**
     * 显示无网络布局
     */
    void showNoNetWork();


    /**
     * 跳转{@link android.app.Activity}
     * @param clz
     * @param bundle
     */
    void launchActivity(Class<?> clz, Bundle bundle);


    /**
     * 关闭页面
     */
    void finish();


}
