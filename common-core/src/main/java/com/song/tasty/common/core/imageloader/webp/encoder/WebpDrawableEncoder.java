package com.song.tasty.common.core.imageloader.webp.encoder;

import android.util.Log;

import com.bumptech.glide.load.EncodeStrategy;
import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.ResourceEncoder;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.util.ByteBufferUtil;
import com.song.tasty.common.core.imageloader.webp.WebpDrawable;

import java.io.File;
import java.io.IOException;

import androidx.annotation.NonNull;

/**
 * @date : 2019-07-25 17:53
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description :
 */
public class WebpDrawableEncoder implements ResourceEncoder<WebpDrawable> {
    private static final String TAG = WebpDrawableEncoder.class.getSimpleName();

    @NonNull
    @Override
    public EncodeStrategy getEncodeStrategy(@NonNull Options options) {
        return EncodeStrategy.SOURCE;
    }

    @Override
    public boolean encode(@NonNull Resource<WebpDrawable> data, @NonNull File file,
                          @NonNull Options options) {
        WebpDrawable drawable = data.get();
        boolean success = false;
        try {
            ByteBufferUtil.toFile(drawable.getBuffer(), file);
            success = true;
        } catch (IOException e) {
            if (Log.isLoggable(TAG, Log.WARN)) {
                Log.w(TAG, "Failed to encode Webp drawable data", e);
            }
        }
        return success;
    }
}
