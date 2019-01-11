package com.dejun.beauty;

import android.content.Context;

import com.dejun.beauty.net.BeautyApiUtil;
import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.io.InputStream;

/**
 * Author:DoctorWei
 * Time:2018/12/5 20:24
 * Description:
 * email:1348172474@qq.com
 */

public class BeautyClient {
    private static BeautyClient beautyClient;
    private static Context mContext;
    protected static BeautyApi beautyApi = null;

    private BeautyClient() {
    }

    /**
     * 懒汉多线程单例
     *
     * @return
     */
    public static BeautyClient getInstance(Context context) {
        mContext = context;
        if (beautyClient == null) {
            synchronized (BeautyClient.class) {
                if (beautyClient == null) {
                    beautyClient = new BeautyClient();
                }
            }
        }
        return beautyClient;
    }

    public static BeautyApi getBeautyApi() {

        return beautyApi;
    }
    public  BeautyApi setBeautyApi() {
        try {
            InputStream inputStream = mContext.getResources().getAssets().open("BeautyApi.xml");
            beautyApi = BeautyApiUtil.getInstance().getBeautyApi(inputStream, BeautyApi.class, "BeautyApi");
            Logger.d(beautyApi);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return beautyApi;
    }
}
