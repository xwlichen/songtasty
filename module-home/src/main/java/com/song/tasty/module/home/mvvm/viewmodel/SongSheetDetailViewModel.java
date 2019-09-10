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

/**
 * @date : 2019-07-23 09:53
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description :
 */
public class SongSheetDetailViewModel extends BaseViewModel<DataRepository> {

    /**
     * 首页接口返回成功数据
     */
    public SingleLiveData<HomeResult> successResult = new SingleLiveData<HomeResult>();

//    public SingleLiveData<>


    public SongSheetDetailViewModel(@NonNull Application application) {
        super(application, Injection.provideDataRepository());
    }


    public void getData() {

        addSubcribe(model
                .getRemoteDataSource()
                .index()
                .compose(RxUtils.schedulersTransformer())
                .doOnSubscribe(disposable -> uiChange.getViewStatusSource().setValue(ViewStatus.LOADING))
                .subscribe(result -> {
                    successResult.setValue(result);

                }, new ResponseErrorHandler(), () -> {
                    uiChange.getViewStatusSource().setValue(ViewStatus.COMPLETE);
                }));

    }


}
