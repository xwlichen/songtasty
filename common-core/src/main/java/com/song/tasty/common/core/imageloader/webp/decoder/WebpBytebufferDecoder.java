package com.song.tasty.common.core.imageloader.webp.decoder;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.ImageHeaderParser;
import com.bumptech.glide.load.ImageHeaderParserUtils;
import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.ResourceDecoder;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.Initializable;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.engine.bitmap_recycle.ArrayPool;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.UnitTransformation;
import com.bumptech.glide.load.resource.drawable.DrawableResource;
import com.bumptech.glide.load.resource.gif.GifBitmapProvider;
import com.facebook.animated.webp.WebPImage;
import com.facebook.soloader.SoLoader;
import com.song.tasty.common.core.imageloader.webp.WebpDrawable;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.List;

/**
 * @date : 2019-07-25 17:50
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description :
 */
public class WebpBytebufferDecoder implements ResourceDecoder<ByteBuffer, WebpDrawable> {

    public final String TAG = WebpBytebufferDecoder.class.getSimpleName();
    private final List<ImageHeaderParser> parsers;
    private final Context context;
    private final BitmapPool bitmapPool;
    private final GifBitmapProvider provider;
    private final ArrayPool byteArrayPool;


    public WebpBytebufferDecoder(Context context) {
        this(context, Glide.get(context).getRegistry().getImageHeaderParsers(), Glide.get(context).getArrayPool(),
                Glide.get(context).getBitmapPool());
        // if not init Soloader, will get error when decode
        try {
            SoLoader.init(context, 0);
        } catch (IOException e) {
            Log.v(TAG, "Failed to init SoLoader", e);
        }
    }


    public WebpBytebufferDecoder(Context context, List<ImageHeaderParser> parsers, ArrayPool byteArrayPool, BitmapPool bitmapPool) {
        this.context = context.getApplicationContext();
        this.parsers = parsers;
        this.bitmapPool = bitmapPool;
        this.provider = new GifBitmapProvider(bitmapPool, byteArrayPool);
        this.byteArrayPool = byteArrayPool;

    }

    @Override
    public boolean handles(@NonNull ByteBuffer buffer, @NonNull Options options) throws IOException {
        ImageHeaderParser.ImageType type = ImageHeaderParserUtils.getType(this.parsers, buffer);
        return type == ImageHeaderParser.ImageType.WEBP || type == ImageHeaderParser.ImageType.WEBP_A;
    }

    @Nullable
    @Override
    public Resource<WebpDrawable> decode(@NonNull ByteBuffer buffer, int width, int height, @NonNull Options options) throws IOException {

        byte[] arr;
        if (buffer.hasArray()) {
            arr = buffer.array();
        } else {
            arr = new byte[buffer.capacity()];
            buffer.get(arr);
        }


        WebPImage webp = WebPImage.create(arr);

        int sampleSize = getSampleSize(webp.getWidth(), webp.getHeight(), width, height);
        WebpDecoder webpDecoder = new WebpDecoder(this.provider, webp, sampleSize);
        Bitmap firstFrame = webpDecoder.getNextFrame();
        if (firstFrame == null) {
            return null;
        }

        Transformation<Bitmap> unitTransformation = UnitTransformation.get();

        return new WebpDrawableResource(new WebpDrawable(this.context, webpDecoder, this.bitmapPool, unitTransformation, width, height,
                firstFrame));
    }

    private static int getSampleSize(int srcWidth, int srcHeight, int targetWidth, int targetHeight) {
        int exactSampleSize = Math.min(srcHeight / targetHeight,
                srcWidth / targetWidth);
        int powerOfTwoSampleSize = exactSampleSize == 0 ? 0 : Integer.highestOneBit(exactSampleSize);
        // Although functionally equivalent to 0 for BitmapFactory, 1 is a safer default for our code
        // than 0.
        int sampleSize = Math.max(1, powerOfTwoSampleSize);
        return sampleSize;
    }


    public class WebpDrawableResource extends DrawableResource<WebpDrawable> implements Initializable {
        public WebpDrawableResource(WebpDrawable drawable) {
            super(drawable);
        }

        @Override
        public Class<WebpDrawable> getResourceClass() {
            return WebpDrawable.class;
        }

        @Override
        public int getSize() {
            return drawable.getSize();
        }

        @Override
        public void recycle() {

        }

        @Override
        public void initialize() {

        }
    }
}
