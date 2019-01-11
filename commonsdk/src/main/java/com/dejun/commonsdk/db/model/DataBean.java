package com.dejun.commonsdk.db.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Author:DoctorWei
 * Time:2018/12/12 19:21
 * Description:
 * email:1348172474@qq.com
 */

public  class DataBean {
    private Long id;
    private String type;
    private String text;
    private String username;
    private String uid;
    private String header;
    private int comment;

    private String top_commentsContent;
    private String top_commentsHeader;
    private String top_commentsName;
    private String passtime;
    private int soureid;
    private int up;
    private int down;
    private int forward;
    private String image;
    private String gif;
    private String thumbnail;
    private String video;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public int getComment() {
        return comment;
    }

    public void setComment(int comment) {
        this.comment = comment;
    }

    public String getTop_commentsContent() {
        return top_commentsContent;
    }

    public void setTop_commentsContent(String top_commentsContent) {
        this.top_commentsContent = top_commentsContent;
    }

    public String getTop_commentsHeader() {
        return top_commentsHeader;
    }

    public void setTop_commentsHeader(String top_commentsHeader) {
        this.top_commentsHeader = top_commentsHeader;
    }

    public String getTop_commentsName() {
        return top_commentsName;
    }

    public void setTop_commentsName(String top_commentsName) {
        this.top_commentsName = top_commentsName;
    }

    public String getPasstime() {
        return passtime;
    }

    public void setPasstime(String passtime) {
        this.passtime = passtime;
    }

    public int getSoureid() {
        return soureid;
    }

    public void setSoureid(int soureid) {
        this.soureid = soureid;
    }

    public int getUp() {
        return up;
    }

    public void setUp(int up) {
        this.up = up;
    }

    public int getDown() {
        return down;
    }

    public void setDown(int down) {
        this.down = down;
    }

    public int getForward() {
        return forward;
    }

    public void setForward(int forward) {
        this.forward = forward;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getGif() {
        return gif;
    }

    public void setGif(String gif) {
        this.gif = gif;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    @Override
    public String toString() {
        return "DataBean{" +
                "type='" + type + '\'' +
                ", text='" + text + '\'' +
                ", username='" + username + '\'' +
                ", uid='" + uid + '\'' +
                ", header='" + header + '\'' +
                ", comment=" + comment +
                ", top_commentsContent='" + top_commentsContent + '\'' +
                ", top_commentsHeader='" + top_commentsHeader + '\'' +
                ", top_commentsName='" + top_commentsName + '\'' +
                ", passtime='" + passtime + '\'' +
                ", soureid=" + soureid +
                ", up=" + up +
                ", down=" + down +
                ", forward=" + forward +
                ", image='" + image + '\'' +

                ", thumbnail='" + thumbnail + '\'' +

                '}';
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}