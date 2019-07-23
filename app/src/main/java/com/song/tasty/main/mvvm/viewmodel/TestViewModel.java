package com.song.tasty.main.mvvm.viewmodel;

import android.app.Application;

import com.song.tasty.common.app.app.Injection;
import com.song.tasty.common.app.model.DataRepository;
import com.song.tasty.common.core.base.BaseViewModel;
import com.song.tasty.common.core.binding.command.BindingAction;
import com.song.tasty.common.core.binding.command.BindingCommand;

import androidx.annotation.NonNull;

/**
 * @date : 2019-07-23 09:53
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description :
 */
public class TestViewModel extends BaseViewModel<DataRepository> {
    public TestViewModel(@NonNull Application application) {
        super(application, Injection.provideDataRepository());
    }


    public BindingCommand textClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            toastSource.setValue("test sdfds ");
        }
    });


}
