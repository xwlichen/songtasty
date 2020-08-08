package com.song.tasty.common.app.activitys.flutter;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Lifecycle;

import com.hjq.toast.ToastUtils;
import com.smart.ui.utils.SMUIStatusBarHelper;
import com.smart.ui.widget.dialog.SMUITipDialog;
import com.song.tasty.common.app.R;
import com.song.tasty.common.core.utils.KeyBoardUtils;
import com.song.tasty.common.core.utils.LogUtils;
import com.song.tasty.common.core.utils.SmartUtils;

import java.lang.reflect.Constructor;

import io.flutter.embedding.android.FlutterActivity;
import io.flutter.embedding.android.SplashScreen;

public class FlutterCustomSplashActivity extends FlutterActivity {

    protected SMUITipDialog smuiTipDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        SMUIStatusBarHelper.translucent(this);
        SMUIStatusBarHelper.setStatusBarLightMode(this);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public SplashScreen provideSplashScreen() {
        return new FlutterCustomSplashScreen();
    }

    @NonNull
    public static NewEngineIntentBuilder withNewEngine() {
        Class<?> builderClass = null;
        try {
            builderClass = Class.forName(NewEngineIntentBuilder.class.getName());
            Constructor builderConstructor = builderClass.getDeclaredConstructor(Class.class);
            Object builder=builderConstructor.newInstance(FlutterCustomSplashActivity.class);
            return (NewEngineIntentBuilder) builder;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }






    public void toast(@Nullable String msg) {
        ToastUtils.show(msg);
    }

    public void showLoading() {

    }

    public void hideLoading() {

    }

    public void showNoData() {

    }


    public void showNoNetWork() {

    }

    public void showError() {

    }

    public void launchActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent(this, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        SmartUtils.startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        delegate.onDestroyView();
//        delegate.onDetach();
//        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY);
        LogUtils.e("xwlc","onDestory");
        Log.e("xwlc","onDestroy");
    }


    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        Log.e("xwlc","finish");
        LogUtils.e("xwlc","finish");
//        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

    }


}
