package com.song.tasty.module.login.mvvm.viewmodel;

import android.app.Application;
import android.text.TextUtils;
import android.view.View;

import com.song.tasty.common.core.base.BaseViewModel;
import com.song.tasty.common.core.binding.command.BindingAction;
import com.song.tasty.common.core.binding.command.BindingCommand;
import com.song.tasty.common.core.binding.command.BindingConsumer;
import com.song.tasty.common.core.enums.ViewStatus;
import com.song.tasty.common.core.livedata.SingleLiveData;
import com.song.tasty.module.login.datasource.DataRepository;
import com.song.tasty.module.login.datasource.Injection;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;

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
        account.set(model.getAccount());
        password.set(model.getPwd());
    }


    public BindingCommand finishOnClickCommond = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            getUiChange().getFinishEvent().call();
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


    public BindingCommand loginOnClickCommond = new BindingCommand(new BindingAction() {
        @Override
        public void call() {

            addSubcribe(model.login(account.get(), password.get())
                    .doOnSubscribe(disposable1 ->
                            getUiChange().getViewStatusSource().setValue(ViewStatus.LOADING))
                    .subscribe(result -> {
                        model.saveAccount(account.get());
                        model.savePwd(password.get());

                    }, throwable -> {
                        Toast

                    }, () -> {
                        getUiChange().getViewStatusSource().setValue(ViewStatus.LOADING);

                    }));

        }
    });


}
