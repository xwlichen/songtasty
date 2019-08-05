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


    /**
     * 保存账号信息
     *
     * @param account
     * @return
     */
    boolean saveAccount(String account);


    /**
     * 获取账号信息
     *
     * @return
     */
    String getAccount();


    /**
     * 保存密码
     *
     * @param pwd
     * @return
     */
    boolean savePwd(String pwd);


    /**
     * 获取密码
     *
     * @return
     */
    String getPwd();
}
