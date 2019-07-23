package com.song.tasty.main;

import android.app.Application;

import com.hjq.toast.ToastUtils;
import com.song.tasty.common.app.utils.AppUtils;
import com.tencent.mmkv.MMKV;

/**
 * @date : 2019-07-23 11:33
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description :
 */
public class AppApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AppUtils.init(getApplicationContext());
        //腾讯kv本地存储
        MMKV.initialize(getApplicationContext());
        //
        ToastUtils.init(this);
    }
}
