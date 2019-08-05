package debug;

import android.app.Application;

import com.billy.cc.core.component.CC;
import com.song.tasty.common.core.AppManager;

/**
 * @date : 2019-07-23 11:33
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description :
 */
public class AppApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        AppManager.getAppManager().init(this);
        CC.enableVerboseLog(true);
        CC.enableDebug(true);
        CC.enableRemoteCC(true);
    }
}
