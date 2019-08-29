package com.song.tasty.module.home.datasource.remote.api.cache;

import com.song.tasty.module.home.entity.HomeResult;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.rx_cache2.LifeCache;
import io.rx_cache2.Reply;

/**
 * @date : 2019-08-07 14:21
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description :
 */
public interface HomeApiServiceCache {

    @LifeCache(duration = 2, timeUnit = TimeUnit.MINUTES)
    Observable<Reply<HomeResult>> index(Observable<HomeResult> result);

}
