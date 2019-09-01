package com.song.tasty.module.home.entity;

import java.util.List;

/**
 * @author lichen
 * @date ：2019-09-01 12:54
 * @email : 196003945@qq.com
 * @description :
 */
public class SongSheetListBean {

    private List<SongSheetBean> list;


    public SongSheetListBean(List<SongSheetBean> list) {
        this.list = list;
    }


    public List<SongSheetBean> getList() {
        return list;
    }

    public void setList(List<SongSheetBean> list) {
        this.list = list;
    }
}
