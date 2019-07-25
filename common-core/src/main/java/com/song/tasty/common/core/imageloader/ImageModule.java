//package com.song.tasty.common.core.imageloader;
//
//import android.content.Context;
//import android.graphics.drawable.PictureDrawable;
//
//import androidx.annotation.NonNull;
//
//import com.bumptech.glide.Glide;
//import com.bumptech.glide.Registry;
//import com.bumptech.glide.annotation.GlideModule;
//import com.bumptech.glide.module.AppGlideModule;
//import com.song.tasty.common.core.imageloader.webp.WebpDrawable;
//import com.song.tasty.common.core.imageloader.webp.decoder.WebpBytebufferDecoder;
//import com.song.tasty.common.core.imageloader.webp.decoder.WebpResourceDecoder;
//import com.song.tasty.common.core.imageloader.webp.encoder.WebpDrawableEncoder;
//
//import java.io.InputStream;
//import java.nio.ByteBuffer;
//
///**
// * @author lichen
// * @date ：2019-07-25 21:09
// * @email : 196003945@qq.com
// * @description :
// */
//@GlideModule
//public class ImageModule extends AppGlideModule {
//    @Override
//    public void registerComponents(@NonNull Context context, @NonNull Glide glide,
//                                   @NonNull Registry registry) {
//
//        registry.register(WebpDrawable.class, new WebpDrawableEncoder())
//                .append(InputStream.class, WebpDrawable.class, new WebpResourceDecoder(context))
//                .append(ByteBuffer.class, WebpDrawable.class, new WebpBytebufferDecoder(context));
//    }
//
//    // Disable manifest parsing to avoid adding similar modules twice.
//    @Override
//    public boolean isManifestParsingEnabled() {
//        return false;
//    }
//}