package com.song.tasty.module.login.datasource.remote;

import com.song.tasty.module.login.entity.LoginResult;

import io.reactivex.Observable;

/**
 * @date : 2019-08-07 14:40
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description :
 */
public interface RemoteDataSource {


    /**
     * 登录
     *
     * @param account
     * @param password
     * @return
     */
    Observable<LoginResult> login(String account, String password);
}
