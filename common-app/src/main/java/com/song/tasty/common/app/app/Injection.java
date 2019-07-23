package com.song.tasty.common.app.app;

import com.song.tasty.common.app.model.DataRepository;
import com.song.tasty.common.app.model.local.LocalDataSource;
import com.song.tasty.common.app.model.local.LocalDataSourceImp;
import com.song.tasty.common.app.model.remote.RemoteDataSource;
import com.song.tasty.common.app.model.remote.RemoteDataSourceImp;

/**
 * @date : 2019-07-23 16:22
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description : 提供DataRepository
 */
public class Injection {

    public static DataRepository provideDataRepository() {
        //网络API服务
//        DemoApiService apiService = RetrofitClient.getInstance().create(DemoApiService.class);
        //网络数据源
        RemoteDataSource remoteDataSource = RemoteDataSourceImp.getInstance();
        //本地数据源
        LocalDataSource localDataSource = LocalDataSourceImp.getInstance();
        //两条分支组成一个数据仓库
        return DataRepository.getInstance(localDataSource, remoteDataSource);
    }
}
