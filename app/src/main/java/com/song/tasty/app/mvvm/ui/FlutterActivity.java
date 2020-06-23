package com.song.tasty.app.mvvm.ui;

import android.os.CountDownTimer;
import android.view.View;
import android.widget.LinearLayout;

import com.billy.cc.core.component.CC;
import com.song.tasty.app.R;
import com.song.tasty.common.core.base.BaseActivity;
import com.song.tasty.common.core.utils.SmartUtils;

import io.flutter.embedding.android.FlutterView;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.embedding.engine.dart.DartExecutor;

import static com.song.tasty.common.app.AppRouters.HOME_COMP_MUSICPLAY;
import static com.song.tasty.common.app.AppRouters.START_ACTIVITY;

/**
 * @author lichen
 * @date ：2019-08-08 22:35
 * @email : 196003945@qq.com
 * @description :
 */
public class FlutterActivity extends BaseActivity {

    private CountDownTimer countDownTimer;

    @Override
    protected int getLayoutResId() {
        return R.layout.app_activity_flutter;
    }

    @Override
    public void initView() {


        FlutterView view = new FlutterView(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT
        );
        LinearLayout linearLayout=findViewById(R.id.bgContainer);
        linearLayout.addView(view, layoutParams);

        //创建引擎
        FlutterEngine  flutterEngine = new FlutterEngine(this);
        String str = "route1?{\"message\":\"" + message + "\"}";
        flutterEngine.getNavigationChannel().setInitialRoute(str);
        flutterEngine.getDartExecutor().executeDartEntrypoint(
                DartExecutor.DartEntrypoint.createDefault()
        );
//渲染Flutter页面
        view.attachToFlutterEngine(flutterEngine);

//

    }

    private void toMain() {
        SmartUtils.startActivity(MainActivity.class);
//        CC cc = CC.obtainBuilder(LOGIN_COMP_MAIN)
//                .setActionName(START_ACTIVITY)
//                .build();
//        cc.call();
        finish();
    }
}
