package com.dejun.androidcourse.service;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.widget.Toast;

import com.dejun.androidcourse.activity.MainActivity;

/**
 * Author:DoctorWei
 * Time:2018/12/13 10:14
 * Description:后台启动服务定时刷新
 * email:1348172474@qq.com
 */

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class AndroidCourseService extends JobService{
    private Handler handler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            Toast.makeText(AndroidCourseService.this, "AndroidCourseService", Toast.LENGTH_SHORT).show();
            JobParameters parameters= (JobParameters) msg.obj;
            jobFinished(parameters,true);
            Intent intent=new Intent(getApplicationContext(), MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            return true;
        }
    });

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public boolean onStartJob(JobParameters params) {
        Message m=Message.obtain();
        m.obj=params;
        handler.sendMessage(m);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        handler.removeCallbacksAndMessages(null);
        return false;
    }
}
