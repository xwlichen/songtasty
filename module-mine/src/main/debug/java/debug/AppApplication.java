package debug;

import android.app.Application;

import com.billy.cc.core.component.CC;

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


        CC.enableVerboseLog(true);
        CC.enableDebug(true);
        CC.enableRemoteCC(true);
    }
}
