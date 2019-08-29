package com.song.tasty.module.home.datasource.remote;


import com.song.tasty.module.home.entity.HomeResult;

import io.reactivex.Observable;

/**
 * @date : 2019-08-07 14:40
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description :
 */
public interface RemoteDataSource {


    /**
     * 首页接口
     * @return
     */
    Observable<HomeResult> index();
}
