package com.song.tasty.module.home.entity;

import java.util.List;

/**
 * @author lichen
 * @date ：2019-09-01 00:11
 * @email : 196003945@qq.com
 * @description :
 */
public class BannerListBean {

    private List<BannerBean> list;

    public BannerListBean(List<BannerBean> list) {
        this.list = list;
    }

    public List<BannerBean> getList() {
        return list;
    }

    public void setList(List<BannerBean> list) {
        this.list = list;
    }
}
