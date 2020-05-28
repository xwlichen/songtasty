package com.song.tasty.module.home.mvvm.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;

import com.song.tasty.common.app.net.ResponseErrorHandler;
import com.song.tasty.common.app.utils.RxUtils;
import com.song.tasty.common.core.base.BaseViewModel;
import com.song.tasty.common.core.enums.ViewStatus;
import com.song.tasty.common.core.livedata.SingleLiveData;
import com.song.tasty.module.home.datasource.DataRepository;
import com.song.tasty.module.home.datasource.Injection;
import com.song.tasty.module.home.entity.HomeResult;

import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * @date : 2019-07-23 09:53
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description :
 */
public class HomeViewModel extends BaseViewModel<DataRepository> {

    /**
     * 首页接口返回成功数据
     */
    public SingleLiveData<HomeResult> successResult = new SingleLiveData<HomeResult>();

//    public SingleLiveData<>


    public HomeViewModel(@NonNull Application application) {
        super(application, Injection.provideDataRepository());
    }


    public void getData() {

        addSubcribe(model
                .getRemoteDataSource()
                .discover()
                .compose(RxUtils.schedulersTransformer())
                .doOnSubscribe(disposable -> uiChange.getViewStatusSource().setValue(ViewStatus.LOADING))
                .subscribe(new Consumer<HomeResult>() {
                    @Override
                    public void accept(HomeResult result) throws Exception {
                        successResult.setValue(result);
                    }
                }, new ResponseErrorHandler(), new Action() {
                    @Override
                    public void run() throws Exception {
                        uiChange.getViewStatusSource().setValue(ViewStatus.COMPLETE);
                    }
                }));

    }


}
