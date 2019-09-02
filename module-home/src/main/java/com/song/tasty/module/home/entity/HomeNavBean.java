package com.song.tasty.module.home.entity;

/**
 * @author lichen
 * @date ：2019-09-01 14:17
 * @email : 196003945@qq.com
 * @description :
 */
public class HomeNavBean {
    private String text;
    private int imgRes;
    private String imgUrl;

    public HomeNavBean() {
    }

    public HomeNavBean(String text, int imgRes) {
        this.text = text;
        this.imgRes = imgRes;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getImgRes() {
        return imgRes;
    }

    public void setImgRes(int imgRes) {
        this.imgRes = imgRes;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
