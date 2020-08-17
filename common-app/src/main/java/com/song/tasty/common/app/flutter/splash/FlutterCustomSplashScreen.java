package com.song.tasty.common.app.flutter.splash;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import io.flutter.embedding.android.SplashScreen;

public class FlutterCustomSplashScreen implements SplashScreen {
    FlutterCustomSplashView flutterCustomSplashView;
    @Nullable
    @Override
    public View createSplashView(@NonNull Context context, @Nullable Bundle savedInstanceState) {
        flutterCustomSplashView =new FlutterCustomSplashView(context);
        return flutterCustomSplashView;
    }

    @Override
    public void transitionToFlutter(@NonNull Runnable onTransitionComplete) {
//        tempSplashView.animateAway(onTransitionComplete);
//new Handler(Looper.getMainLooper()).postDelayed(onTransitionComplete,1000);
        onTransitionComplete.run();

    }
}
