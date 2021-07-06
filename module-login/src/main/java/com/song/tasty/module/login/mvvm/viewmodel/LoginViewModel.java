package com.song.tasty.module.login.mvvm.viewmodel;

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
import com.song.tasty.module.login.datasource.DataRepository;
import com.song.tasty.module.login.datasource.Injection;

import androidx.annotation.NonNull;

/**
 * @date : 2019-07-23 09:53
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description :
 */
public class LoginViewModel extends BaseViewModel<DataRepository> {



    /**
     * 密码显示开关
     */
    public SingleLiveData<Boolean> pwdSwitchData = new SingleLiveData<Boolean>();


    public LoginViewModel(@NonNull Application application) {
        super(application, Injection.provideDataRepository());
        model.getLocalDataSource().getAccount();
        model.getLocalDataSource().getPwd();
    }


    public void login(String account,String password){
        addSubcribe(model
                .getRemoteDataSource()
                .login(account, password)
                .compose(RxUtils.schedulersTransformer())
                .doOnSubscribe(disposable -> uiChange.getViewStatusSource().setValue(ViewStatus.LOADING))
                .subscribe(result -> {
                    if (result.getError() == 0) {
                        model.getLocalDataSource().setIsLogin(true);
                        model.getLocalDataSource().saveAccount(account);
                        model.getLocalDataSource().savePwd(password);
                        uiChange.getFinishEvent().call();
                    } else {
                        uiChange.getToastSource().setValue(result.getMsg());
                    }


                }, new ResponseErrorHandler(), () -> {
                    uiChange.getViewStatusSource().setValue(ViewStatus.COMPLETE);
                }));

    }

}
