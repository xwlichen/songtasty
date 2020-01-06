package com.song.tasty.app.mvvm.ui;

import com.billy.cc.core.component.CC;
import com.song.tasty.app.R;
import com.song.tasty.common.core.base.BaseActivity;

import static com.song.tasty.common.app.AppRouters.LOGIN_COMP_MAIN;
import static com.song.tasty.common.app.AppRouters.START_ACTIVITY;

/**
 * @author lichen
 * @date ：2019-08-08 22:35
 * @email : 196003945@qq.com
 * @description :
 */
public class SplashActivity extends BaseActivity {
    @Override
    protected int getLayoutResId() {
        return R.layout.app_activity_splash;
    }

    @Override
    public void initView() {
//        DataRepository dataRepository = Injection.provideDataRepository();
//        if (dataRepository.isLogin()) {
        toMain();
//        } else {
//            CCResult result = null;

//
//            result.cc
//        }

    }

    private void toMain() {
//        SmartUtils.startActivity(MainActivity.class);
        CC cc = CC.obtainBuilder(LOGIN_COMP_MAIN)
                .setActionName(START_ACTIVITY)
                .build();
        cc.call();
        finish();
    }
}
