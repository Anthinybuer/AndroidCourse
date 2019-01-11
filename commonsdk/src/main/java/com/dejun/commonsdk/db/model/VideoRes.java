package com.dejun.commonsdk.db.model;

import java.util.List;

/**
 * Author:DoctorWei
 * Time:2018/12/10 17:38
 * Description:
 * email:1348172474@qq.com
 */
public class VideoRes {
    private int code;
    private String msg;
    private List<DataBean> data;

    @Override
    public String toString() {
        return "VideoRes{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

}
