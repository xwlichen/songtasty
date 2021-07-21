package com.song.tasty.module.video.mvvm.viewmodel;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;

import com.song.tasty.common.core.base.BaseViewModel;
import com.song.tasty.common.core.binding.command.BindingCommand;
import com.song.tasty.common.core.livedata.SingleLiveData;
import com.song.tasty.module.video.datasource.DataRepository;
import com.song.tasty.module.video.datasource.Injection;

/**
 * @date : 2019-07-23 09:53
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description :
 */
public class VideoViewModel extends BaseViewModel<DataRepository> {



    /**
     * 密码显示开关
     */
    public SingleLiveData<Boolean> pwdSwitchData = new SingleLiveData<Boolean>();


    public VideoViewModel(@NonNull Application application) {
        super(application, Injection.provideDataRepository());
        model.getLocalDataSource().getAccount();
        model.getLocalDataSource().getPwd();
    }


}
