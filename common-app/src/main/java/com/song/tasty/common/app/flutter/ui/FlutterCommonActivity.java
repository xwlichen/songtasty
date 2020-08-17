package com.song.tasty.common.app.flutter.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.hjq.toast.ToastUtils;
import com.smart.ui.utils.SMUIStatusBarHelper;
import com.smart.ui.widget.dialog.SMUITipDialog;
import com.song.tasty.common.app.R;
import com.song.tasty.common.app.flutter.splash.FlutterCustomSplashScreen;
import com.song.tasty.common.core.utils.LogUtils;
import com.song.tasty.common.core.utils.SmartUtils;

import java.lang.reflect.Constructor;

import io.flutter.embedding.android.FlutterActivity;
import io.flutter.embedding.android.SplashScreen;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugins.GeneratedPluginRegistrant;

public class FlutterCommonActivity extends FlutterActivity {

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


    @Override
    public void configureFlutterEngine(@NonNull FlutterEngine flutterEngine) {
//        super.configureFlutterEngine(flutterEngine);
        GeneratedPluginRegistrant.registerWith(flutterEngine);
        MethodChannel methodChannel = new MethodChannel(flutterEngine.getDartExecutor(), "com.song.tasty.module.flutterpage.host"); //此处名称应与Flutter端保持一致
        //接收Flutter消息
        methodChannel.setMethodCallHandler(new MethodChannel.MethodCallHandler() {
            @Override
            public void onMethodCall(@NonNull MethodCall call, @NonNull MethodChannel.Result result) {
                System.out.println("MethodChannel call.method:"+call.method+ "  call arguments:"+call.arguments);
                switch (call.method){
                    case "envType":
                        result.success("2");
                        break;
                    case "toast":
                        String msg = call.argument("msg");
                        break;
                    default:
                        result.error("404", "未匹配到对应的方法"+call.method, null);
                }
            }
        });

        methodChannel.invokeMethod("aaa", "c") ;//发送消息
    }

    @NonNull
    public static NewEngineIntentBuilder withNewEngine() {
        Class<?> builderClass = null;
        try {
            builderClass = Class.forName(NewEngineIntentBuilder.class.getName());
            Constructor builderConstructor = builderClass.getDeclaredConstructor(Class.class);
            Object builder=builderConstructor.newInstance(FlutterCommonActivity.class);
            return (NewEngineIntentBuilder) builder;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    public static CachedEngineIntentBuilder withCachedEngine(@NonNull String cachedEngineId) {
        Class<?> builderClass = null;
        try {
            builderClass = Class.forName(CachedEngineIntentBuilder.class.getName());
            Constructor builderConstructor = builderClass.getDeclaredConstructor(Class.class,String.class);
            Object builder=builderConstructor.newInstance(FlutterCommonActivity.class,cachedEngineId);
            return (CachedEngineIntentBuilder) builder;
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


//    protected void destroy() {
//
//        this.mCalled = true;
//
//        // dismiss any dialogs we are managing.
//        if (mManagedDialogs != null) {
//            final int numDialogs = mManagedDialogs.size();
//            for (int i = 0; i < numDialogs; i++) {
//                final ManagedDialog md = mManagedDialogs.valueAt(i);
//                if (md.mDialog.isShowing()) {
//                    md.mDialog.dismiss();
//                }
//            }
//            mManagedDialogs = null;
//        }
//
//        // close any cursors we are managing.
//        synchronized (mManagedCursors) {
//            int numCursors = mManagedCursors.size();
//            for (int i = 0; i < numCursors; i++) {
//                ManagedCursor c = mManagedCursors.get(i);
//                if (c != null) {
//                    c.mCursor.close();
//                }
//            }
//            mManagedCursors.clear();
//        }
//
//        // Close any open search dialog
//        if (mSearchManager != null) {
//            mSearchManager.stopSearch();
//        }
//
//        if (mActionBar != null) {
//            mActionBar.onDestroy();
//        }
//
//        dispatchActivityDestroyed();
//
//        notifyContentCaptureManagerIfNeeded(CONTENT_CAPTURE_STOP);
//    }


    @SuppressLint("MissingSuperCall")
    @Override
    protected void onDestroy() {
        super.onDestroy();
//        if (super!=null) {
//            Log.e("xwlc", getParent().getClass().getName());
//        }else{
//            Log.e("xwlc","getparent is null");
//        }


//        try {
//            Method method=getClass().getMethod("onDestroy");
//                method.invoke((Activity)this);
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        getParent().onDestroy();onDestroy
//        delegate.onDestroyView();
//        delegate.onDetach();
//        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)

        LogUtils.e("xwlc","onDestory");
        Log.e("xwlc","onDestroy");
        Log.e("xwlc","cost time :"+(System.currentTimeMillis()-time));
    }

    long time;

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_no_effect);
        Log.e("xwlc","finish");
        LogUtils.e("xwlc","finish");
        time=System.currentTimeMillis();
//        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

    }




}
