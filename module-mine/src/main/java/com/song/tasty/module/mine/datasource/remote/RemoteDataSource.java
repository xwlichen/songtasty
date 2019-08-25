package com.song.tasty.module.mine.datasource.remote;

import com.song.tasty.module.mine.entity.MIneResult;

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
    Observable<MIneResult> login(String account, String password);
}
