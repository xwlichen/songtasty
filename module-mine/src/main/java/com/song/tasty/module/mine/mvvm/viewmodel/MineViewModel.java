package com.song.tasty.module.mine.mvvm.viewmodel;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;

import com.song.tasty.common.core.base.BaseViewModel;
import com.song.tasty.common.core.binding.command.BindingCommand;
import com.song.tasty.common.core.livedata.SingleLiveData;
import com.song.tasty.module.mine.datasource.DataRepository;
import com.song.tasty.module.mine.datasource.Injection;

/**
 * @date : 2019-07-23 09:53
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description :
 */
public class MineViewModel extends BaseViewModel<DataRepository> {

    public ObservableField<String> account = new ObservableField<>("");
    public ObservableField<String> password = new ObservableField<>("");


    /**
     * 账号的清除按钮是否显示
     */
    public ObservableInt ivClearVisibility = new ObservableInt(View.GONE);


    /**
     * 密码显示开关
     */
    public SingleLiveData<Boolean> pwdSwitchData = new SingleLiveData<Boolean>();


    public MineViewModel(@NonNull Application application) {
        super(application, Injection.provideDataRepository());
        account.set(model.getLocalDataSource().getAccount());
        password.set(model.getLocalDataSource().getPwd());
    }


    public BindingCommand finishOnClickCommond = new BindingCommand(() -> uiChange.getFinishEvent().call());


}
