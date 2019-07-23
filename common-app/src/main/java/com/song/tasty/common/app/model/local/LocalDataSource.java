package com.song.tasty.common.app.model.local;

/**
 * @date : 2019-07-23 11:21
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description :
 */
public interface LocalDataSource {

    /**
     * 存储userid
     *
     * @param id
     * @return boolean 保存是否成功
     */
    boolean saveUserId(String id);


    /**
     * 获取用户Id
     *
     * @return
     */
    String getUserId();
}
