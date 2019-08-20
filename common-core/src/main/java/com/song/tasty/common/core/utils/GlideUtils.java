package com.song.tasty.common.core.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import java.io.File;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.annotation.RawRes;

/**
 * @date : 2019-08-20 13:46
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description :
 */
public class GlideUtils {

    public static final int PLACEHOLDER = Color.TRANSPARENT;
    public static final int ERROR = Color.TRANSPARENT;


    public static void loadImage(Context context, String url, ImageView imageView) {
        loadImage(context, url, 0, 0, imageView, 0, 0);
    }


    public static void loadImage(Context context, String url, ImageView imageView, @DrawableRes int placeholder, @DrawableRes int error) {
        loadImage(context, url, placeholder, error, imageView, 0, 0);
    }

    public static void loadImageSize(Context context, String url, ImageView imageView, int width, int height) {
        loadImage(context, url, 0, 0, imageView, width, height);
    }


    public static void loadImage(Context context, String url, @DrawableRes int placeholder, @DrawableRes int error, ImageView imageView, int width, int height) {
        RequestOptions options = new RequestOptions()
                //占位图
                .placeholder(placeholder == 0 ? PLACEHOLDER : placeholder)
                //错误图
                .error(error == 0 ? ERROR : error)
                .override(width == 0 ? Target.SIZE_ORIGINAL : width, height == 0 ? Target.SIZE_ORIGINAL : height)
                .diskCacheStrategy(DiskCacheStrategy.DATA);
        Glide
                .with(context)
                .load(url)
                .apply(options)
                .into(imageView);

    }


    public static void loadImage(Context context, File file, ImageView imageView) {
        loadImage(context, file, 0, 0, imageView, 0, 0);
    }

    public static void loadImage(Context context, File file, ImageView imageView, @DrawableRes int placeholder, @DrawableRes int error) {
        loadImage(context, file, placeholder, error, imageView, 0, 0);
    }

    public static void loadImageSize(Context context, File file, ImageView imageView, int width, int height) {
        loadImage(context, file, 0, 0, imageView, width, height);
    }

    public static void loadImage(Context context, File file, @DrawableRes int placeholder, @DrawableRes int error, ImageView imageView, int width, int height) {
        RequestOptions options = new RequestOptions()
                //占位图
                .placeholder(placeholder == 0 ? PLACEHOLDER : placeholder)
                //错误图
                .error(error == 0 ? ERROR : error)
                .override(width == 0 ? Target.SIZE_ORIGINAL : width, height == 0 ? Target.SIZE_ORIGINAL : height)
                .diskCacheStrategy(DiskCacheStrategy.DATA);
        Glide
                .with(context)
                .load(file)
                .apply(options)
                .into(imageView);

    }


    public static void loadImage(Context context, Bitmap bitmap, ImageView imageView) {
        loadImage(context, bitmap, 0, 0, imageView, 0, 0);
    }

    public static void loadImage(Context context, Bitmap bitmap, ImageView imageView, @DrawableRes int placeholder, @DrawableRes int error) {
        loadImage(context, bitmap, placeholder, error, imageView, 0, 0);
    }

    public static void loadImageSize(Context context, Bitmap bitmap, ImageView imageView, int width, int height) {
        loadImage(context, bitmap, 0, 0, imageView, width, height);
    }

    public static void loadImage(Context context, Bitmap bitmap, @DrawableRes int placeholder, @DrawableRes int error, ImageView imageView, int width, int height) {
        RequestOptions options = new RequestOptions()
                //占位图
                .placeholder(placeholder == 0 ? PLACEHOLDER : placeholder)
                //错误图
                .error(error == 0 ? ERROR : error)
                .override(width == 0 ? Target.SIZE_ORIGINAL : width, height == 0 ? Target.SIZE_ORIGINAL : height)
                .diskCacheStrategy(DiskCacheStrategy.DATA);
        Glide
                .with(context)
                .load(bitmap)
                .apply(options)
                .into(imageView);

    }


