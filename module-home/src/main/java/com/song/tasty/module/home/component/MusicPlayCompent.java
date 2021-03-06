package com.song.tasty.module.home.component;

import com.billy.cc.core.component.CC;
import com.billy.cc.core.component.CCResult;
import com.billy.cc.core.component.IComponent;
import com.song.tasty.common.core.AppManager;
import com.song.tasty.module.home.mvvm.ui.MusicPlayActivity;

import static com.song.tasty.common.app.AppRouters.HOME_COMP_MUSICPLAY;
import static com.song.tasty.common.app.AppRouters.START_ACTIVITY;

/**
 * @date : 2020-01-13 15:52
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description :
 */
public class MusicPlayCompent implements IComponent {
    @Override
    public String getName() {
        return HOME_COMP_MUSICPLAY;
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
        AppManager.getAppManager().startActivity(MusicPlayActivity.class);
        CC.sendCCResult(cc.getCallId(), CCResult.success());
    }
}
