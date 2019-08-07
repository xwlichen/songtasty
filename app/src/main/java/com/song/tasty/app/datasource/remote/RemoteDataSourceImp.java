package com.song.tasty.app.datasource.remote;


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

}
