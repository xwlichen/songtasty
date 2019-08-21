package com.song.tasty.module.home.datasource.remote;

import com.song.tasty.common.app.entity.LoginResult;

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
