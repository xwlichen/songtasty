package com.song.tasty.app.component;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.billy.cc.core.component.CC;
import com.billy.cc.core.component.CCResult;
import com.billy.cc.core.component.IComponent;
import com.song.tasty.common.app.flutter.FRouterConstants;
import com.song.tasty.common.app.flutter.ui.FlutterCommonActivity;
import com.song.tasty.common.app.flutter.utils.FlutterPageUtils;

import static com.song.tasty.common.app.AppRouters.APP_COMP_FLUTTER;
import static com.song.tasty.common.app.AppRouters.START_ACTIVITY;

/**
 * @date : 2020-01-13 15:52
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description :
 */
public class FlutterCompent implements IComponent {
    @Override
    public String getName() {
        return APP_COMP_FLUTTER;
    }

    @Override
    public boolean onCall(CC cc) {
        String action = cc.getActionName();
        switch (action) {
            case START_ACTIVITY:
                startActivity(cc);
                break;
            default:
                break;
        }
        return false;
    }

    /**
     * 打开 {@link } 页面
     *
     * @param cc
     */
    private void startActivity(CC cc) {
//        AppManager.getAppManager().startActivity(FlutterActivity.class);
//        FlutterActivity.createDefaultIntent(AppManager.getAppManager().getApplication());
        Log.e("xwlc","FlutterCompent startActivity");
        Context context=cc.getParamItem("pContext");
//        Intent intent = FlutterActivity.withNewEngine().initialRoute("route1").build(context);
//        Intent intent = FlutterCustomActivity
//                .withCachedEngine("my_engine_id")
////                .backgroundMode(FlutterActivityLaunchConfigs.BackgroundMode.transparent)
//                .build(context);
//        Log.e("xwlc","FlutterCompent LoadingActivity");
//        intent.putExtra("background_mode","transparent");



//        FlutterEngineCache flutterEngineCache= FlutterEngineCache.getInstance();
//        if (!flutterEngineCache.contains(FlutterRouterConstants.ROUTER_SETTING)){
//
//            // Instantiate a FlutterEngine.
//            FlutterEngine flutterEngine = new FlutterEngine(context);
//            // Configure an initial route.
//            flutterEngine.getNavigationChannel().setInitialRoute(FlutterRouterConstants.ROUTER_SETTING);
//            // Start executing Dart code to pre-warm the FlutterEngine.
//            flutterEngine.getDartExecutor().executeDartEntrypoint(
//                    DartExecutor.DartEntrypoint.createDefault()
//            );
//            // Cache the FlutterEngine to be used by FlutterActivity or FlutterFragment.
//            FlutterEngineCache
//                    .getInstance()
//                    .put(FlutterRouterConstants.ROUTER_SETTING, flutterEngine);
//
//        }

//        Intent intent= FlutterCommonActivity
////                .withCachedEngine(FRouterConstants.ROUTER_MINE_SETTING)
//                .withNewEngine()
//                .initialRoute(FRouterConstants.ROUTER_MINE_SETTING)
////                .backgroundMode(FlutterActivityLaunchConfigs.BackgroundMode.transparent)
//                .build(context);


//        context.startActivity(intent);


        context.startActivity(FlutterPageUtils.getInstance().createIntent(context, FRouterConstants.ROUTER_MINE_SETTING));
        context=null;
        CC.sendCCResult(cc.getCallId(), CCResult.success());
    }
}
