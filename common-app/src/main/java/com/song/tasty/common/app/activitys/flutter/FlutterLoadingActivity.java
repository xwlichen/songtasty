package com.song.tasty.common.app.activitys.flutter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.song.tasty.common.core.utils.LogUtils;

import java.lang.reflect.Constructor;

import io.flutter.embedding.android.FlutterActivity;
import io.flutter.embedding.android.SplashScreen;

public class FlutterLoadingActivity extends FlutterActivity {

    @Nullable
    @Override
    public SplashScreen provideSplashScreen() {
        LogUtils.e("xwlc","provideSplashScreen");
        return new FlutterCustomSplashScreen();
//        return null;
    }

    @NonNull
    public static NewEngineIntentBuilder withNewEngine() {
        Class<?> builderClass = null;//完整类名
        try {
            builderClass = Class.forName(NewEngineIntentBuilder.class.getName());
            Constructor builderConstructor = builderClass.getDeclaredConstructor(Class.class);//获得实例
            Object builder=builderConstructor.newInstance(FlutterLoadingActivity.class);
            return (NewEngineIntentBuilder) builder;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }


}
