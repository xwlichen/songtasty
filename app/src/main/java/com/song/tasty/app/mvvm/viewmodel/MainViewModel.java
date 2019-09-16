package com.song.tasty.app.mvvm.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;

import com.billy.cc.core.component.CC;
import com.billy.cc.core.component.CCResult;
import com.song.tasty.app.datasource.DataRepository;
import com.song.tasty.app.datasource.Injection;
import com.song.tasty.common.core.base.BaseViewModel;
import com.song.tasty.common.core.binding.command.BindingAction;
import com.song.tasty.common.core.binding.command.BindingCommand;

/**
 * @date : 2019-07-23 09:53
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description :
 */
public class MainViewModel extends BaseViewModel<DataRepository> {
    public MainViewModel(@NonNull Application application) {
        super(application, Injection.provideDataRepository());
    }


    public BindingCommand textClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            CCResult result = null;
            CC cc = null;
//                Utils.navigation(MainActivity.this, RouterHub.ZHIHU_HOMEACTIVITY);
//            cc = CC.obtainBuilder("module-main.test")
            cc = CC.obtainBuilder("module_login.login")
                    .setActionName("showActivityA")
                    .build();

//            cc = CC.obtainBuilder("module-mine.test")
//                    .setActionName("showActivityA")
//                    .build();



            result = cc.call();

        }
    });


}
