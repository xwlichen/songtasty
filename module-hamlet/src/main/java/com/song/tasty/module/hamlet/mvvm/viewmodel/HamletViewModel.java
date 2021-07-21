package com.song.tasty.module.hamlet.mvvm.viewmodel;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;

import com.song.tasty.common.core.base.BaseViewModel;
import com.song.tasty.common.core.binding.command.BindingCommand;
import com.song.tasty.common.core.livedata.SingleLiveData;
import com.song.tasty.module.hamlet.datasource.DataRepository;
import com.song.tasty.module.hamlet.datasource.Injection;

/**
 * @date : 2019-07-23 09:53
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description :
 */
public class HamletViewModel extends BaseViewModel<DataRepository> {




    /**
     * 账号的清除按钮是否显示
     */



    /**
     * 密码显示开关
     */
    public SingleLiveData<Boolean> pwdSwitchData = new SingleLiveData<Boolean>();


    public HamletViewModel(@NonNull Application application) {
        super(application, Injection.provideDataRepository());

    }


    public BindingCommand finishOnClickCommond = new BindingCommand(() -> uiChange.getFinishEvent().call());


}
