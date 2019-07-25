package com.song.tasty.common.core.imageloader.webp.decoder;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import androidx.annotation.NonNull;

import com.bumptech.glide.gifdecoder.GifDecoder;
import com.bumptech.glide.gifdecoder.GifHeader;
import com.facebook.animated.webp.WebPFrame;
import com.facebook.animated.webp.WebPImage;
import com.facebook.imagepipeline.animated.base.AnimatedDrawableFrameInfo;

import java.io.InputStream;
import java.nio.ByteBuffer;

/**
 * @date : 2019-07-25 16:31
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description :
 */
public class WebpDecoder implements GifDecoder {

    private WebPImage webPImage;
    private GifDecoder.BitmapProvider provider;
    private int framePointer;
    private int[] frameDurations;
    private int downsampledWidth;
    private int downsampledHeight;
    private boolean[] keyFrame;
    private int sampleSize;
    // 缓存上一帧，用于非关键帧
    private Bitmap cacheBmp;


    public WebpDecoder(GifDecoder.BitmapProvider provider, WebPImage webPImage, int sampleSize) {
        this.provider = provider;
        this.webPImage = webPImage;
        this.frameDurations = webPImage.getFrameDurations();
        this.keyFrame = new boolean[this.frameDurations.length];
        downsampledWidth = webPImage.getWidth() / sampleSize;
        downsampledHeight = webPImage.getHeight() / sampleSize;
        this.sampleSize = sampleSize;
    }

    @Override
    public int getWidth() {
        return this.webPImage.getWidth();
    }

    @Override
    public int getHeight() {
        return this.webPImage.getHeight();
    }

    @Override
    public ByteBuffer getData() {
        return null;
    }

    @Override
    public int getStatus() {
        return GifDecoder.STATUS_OK;
    }

    @Override
    public void advance() {
        this.framePointer = (this.framePointer + 1) % this.webPImage.getFrameCount();
    }

    @Override
    public int getDelay(int n) {
        int delay = -1;
        if ((n >= 0) && (n < this.frameDurations.length)) {
            delay = this.frameDurations[n];
        }
        return delay;
    }

    @Override
    public int getNextDelay() {
        if (this.frameDurations.length == 0 || this.framePointer < 0) {
            return 0;
        }

        return getDelay(this.framePointer);
    }

    @Override
    public int getFrameCount() {
        return this.webPImage.getFrameCount();
    }

    @Override
    public int getCurrentFrameIndex() {
        return this.framePointer;
    }

    @Override
    public void resetFrameIndex() {
        this.framePointer = -1;
    }

    @Override
    public int getLoopCount() {
        return this.webPImage.getLoopCount();
    }

    @Override
    public int getNetscapeLoopCount() {
        return this.webPImage.getLoopCount();
    }

    @Override
    public int getTotalIterationCount() {
        if (this.webPImage.getLoopCount() == 0) {
            return TOTAL_ITERATION_COUNT_FOREVER;
        }
        return this.webPImage.getFrameCount() + 1;
    }

    @Override
    public int getByteSize() {
        return this.webPImage.getSizeInBytes();
    }

    @Override
    public Bitmap getNextFrame() {
        Bitmap result = this.provider.obtain(downsampledWidth, downsampledHeight, Bitmap.Config.ARGB_8888);
        int currentIndex = getCurrentFrameIndex();
        WebPFrame currentFrame = this.webPImage.getFrame(currentIndex);

        // render key frame
        if (isKeyFrame(currentIndex)) {
            this.keyFrame[currentIndex] = true;
            currentFrame.renderFrame(downsampledWidth, downsampledHeight, result);

            this.cacheBmp = result;
        } else {
            int frameW = currentFrame.getWidth() / this.sampleSize;
            int frameH = currentFrame.getHeight() / this.sampleSize;
            int offX = currentFrame.getXOffset() / this.sampleSize;
            int offY = currentFrame.getYOffset() / this.sampleSize;

            Canvas canvas = new Canvas(result);
            canvas.drawBitmap(this.cacheBmp, 0, 0, null);

            Bitmap frameBmp = this.provider.obtain(frameW, frameH, Bitmap.Config.ARGB_8888);
            currentFrame.renderFrame(frameW, frameH, frameBmp);
            canvas.drawBitmap(frameBmp, offX, offY, null);

            this.provider.release(frameBmp);
            this.cacheBmp = result;
        }
        currentFrame.dispose();
        return result;
    }

    private boolean isKeyFrame(int index) {
        if (index == 0) {
            return true;
        }

        AnimatedDrawableFrameInfo curFrameInfo = this.webPImage.getFrameInfo(index);
        AnimatedDrawableFrameInfo prevFrameInfo = this.webPImage.getFrameInfo(index - 1);
        if (curFrameInfo.blendOperation == AnimatedDrawableFrameInfo.BlendOperation.NO_BLEND
                && isFullFrame(curFrameInfo)) {
            return true;
        } else {
            return prevFrameInfo.disposalMethod == AnimatedDrawableFrameInfo.DisposalMethod.DISPOSE_TO_BACKGROUND
                    && isFullFrame(prevFrameInfo);
        }
    }

    private boolean isFullFrame(AnimatedDrawableFrameInfo info) {
        return info.yOffset == 0 && info.xOffset == 0
                && this.webPImage.getHeight() == info.width
                && this.webPImage.getWidth() == info.height;
    }

    @Override
    public int read(InputStream inputStream, int i) {
        return GifDecoder.STATUS_OK;
    }

    @Override
    public void clear() {
        this.webPImage.dispose();
        this.webPImage = null;
    }

    @Override
    public void setData(GifHeader gifHeader, byte[] bytes) {

    }

    @Override
    public void setData(GifHeader gifHeader, ByteBuffer byteBuffer) {

    }

    @Override
    public void setData(GifHeader gifHeader, ByteBuffer byteBuffer, int i) {

    }

    @Override
    public int read(byte[] bytes) {
        return GifDecoder.STATUS_OK;
    }

    @Override
    public void setDefaultBitmapConfig(@NonNull Bitmap.Config format) {
    }
}