    public static void loadImage(Context context, Drawable drawable, ImageView imageView) {
        loadImage(context, drawable, 0, 0, imageView, 0, 0);
    }

    public static void loadImage(Context context, Drawable drawable, ImageView imageView, @DrawableRes int placeholder, @DrawableRes int error) {
        loadImage(context, drawable, placeholder, error, imageView, 0, 0);
    }

    public static void loadImageSize(Context context, Drawable drawable, ImageView imageView, int width, int height) {
        loadImage(context, drawable, 0, 0, imageView, width, height);
    }

    public static void loadImage(Context context, Drawable drawable, @DrawableRes int placeholder, @DrawableRes int error, ImageView imageView, int width, int height) {
        RequestOptions options = new RequestOptions()
                //占位图
                .placeholder(placeholder == 0 ? PLACEHOLDER : placeholder)
                //错误图
                .error(error == 0 ? ERROR : error)
                .override(width == 0 ? Target.SIZE_ORIGINAL : width, height == 0 ? Target.SIZE_ORIGINAL : height)
                .diskCacheStrategy(DiskCacheStrategy.DATA);
        Glide
                .with(context)
                .load(drawable)
                .apply(options)
                .into(imageView);

    }


    public static void loadImage(Context context, Uri uri, ImageView imageView) {
        loadImage(context, uri, 0, 0, imageView, 0, 0);
    }

    public static void loadImage(Context context, Uri uri, ImageView imageView, @DrawableRes int placeholder, @DrawableRes int error) {
        loadImage(context, uri, placeholder, error, imageView, 0, 0);
    }

    public static void loadImageSize(Context context, Uri uri, ImageView imageView, int width, int height) {
        loadImage(context, uri, 0, 0, imageView, width, height);
    }

    public static void loadImage(Context context, Uri uri, @DrawableRes int placeholder, @DrawableRes int error, ImageView imageView, int width, int height) {
        RequestOptions options = new RequestOptions()
                //占位图
                .placeholder(placeholder == 0 ? PLACEHOLDER : placeholder)
                //错误图
                .error(error == 0 ? ERROR : error)
                .override(width == 0 ? Target.SIZE_ORIGINAL : width, height == 0 ? Target.SIZE_ORIGINAL : height)
                .diskCacheStrategy(DiskCacheStrategy.DATA);
        Glide
                .with(context)
                .load(uri)
                .apply(options)
                .into(imageView);

    }


    public static void loadImage(Context context, @RawRes @DrawableRes @Nullable Integer resourceId, ImageView imageView) {
        loadImage(context, resourceId, 0, 0, imageView, 0, 0);
    }

    public static void loadImage(Context context, @RawRes @DrawableRes @Nullable Integer resourceId, ImageView imageView, @DrawableRes int placeholder, @DrawableRes int error) {
        loadImage(context, resourceId, placeholder, error, imageView, 0, 0);
    }

    public static void loadImageSize(Context context, @RawRes @DrawableRes @Nullable Integer resourceId, ImageView imageView, int width, int height) {
        loadImage(context, resourceId, 0, 0, imageView, width, height);
    }

    public static void loadImage(Context context, @RawRes @DrawableRes @Nullable Integer resourceId, @DrawableRes int placeholder, @DrawableRes int error, ImageView imageView, int width, int height) {
        RequestOptions options = new RequestOptions()
                //占位图
                .placeholder(placeholder == 0 ? PLACEHOLDER : placeholder)
                //错误图
                .error(error == 0 ? ERROR : error)
                .override(width == 0 ? Target.SIZE_ORIGINAL : width, height == 0 ? Target.SIZE_ORIGINAL : height)
                .diskCacheStrategy(DiskCacheStrategy.DATA);
        Glide
                .with(context)
                .load(resourceId)
                .apply(options)
                .into(imageView);

    }
}
