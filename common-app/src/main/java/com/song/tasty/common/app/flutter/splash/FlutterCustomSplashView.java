package com.song.tasty.common.app.flutter.splash;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.song.tasty.common.app.R;


public class FlutterCustomSplashView extends LinearLayout {
    public FlutterCustomSplashView(Context context) {
        this(context,null);
    }

    public FlutterCustomSplashView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public FlutterCustomSplashView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context){
        ImageView imageView=new ImageView(context);
        LayoutParams layoutParams=new LayoutParams(100,100);
        imageView.setImageResource(R.mipmap.ic_logo);
        imageView.setLayoutParams(layoutParams);
        layoutParams.gravity= Gravity.CENTER;
        addView(imageView,layoutParams);
        setBackgroundColor(ContextCompat.getColor(context,R.color.color_transparent));

    }
}
