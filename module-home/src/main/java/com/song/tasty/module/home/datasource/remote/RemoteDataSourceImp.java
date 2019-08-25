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
    public Observable<HomeResult> login(String account, String password) {
        return RetrofitManager
                .init()
                .obtainRetrofitService(HomeApiService.class)
                .login(account, password);
    }


//    Observable.just(mRepositoryManager
//            .obtainRetrofitService(UserService.class)
//            .getUsers(lastIdQueried, USERS_PER_PAGE))
//            .flatMap(new Function<Observable<List<User>>, ObservableSource<List<User>>>() {
//        @Override
//        public ObservableSource<List<User>> apply(@NonNull Observable<List<User>> listObservable) throws Exception {
//            return mRepositoryManager.obtainCacheService(CommonCache.class)
//                    .getUsers(listObservable
//                            , new DynamicKey(lastIdQueried)
//                            , new EvictDynamicKey(update))
//                    .map(listReply -> listReply.getData());
//        }
//    })
}
