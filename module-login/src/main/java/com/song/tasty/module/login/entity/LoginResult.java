package com.song.tasty.module.login.entity;

/**
 * @date : 2019-08-07 14:19
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description :
 */
public class LoginResult {


    /**
     * error : 0
     * info : ok
     * msg : ok
     */

    private int error;
    private String info;
    private String msg;

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
