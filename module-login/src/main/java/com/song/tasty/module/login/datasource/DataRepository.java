package com.song.tasty.module.login.datasource;

import com.song.tasty.common.app.datasource.local.LocalDataSource;
import com.song.tasty.common.app.entity.LoginResult;
import com.song.tasty.common.core.base.BaseModel;
import com.song.tasty.module.login.datasource.remote.RemoteDataSource;

import androidx.annotation.Nullable;
import io.reactivex.Observable;

/**
 * @date : 2019-07-23 11:44
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description :
 */
public class DataRepository extends BaseModel implements LocalDataSource, RemoteDataSource {
    private volatile static DataRepository instance = null;

    private final LocalDataSource localDataSource;
    private final RemoteDataSource remoteDataSource;

    private DataRepository(@Nullable LocalDataSource localDataSource,
                           @Nullable RemoteDataSource remoteDataSource) {
        this.localDataSource = localDataSource;
        this.remoteDataSource = remoteDataSource;
    }

    public static DataRepository getInstance(@Nullable LocalDataSource localDataSource,
                                             @Nullable RemoteDataSource remoteDataSource) {
        if (instance == null) {
            synchronized (DataRepository.class) {
                if (instance == null) {
                    instance = new DataRepository(localDataSource, remoteDataSource);
                }
            }
        }
        return instance;
    }


    @Override
    public boolean saveUserId(String id) {
        return localDataSource.saveUserId(id);
    }

    @Override
    public String getUserId() {
        return localDataSource.getUserId();
    }

    @Override
    public boolean saveAccount(String account) {
        return localDataSource.saveAccount(account);
    }

    @Override
    public String getAccount() {
        return localDataSource.getAccount();
    }

    @Override
    public boolean savePwd(String pwd) {
        return localDataSource.savePwd(pwd);
    }

    @Override
    public String getPwd() {
        return localDataSource.getPwd();
    }

    @Override
    public Observable<LoginResult> login(String account, String password) {
        return remoteDataSource.login(account, password);
    }
}
