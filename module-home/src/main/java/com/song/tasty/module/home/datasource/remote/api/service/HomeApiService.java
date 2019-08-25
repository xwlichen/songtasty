package com.song.tasty.module.home.datasource.remote.api.service;


import com.song.tasty.module.home.entity.HomeResult;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

import static com.song.tasty.module.home.Constants.HOME_DOMAIN_NAME;
import static me.jessyan.retrofiturlmanager.RetrofitUrlManager.DOMAIN_NAME_HEADER;

;

/**
 * @date : 2019-08-07 14:01
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description :
 */
public interface HomeApiService {


    /**
     * 登录
     */
    @Headers({DOMAIN_NAME_HEADER + HOME_DOMAIN_NAME})
    @FormUrlEncoded
    @POST("api/ulog/login")
    Observable<HomeResult> login(@Field("username") String account,
                                 @Field("userpass") String password);
}
