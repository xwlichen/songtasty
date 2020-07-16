package com.song.tasty.app.component;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.billy.cc.core.component.CC;
import com.billy.cc.core.component.CCResult;
import com.billy.cc.core.component.IComponent;
import com.song.tasty.common.app.activitys.flutter.FlutterLoadingActivity;

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
        Intent intent = FlutterLoadingActivity.withNewEngine().initialRoute("route1").build(context);
        Log.e("xwlc","FlutterCompent LoadingActivity");
//        intent.putExtra("background_mode","transparent");
        context.startActivity(intent);
        CC.sendCCResult(cc.getCallId(), CCResult.success());
    }
}
