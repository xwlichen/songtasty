package com.song.tasty.common.app.flutter.utils;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.song.tasty.common.app.flutter.ui.FlutterCommonActivity;
import com.song.tasty.common.app.flutter.ui.FlutterCommonFragment;

import io.flutter.embedding.android.FlutterFragment;
import io.flutter.embedding.android.FlutterView;
import io.flutter.embedding.android.RenderMode;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.embedding.engine.FlutterEngineCache;
import io.flutter.embedding.engine.dart.DartExecutor;

public class FlutterPageUtils {
    static  FlutterPageUtils instance;

    public static  FlutterPageUtils getInstance(){
        synchronized (FlutterPageUtils.class){
            if (instance==null){
                instance=new FlutterPageUtils();
            }
            return instance;
        }
    }


    public void create(Context context){

    }

    /**
     *
     * @param routeName {@link com.song.tasty.common.app.flutter.ui.FlutterCommonActivity}
     */
    public FlutterFragment createFragment(Context context,String routeName){
        if (TextUtils.isEmpty(routeName)){
            throw new UnsupportedOperationException("engine_id can't be null...");
        }
////        cacheEngine(context,routeName);
//        FlutterEngineCache flutterEngineCache= FlutterEngineCache.getInstance();
////        if (!flutterEngineCache.contains(routeName)){
//
//            // Instantiate a FlutterEngine.
//            FlutterEngine flutterEngine = new FlutterEngine(context);
//            // Configure an initial route.
//            flutterEngine.getNavigationChannel().setInitialRoute(routeName);
//            // Start executing Dart code to pre-warm the FlutterEngine.
//            flutterEngine.getDartExecutor().executeDartEntrypoint(
//                    DartExecutor.DartEntrypoint.createDefault()
//            );
//            // Cache the FlutterEngine to be used by FlutterActivity or FlutterFragment.
//        flutterEngineCache
//                    .put(routeName, flutterEngine);
//
////        }
////
//
//
//
//        FlutterFragment fragment=FlutterFragment.withCachedEngine(routeName).build();
        FlutterFragment fragment= FlutterCommonFragment
                .withNewEngine()
                .initialRoute(routeName)
                .renderMode(RenderMode.texture)
                .build();

        return fragment;
//        return FlutterCustomFragment.withCachedEngine(routeName).build();

    }


    /**
     *
     * @paramcontext
     * @param routeName {@link com.song.tasty.common.app.flutter.FRouterConstants}
     */
    public Intent createIntent(Context context,String routeName){

        if (TextUtils.isEmpty(routeName)){
            throw new UnsupportedOperationException("engine_id can't be null...");
        }
       cacheEngine(context,routeName);



        return  FlutterCommonActivity
                .withCachedEngine(routeName)
//                .backgroundMode(FlutterActivityLaunchConfigs.BackgroundMode.transparent)
                .build(context);
    }

    public void cacheEngine(Context context,String routeName){
        FlutterEngineCache flutterEngineCache= FlutterEngineCache.getInstance();
        if (!flutterEngineCache.contains(routeName)){

            // Instantiate a FlutterEngine.
            FlutterEngine flutterEngine = new FlutterEngine(context);
            // Configure an initial route.
            flutterEngine.getNavigationChannel().setInitialRoute(routeName);
            // Start executing Dart code to pre-warm the FlutterEngine.
            flutterEngine.getDartExecutor().executeDartEntrypoint(
                    DartExecutor.DartEntrypoint.createDefault()
            );
            // Cache the FlutterEngine to be used by FlutterActivity or FlutterFragment.
            FlutterEngineCache
                    .getInstance()
                    .put(routeName, flutterEngine);

        }
    }

}
