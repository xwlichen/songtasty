package com.song.tasty.module.login.mvvm.viewmodel;

import android.app.Application;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;

import com.song.tasty.common.app.app.Injection;
import com.song.tasty.common.app.model.DataRepository;
import com.song.tasty.common.core.base.BaseViewModel;
import com.song.tasty.common.core.binding.command.BindingAction;
import com.song.tasty.common.core.binding.command.BindingCommand;
import com.song.tasty.common.core.binding.command.BindingConsumer;
import com.song.tasty.common.core.livedata.SingleLiveData;

/**
 * @date : 2019-07-23 09:53
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description :
 */
public class LoginViewModel extends BaseViewModel<DataRepository> {

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


    public LoginViewModel(@NonNull Application application) {
        super(application, Injection.provideDataRepository());
    }


    public BindingCommand finishOnClickCommond = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            toastSource.setValue("关闭");
        }
    });


    public BindingCommand accountTextChangedCommond = new BindingCommand(new BindingConsumer<String>() {
        @Override
        public void call(String s) {
            if (TextUtils.isEmpty(s)) {
                ivClearVisibility.set(View.GONE);
            } else {
                ivClearVisibility.set(View.VISIBLE);

            }

        }
    });


    public BindingCommand clearAccountOnClickCommond = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            account.set("");
        }
    });


    public BindingCommand switchPwdOnClickCommond = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            pwdSwitchData.setValue(pwdSwitchData.getValue() == null || !pwdSwitchData.getValue());
        }
    });


}
