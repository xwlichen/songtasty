package com.song.tasty.module.home.component;

import com.billy.cc.core.component.CC;
import com.billy.cc.core.component.CCResult;
import com.billy.cc.core.component.IComponent;
import com.song.tasty.module.home.mvvm.ui.HomeFragment;

import static com.song.tasty.common.app.AppRouters.HOME_COMP_MAIN;
import static com.song.tasty.common.app.AppRouters.HOME_COMP_MAIN_ACTION_GET_HOMEFRAGMENT;

/**
 * @author lichen
 * @date ：2018/9/18 下午2:29
 * @email : 196003945@qq.com
 * @description :
 */
public class HomeComponent implements IComponent {

    @Override

    public String getName() {
        return HOME_COMP_MAIN;
    }

    @Override
    public boolean onCall(CC cc) {
        String actionName = cc.getActionName();
        switch (actionName) {
            case HOME_COMP_MAIN_ACTION_GET_HOMEFRAGMENT:
                getHomeFragment(cc);
                break;
            default:
                //这个逻辑分支上没有调用CC.sendCCResult(...),是一种错误的示例
                //并且方法的返回值为false，代表不会异步调用CC.sendCCResult(...)
                //在LocalCCInterceptor中将会返回错误码为-10的CCResult
                break;
        }
        return false;
    }


    /**
     * 获取homefragment
     *
     * @param cc
     */
    private void getHomeFragment(CC cc) {
        CC.sendCCResult(cc.getCallId(), CCResult.successWithNoKey(HomeFragment.getInstance()));
    }
}
