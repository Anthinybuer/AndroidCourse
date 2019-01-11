package com.dejun.commonsdk.util;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

import com.dejun.commonsdk.receiver.AlarmReceiver;

import static android.content.Context.ALARM_SERVICE;

/**
 * Author:DoctorWei
 * Time:2018/12/21 11:30
 * Description:后台定时运行的闹钟
 * email:1348172474@qq.com
 */

public class AlarmManagerUtil {
    public static final int REFRSH_TIME = 10 * 1000;
    private static AlarmManagerUtil alarmManagerUtil;
    private Context context;

    private AlarmManagerUtil() {
    }

    /**
     * 懒汉多线程单例
     *
     * @return
     */
    public static AlarmManagerUtil getInstance() {
        if (alarmManagerUtil == null) {
            synchronized (AlarmManagerUtil.class) {
                if (alarmManagerUtil == null) {
                    alarmManagerUtil = new AlarmManagerUtil();
                }
            }
        }
        return alarmManagerUtil;
    }

    public AlarmManagerUtil init(Context context) {
        this.context = context;
        return this;
    }

    public  void startAlarmReceiver() {
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.setAction("repeating");
        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
        //开始时间
        long firstime = SystemClock.elapsedRealtime();
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        //刷新间隔 不精确
        alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, firstime, REFRSH_TIME, sender);
    }
    public  void stopAlarmReceiver(){
        Intent intent=new Intent(context,AlarmReceiver.class);
        intent.setAction("repeating");
        PendingIntent cancelIntent=PendingIntent.getBroadcast(context,0,intent,0);
        AlarmManager alarmManager= (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(cancelIntent);
    }
}
