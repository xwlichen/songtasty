package com.song.tasty.module.home.datasource.remote;


import com.song.tasty.common.app.net.RetrofitManager;
import com.song.tasty.module.home.datasource.remote.api.service.HomeApiService;
import com.song.tasty.module.home.entity.HomeResult;

import io.reactivex.Observable;

/**
 * @date : 2019-07-23 15:09
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description :
 */
public class RemoteDataSourceImp implements RemoteDataSource {

    private volatile static RemoteDataSourceImp instance = null;

    public static RemoteDataSourceImp getInstance() {
        if (instance == null) {
            synchronized (RemoteDataSourceImp.class) {
                if (instance == null) {
                    instance = new RemoteDataSourceImp();
                }
            }
        }
        return instance;

    }

    public RemoteDataSourceImp() {
        //使用 RetrofitUrlManager 切换 BaseUrl

    }

    @Override
    public Observable<HomeResult> index() {
        return RetrofitManager
                .init()
                .obtainRetrofitService(HomeApiService.class)
                .index();
    }


}
