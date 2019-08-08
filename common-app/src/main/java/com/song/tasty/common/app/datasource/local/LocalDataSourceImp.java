package com.song.tasty.common.app.datasource.local;

import com.song.tasty.common.app.KVConstants;
import com.tencent.mmkv.MMKV;

/**
 * @date : 2019-07-23 14:36
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description :
 */
public class LocalDataSourceImp implements LocalDataSource {
    private volatile static LocalDataSourceImp instance = null;

    public static LocalDataSourceImp getInstance() {
        if (instance == null) {
            synchronized (LocalDataSourceImp.class) {
                if (instance == null) {
                    instance = new LocalDataSourceImp();
                }
            }
        }
        return instance;

    }


    @Override
    public boolean saveUserId(String id) {
        return MMKV.defaultMMKV().encode(KVConstants.KV_USERID, id);

    }


    @Override
    public String getUserId() {
        return MMKV.defaultMMKV().decodeString(KVConstants.KV_USERID);
    }

    @Override
    public boolean saveAccount(String account) {
        return MMKV.defaultMMKV().encode(KVConstants.KV_ACCOUNT, account);
    }

    @Override
    public String getAccount() {
        return MMKV.defaultMMKV().decodeString(KVConstants.KV_ACCOUNT);
    }

    @Override
    public boolean savePwd(String pwd) {
        return MMKV.defaultMMKV().encode(KVConstants.KV_PWD, pwd);
    }

    @Override
    public String getPwd() {
        return MMKV.defaultMMKV().decodeString(KVConstants.KV_PWD);
    }

    @Override
    public boolean setIsLogin(boolean flag) {
        return MMKV.defaultMMKV().encode(KVConstants.KV_ISLOGIN, flag);
    }

    @Override
    public boolean isLogin() {
        return MMKV.defaultMMKV().decodeBool(KVConstants.KV_ISLOGIN);
    }
}
