package com.song.tasty.app;

import android.app.Application;
import android.graphics.drawable.Drawable;

import com.billy.cc.core.component.CC;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.ResourceDecoder;
import com.hjq.toast.ToastUtils;
import com.song.tasty.common.app.utils.AppUtils;
import com.song.tasty.common.core.imageloader.webp.decoder.WebpBytebufferDecoder;
import com.song.tasty.common.core.imageloader.webp.decoder.WebpResourceDecoder;
import com.tencent.mmkv.MMKV;

import java.io.InputStream;
import java.nio.ByteBuffer;

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


        CC.enableVerboseLog(true);
        CC.enableDebug(true);
        CC.enableRemoteCC(true);


        // webp support
        ResourceDecoder decoder = new WebpResourceDecoder(this);
        ResourceDecoder byteDecoder = new WebpBytebufferDecoder(this);
        // use prepend() avoid intercept by default decoder
        Glide.get(this).getRegistry()
                .prepend(InputStream.class, Drawable.class, decoder)
                .prepend(ByteBuffer.class, Drawable.class, byteDecoder);
    }
}
