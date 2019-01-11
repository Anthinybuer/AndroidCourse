package com.dejun.androidcourse.service;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;

/**
 * Author:DoctorWei
 * Time:2018/12/13 10:28
 * Description:
 * email:1348172474@qq.com
 */

public class ServiceUtil {
    public static void startService(Context context){
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            JobScheduler jobScheduler= (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
            JobInfo jobInfo=new JobInfo.Builder(1,new ComponentName(context.getPackageName(),AndroidCourseService.class.getName()))
                    .setPeriodic(2000)
                    .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                    .build();
            jobScheduler.schedule(jobInfo);
        }
    }
}
