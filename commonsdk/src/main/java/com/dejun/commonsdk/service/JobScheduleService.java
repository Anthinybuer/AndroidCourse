package com.dejun.commonsdk.service;

import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.dejun.commonsdk.util.TimeUtil;
import com.orhanobut.logger.Logger;

import java.util.concurrent.TimeUnit;

/**
 * Author:DoctorWei
 * Time:2018/12/21 14:33
 * Description:
 * email:1348172474@qq.com
 */

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class JobScheduleService extends JobService{
    @Override
    public boolean onStartJob(JobParameters params) {
        doJob(params);
        jobFinished(params,false);
        return false;//false 任务是非耗时操作
    }

    /**
     * 处理业务
     */
    private void doJob(JobParameters params) {
        Logger.d(TimeUtil.formatLongTime(System.currentTimeMillis()));
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Logger.d("onStopJob");
        return false;
    }
    public static void startService(Context context){
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP) {
            JobScheduler jobScheduler = (JobScheduler) context.getSystemService(JOB_SCHEDULER_SERVICE);
            JobInfo.Builder builder = new JobInfo.Builder(1, new ComponentName(context, JobScheduleService.class));
            builder.setBackoffCriteria(TimeUnit.MICROSECONDS.toMillis(6), JobInfo.BACKOFF_POLICY_LINEAR);
            builder.setPeriodic( 1000);
            jobScheduler.schedule(builder.build());
        }
//        Intent intent=new Intent(context,JobScheduleService.class);
//        context.startService(intent);
    }
}
