package com.song.tasty.common.app.model.local;

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
        MMKV kv = MMKV.defaultMMKV();
        return kv.encode(KVConstants.KV_USERID, id);

    }


    @Override
    public String getUserId() {
        MMKV kv = MMKV.defaultMMKV();
        return kv.decodeString(KVConstants.KV_USERID);
    }

    @Override
    public boolean saveAccount(String account) {
        MMKV kv = MMKV.defaultMMKV();
        return kv.encode(KVConstants.KV_ACCOUNT, account);
    }

    @Override
    public String getAccount() {
        MMKV kv = MMKV.defaultMMKV();
        return kv.decodeString(KVConstants.KV_ACCOUNT);
    }

    @Override
    public boolean savePwd(String pwd) {
        MMKV kv = MMKV.defaultMMKV();
        return kv.encode(KVConstants.KV_PWD, pwd);
    }

    @Override
    public String getPwd() {
        MMKV kv = MMKV.defaultMMKV();
        return kv.decodeString(KVConstants.KV_PWD);
    }
}
