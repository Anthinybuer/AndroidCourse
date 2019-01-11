package com.dejun.commonsdk.db.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Author:DoctorWei
 * Time:2018/12/12 20:20
 * Description:
 * email:1348172474@qq.com
 */
public  class ResultsBean {
    /**
     * _id : 5bfe1a5b9d2122309624cbb7
     * createdAt : 2018-11-28T04:32:27.338Z
     * desc : 2018-11-28
     * publishedAt : 2018-11-28T00:00:00.0Z
     * source : web
     * type : 福利
     * url : https://ws1.sinaimg.cn/large/0065oQSqgy1fxno2dvxusj30sf10nqcm.jpg
     * used : true
     * who : lijinshanmx
     */
    private String _id;
    private String createdAt;
    private String desc;
    private String publishedAt;
    private String source;
    private String type;
    private String url;
    private boolean used;
    private String who;







    @Override
    public String toString() {
        return "ResultsBean{" +
                "_id='" + _id + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", desc='" + desc + '\'' +
                ", publishedAt='" + publishedAt + '\'' +
                ", source='" + source + '\'' +
                ", type='" + type + '\'' +
                ", url='" + url + '\'' +
                ", used=" + used +
                ", who='" + who + '\'' +
                '}';
    }


    public String get_id() {
        return this._id;
    }


    public void set_id(String _id) {
        this._id = _id;
    }


    public String getCreatedAt() {
        return this.createdAt;
    }


    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }


    public String getDesc() {
        return this.desc;
    }


    public void setDesc(String desc) {
        this.desc = desc;
    }


    public String getPublishedAt() {
        return this.publishedAt;
    }


    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }


    public String getSource() {
        return this.source;
    }


    public void setSource(String source) {
        this.source = source;
    }


    public String getType() {
        return this.type;
    }


    public void setType(String type) {
        this.type = type;
    }


    public String getUrl() {
        return this.url;
    }


    public void setUrl(String url) {
        this.url = url;
    }


    public boolean getUsed() {
        return this.used;
    }


    public void setUsed(boolean used) {
        this.used = used;
    }


    public String getWho() {
        return this.who;
    }


    public void setWho(String who) {
        this.who = who;
    }
    
}