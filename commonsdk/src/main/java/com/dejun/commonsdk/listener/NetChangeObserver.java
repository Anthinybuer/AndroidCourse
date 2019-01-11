package com.dejun.commonsdk.listener;

import com.dejun.commonsdk.util.NetUtils;

/**
 * Author:DoctorWei
 * Time:2018/12/15 16:37
 * Description:网络改变观察者,观察网络改变后回调的方法
 * email:1348172474@qq.com
 */

public interface NetChangeObserver {
    /**
     * 网络连接回调 type为网络类型
     */
    void onNetConnected(NetUtils.NetType netType);
    /**
     * 没有网络
     */
    void onNetDisConnect();
}
