package com.song.tasty.module.home.entity;

/**
 * @author lichen
 * @date ：2019-09-01 14:17
 * @email : 196003945@qq.com
 * @description :
 */
public class HomeNavBean {
    private String text;

    public HomeNavBean(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
