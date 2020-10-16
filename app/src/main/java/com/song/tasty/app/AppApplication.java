package com.song.tasty.app;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import com.billy.cc.core.component.CC;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.ResourceDecoder;
import com.hjq.toast.ToastUtils;
import com.smart.utils.LogUtils;
import com.song.tasty.app.matrix.config.DynamicConfigImpl;
import com.song.tasty.common.core.AppManager;
import com.song.tasty.common.core.imageloader.webp.decoder.WebpBytebufferDecoder;
import com.song.tasty.common.core.imageloader.webp.decoder.WebpResourceDecoder;
import com.tencent.matrix.Matrix;
import com.tencent.matrix.iocanary.IOCanaryPlugin;
import com.tencent.matrix.iocanary.config.IOConfig;
import com.tencent.matrix.resource.ResourcePlugin;
import com.tencent.matrix.resource.config.ResourceConfig;
import com.tencent.matrix.trace.TracePlugin;
import com.tencent.matrix.trace.config.TraceConfig;
import com.tencent.matrix.util.MatrixLog;
import com.tencent.mmkv.MMKV;

import java.io.InputStream;
import java.nio.ByteBuffer;

import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.embedding.engine.FlutterEngineCache;
import io.flutter.embedding.engine.dart.DartExecutor;

/**
 * @date : 2019-07-23 11:33
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description :
 */
public class AppApplication extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        AppManager.getAppManager().init(this);
        //腾讯kv本地存储
        MMKV.initialize(getApplicationContext());
        //
        ToastUtils.init(this);
        ToastUtils.setGravity(Gravity.BOTTOM, 0, 200);
        ToastUtils.getToast().setDuration(Toast.LENGTH_SHORT);


        CC.enableVerboseLog(true);
        CC.enableDebug(true);
        CC.enableRemoteCC(true);

        // webp support
        ResourceDecoder decoder = new WebpResourceDecoder(this);
        ResourceDecoder byteDecoder = new WebpBytebufferDecoder(this);
        // use prepend() avoid intercept by default decoder
        Glide.get(this).getRegistry()
                .prepend(InputStream.class, Drawable.class, decoder)
                .prepend(ByteBuffer.class, Drawable.class, byteDecoder);

        initMatrix();
        String path=getExternalFilesDir(null).getAbsolutePath()+"/log";
        String cachePath=getExternalCacheDir().getAbsolutePath()+"/log";

        Log.e("xw","path:"+path);
        Log.e("xw","cachePath:"+cachePath);

        LogUtils.initXLog("st", path,cachePath,"",0,true);

//
//        Log.e("xwlc","FlutterEngine start");
//
//        // Instantiate a FlutterEngine.
//        FlutterEngine flutterEngine = new FlutterEngine(this);
//        // Configure an initial route.
//        flutterEngine.getNavigationChannel().setInitialRoute("/");
//        // Start executing Dart code to pre-warm the FlutterEngine.
//        flutterEngine.getDartExecutor().executeDartEntrypoint(
//                DartExecutor.DartEntrypoint.createDefault()
//        );
//        // Cache the FlutterEngine to be used by FlutterActivity or FlutterFragment.
//        FlutterEngineCache
//                .getInstance()
//                .put("my_engine_id", flutterEngine);
//        Log.e("xwlc","FlutterEngine end");

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }


    private void initMatrix(){
        super.onCreate();
        DynamicConfigImpl dynamicConfig = new DynamicConfigImpl();
        boolean matrixEnable = dynamicConfig.isMatrixEnable();
        boolean fpsEnable = dynamicConfig.isFPSEnable();
        boolean traceEnable = dynamicConfig.isTraceEnable();

        MatrixLog.i("xw", "MatrixApplication.onCreate");

        Matrix.Builder builder = new Matrix.Builder(this);
//        builder.patchListener(new TestPluginListener(this));

        //trace
        TraceConfig traceConfig = new TraceConfig.Builder()
                .dynamicConfig(dynamicConfig)
                .enableFPS(fpsEnable)
                .enableEvilMethodTrace(traceEnable)
                .enableAnrTrace(traceEnable)
                .enableStartup(traceEnable)
                .splashActivities("com.song.tasty.app.mvvm.ui.SplashActivity;")
                .isDebug(true)
                .isDevEnv(false)
                .build();

        TracePlugin tracePlugin = (new TracePlugin(traceConfig));
        builder.plugin(tracePlugin);

        if (matrixEnable) {

            //resource
            Intent intent = new Intent();
            ResourceConfig.DumpMode mode = ResourceConfig.DumpMode.AUTO_DUMP;
            MatrixLog.i("xw", "Dump Activity Leak Mode=%s", mode);
            intent.setClassName(this.getPackageName(), "com.tencent.mm.ui.matrix.ManualDumpActivity");
            ResourceConfig resourceConfig = new ResourceConfig.Builder()
                    .dynamicConfig(dynamicConfig)
                    .setAutoDumpHprofMode(mode)
//                .setDetectDebuger(true) //matrix test code
                    .setNotificationContentIntent(intent)
                    .build();
            builder.plugin(new ResourcePlugin(resourceConfig));
            ResourcePlugin.activityLeakFixer(this);

            //io
            IOCanaryPlugin ioCanaryPlugin = new IOCanaryPlugin(new IOConfig.Builder()
                    .dynamicConfig(dynamicConfig)
                    .build());
            builder.plugin(ioCanaryPlugin);


            // prevent api 19 UnsatisfiedLinkError
            //sqlite
//            SQLiteLintConfig sqlLiteConfig;
//            try {
//                sqlLiteConfig = new SQLiteLintConfig(SQLiteLint.SqlExecutionCallbackMode.CUSTOM_NOTIFY);
//            } catch (Throwable t) {
//                sqlLiteConfig = new SQLiteLintConfig(SQLiteLint.SqlExecutionCallbackMode.CUSTOM_NOTIFY);
//            }
//            builder.plugin(new SQLiteLintPlugin(sqlLiteConfig));

        }

        Matrix.init(builder.build());

        //start only startup tracer, close other tracer.
        tracePlugin.start();
    }
}
