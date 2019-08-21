package com.song.tasty.module.home.mvvm.viewmodel;

import android.app.Application;
import android.text.TextUtils;
import android.view.View;

import com.song.tasty.common.app.net.ResponseErrorHandler;
import com.song.tasty.common.app.utils.RxUtils;
import com.song.tasty.common.core.base.BaseViewModel;
import com.song.tasty.common.core.binding.command.BindingAction;
import com.song.tasty.common.core.binding.command.BindingCommand;
import com.song.tasty.common.core.binding.command.BindingConsumer;
import com.song.tasty.common.core.enums.ViewStatus;
import com.song.tasty.common.core.livedata.SingleLiveData;
import com.song.tasty.module.home.datasource.DataRepository;
import com.song.tasty.module.home.datasource.Injection;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;

/**
 * @date : 2019-07-23 09:53
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description :
 */
public class HomeViewModel extends BaseViewModel<DataRepository> {

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


    public HomeViewModel(@NonNull Application application) {
        super(application, Injection.provideDataRepository());
        account.set(model.getLocalDataSource().getAccount());
        password.set(model.getLocalDataSource().getPwd());
    }


    public BindingCommand finishOnClickCommond = new BindingCommand(() -> uiChange.getFinishEvent().call());


    public BindingCommand accountTextChangedCommond = new BindingCommand<>((BindingConsumer<String>) s -> {
        if (TextUtils.isEmpty(s)) {
            ivClearVisibility.set(View.GONE);
        } else {
            ivClearVisibility.set(View.VISIBLE);

        }

    });


    public BindingCommand clearAccountOnClickCommond = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            account.set("");
        }
    });


    public BindingCommand switchPwdOnClickCommond = new BindingCommand(() -> pwdSwitchData.setValue(pwdSwitchData.getValue() == null || !pwdSwitchData.getValue()));


    public BindingCommand loginOnClickCommond = new BindingCommand(() -> addSubcribe(model
            .getRemoteDataSource()
            .login(account.get(), password.get())
            .compose(RxUtils.schedulersTransformer())
            .doOnSubscribe(disposable -> uiChange.getViewStatusSource().setValue(ViewStatus.LOADING))
            .subscribe(result -> {
                if (result.getError() == 0) {
                    model.getLocalDataSource().setIsLogin(true);
                    model.getLocalDataSource().saveAccount(account.get());
                    model.getLocalDataSource().savePwd(password.get());
                    uiChange.getFinishEvent().call();
                } else {
                    uiChange.getToastSource().setValue(result.getMsg());
                }


            }, new ResponseErrorHandler(), () -> {
                uiChange.getViewStatusSource().setValue(ViewStatus.COMPLETE);
            })));


}
