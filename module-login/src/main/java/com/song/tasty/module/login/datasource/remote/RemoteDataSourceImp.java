package com.song.tasty.module.login.datasource.remote;


import com.song.tasty.common.app.entity.LoginResult;
import com.song.tasty.common.app.net.RetrofitManager;
import com.song.tasty.module.login.datasource.remote.api.service.LoginApiService;

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


    @Override
    public Observable<LoginResult> login(String account, String password) {
        return RetrofitManager.getInstance()
                .obtainCacheService(LoginApiService.class)
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
