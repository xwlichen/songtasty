package com.song.tasty.module.home.datasource;

import com.song.tasty.common.app.datasource.local.LocalDataSource;
import com.song.tasty.common.app.datasource.local.LocalDataSourceImp;
import com.song.tasty.common.app.net.RetrofitManager;
import com.song.tasty.module.home.datasource.remote.RemoteDataSource;
import com.song.tasty.module.home.datasource.remote.RemoteDataSourceImp;

import me.jessyan.retrofiturlmanager.RetrofitUrlManager;

import static com.song.tasty.module.home.Constants.HOME_DOMAIN_NAME;
import static com.song.tasty.module.home.Constants.HOME_HOST_ONLINE_URL;


/**
 * @date : 2019-07-23 16:22
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description : 提供DataRepository
 */
public class Injection {

    public static DataRepository provideDataRepository() {
        RetrofitManager.init();
        RetrofitUrlManager.getInstance().putDomain(HOME_DOMAIN_NAME, HOME_HOST_ONLINE_URL);

        //网络数据源
        RemoteDataSource remoteDataSource = RemoteDataSourceImp.getInstance();
        //本地数据源
        LocalDataSource localDataSource = LocalDataSourceImp.getInstance();
        //两条分支组成一个数据仓库
        return DataRepository.getInstance(localDataSource, remoteDataSource);
    }
}
