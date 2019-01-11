package com.dejun.commonsdk.util;

import android.view.View;

/**
 * Author:DoctorWei
 * Time:2018/11/2 18:10
 * Description:重写OnClickListener事件 控制按钮的点击
 * email:1348172474@qq.com
 */

public abstract class MyOnClickListener implements View.OnClickListener{
    private int id;
    private long lastTime;
    private static final int LIMIT_TIME=500;
    @Override
    public void onClick(View v) {

            long currentTime=System.currentTimeMillis();
            //不允许同一个按钮在0.5秒内重复点击
        if (id==v.getId()&&currentTime-lastTime<500) {
            return;
        }
        id=v.getId();
        lastTime=currentTime;
        myOnClick(v);
    }
    public abstract void myOnClick(View view);
}
