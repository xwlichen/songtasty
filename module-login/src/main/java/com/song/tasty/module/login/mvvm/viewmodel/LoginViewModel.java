package com.song.tasty.module.login.mvvm.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;

import com.song.tasty.common.app.app.Injection;
import com.song.tasty.common.app.model.DataRepository;
import com.song.tasty.common.core.base.BaseViewModel;
import com.song.tasty.common.core.binding.command.BindingAction;
import com.song.tasty.common.core.binding.command.BindingCommand;

/**
 * @date : 2019-07-23 09:53
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description :
 */
public class LoginViewModel extends BaseViewModel<DataRepository> {
    public LoginViewModel(@NonNull Application application) {
        super(application, Injection.provideDataRepository());
    }


    public BindingCommand textClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            toastSource.setValue("test sdfds ");
        }
    });


}
