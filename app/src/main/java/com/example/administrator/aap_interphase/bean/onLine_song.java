package com.example.administrator.aap_interphase.bean;

/**
 * Created by Administrator on 2017/4/9 0009.
 */

public class onLine_song {
    //在线音乐的bean类
    private  String  song;
    private  String singer;
    private  String imgUrl;
    private String  url;

    public onLine_song(String song, String singer, String imgUrl, String url){
        this.song = song;
        this.singer = singer;
        this.imgUrl = imgUrl;
        this.url = url;
    }

    public String getSong() {
        return song;
    }

    public void setSong(String song) {
        this.song = song;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
