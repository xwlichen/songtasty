package com.song.tasty.common.app.entity;

/**
 * @date : 2019-08-20 11:24
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description :
 */
public class MusicBean {

    private long id;
    private String name;
    private String coverSmall;
    private String coverBig;
    private String url;
    private String songer;
    private long totalTime;

    public MusicBean(String name, String coverSmall, String songer) {
        this.name = name;
        this.coverSmall = coverSmall;
        this.songer = songer;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCoverSmall() {
        return coverSmall;
    }

    public void setCoverSmall(String coverSmall) {
        this.coverSmall = coverSmall;
    }

    public String getCoverBig() {
        return coverBig;
    }

    public void setCoverBig(String coverBig) {
        this.coverBig = coverBig;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(long totalTime) {
        this.totalTime = totalTime;
    }

    public String getSonger() {
        return songer;
    }

    public void setSonger(String songer) {
        this.songer = songer;
    }
}
