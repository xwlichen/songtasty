package com.song.tasty.app.mvvm.ui;

import com.song.tasty.app.R;
import com.song.tasty.common.core.base.BaseActivity;
import com.song.tasty.common.core.utils.SmartUtils;

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
    protected void initView() {
//        DataRepository dataRepository = Injection.provideDataRepository();
//        if (dataRepository.isLogin()) {
        toMain();
//        } else {
//            CCResult result = null;
//            CC cc = CC.obtainBuilder("module_login.login")
//                    .setActionName("showActivityA")
//                    .build();
//
//            result.cc
//        }

    }

    private void toMain() {
        SmartUtils.startActivity(MainActivity.class);
    }
}
