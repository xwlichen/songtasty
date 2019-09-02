package com.song.tasty.module.home.entity;

import java.util.List;

/**
 * @author lichen
 * @date ：2019-09-02 21:25
 * @email : 196003945@qq.com
 * @description :
 */
public class HomeNavListBean {

    private List<HomeNavBean> list;

    public HomeNavListBean(List<HomeNavBean> list) {
        this.list = list;
    }

    public List<HomeNavBean> getList() {
        return list;
    }

    public void setList(List<HomeNavBean> list) {
        this.list = list;
    }
}
