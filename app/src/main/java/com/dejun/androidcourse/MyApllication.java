package com.dejun.androidcourse;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.dejun.androidcourse.service.ServiceUtil;
import com.dejun.beauty.BeautyApi;
import com.dejun.beauty.BeautyClient;
import com.dejun.beauty.net.BeautyApiUtil;
import com.dejun.commonsdk.cache.AppCache;
import com.dejun.commonsdk.db.DaoManager;
import com.dejun.commonsdk.receiver.NetStateReceiver;
import com.dejun.commonsdk.util.AlarmManagerUtil;
import com.dejun.commonsdk.util.LoggerUtil;
import com.dejun.function.FunctionClient;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

import java.io.IOException;
import java.io.InputStream;


/**
 * Author:DoctorWei
 * Time:2018/11/3 11:01
 * Description:
 * email:1348172474@qq.com
 */

public class MyApllication extends Application{
    /**
     * 设置全局smartRefresh控件
     */
    static {
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @NonNull
            @Override
            public RefreshHeader createRefreshHeader(@NonNull Context context, @NonNull RefreshLayout layout) {
                return new ClassicsHeader(context).setSpinnerStyle(SpinnerStyle.Translate);
            }
        });
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @NonNull
            @Override
            public RefreshFooter createRefreshFooter(@NonNull Context context, @NonNull RefreshLayout layout) {
                return new ClassicsFooter(context).setSpinnerStyle(SpinnerStyle.Translate);
            }
        });
    }
    @Override
    public void onCreate() {
        super.onCreate();
       // AlarmManagerUtil.getInstance().init(this).startAlarmReceiver();
        NetStateReceiver.registerNetworkStateReceiver(this);
        /**
         * 设置全局logger
         */
        Logger.addLogAdapter(new AndroidLogAdapter(LoggerUtil.getInstance().getFormatStrategy()){
            @Override
            public boolean isLoggable(int priority, String tag) {
                return super.isLoggable(priority, tag);
            }
        });
        AppCache.initAppCache(this);
        BeautyClient.getInstance(this).setBeautyApi();
        FunctionClient.getInstance(this).setFunctionApi();
        DaoManager.init(this);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        NetStateReceiver.unRegisterStateReceiver(this);
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}
