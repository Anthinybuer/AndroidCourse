package com.dejun.commonsdk.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.dejun.commonsdk.util.TimeUtil;
import com.orhanobut.logger.Logger;

/**
 * Author:DoctorWei
 * Time:2018/12/21 11:36
 * Description:
 * email:1348172474@qq.com
 */

public class AlarmReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        //处理定时任务
        Logger.d(System.currentTimeMillis());
        Logger.d(TimeUtil.formatLongTime(System.currentTimeMillis()));
    }

}
