package com.song.tasty.common.app.music.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @date : 2020-01-14 10:39
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description :
 */
public class MusicBean implements Parcelable {


    private String id;
    private String name;
    private String author;
    private String cover;
    private String url;
    private long totalTime;


    public MusicBean() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
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

    protected MusicBean(Parcel in) {
        id = in.readString();
        name = in.readString();
        author = in.readString();
        cover = in.readString();
        url = in.readString();
        totalTime = in.readLong();
    }

    public static final Creator<MusicBean> CREATOR = new Creator<MusicBean>() {
        @Override
        public MusicBean createFromParcel(Parcel in) {
            return new MusicBean(in);
        }

        @Override
        public MusicBean[] newArray(int size) {
            return new MusicBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(author);
        dest.writeString(cover);
        dest.writeString(url);
        dest.writeLong(totalTime);
    }
}
