package com.dejun.function;

import android.content.Context;

import com.dejun.function.net.FunctionApiUtil;
import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.io.InputStream;

/**
 * Author:DoctorWei
 * Time:2018/12/5 20:24
 * Description:
 * email:1348172474@qq.com
 */

public class FunctionClient {
    private static FunctionClient beautyClient;
    private static Context mContext;
    protected static FunctionApi beautyApi = null;

    private FunctionClient() {
    }

    /**
     * 懒汉多线程单例
     *
     * @return
     */
    public static FunctionClient getInstance(Context context) {
        mContext = context;
        if (beautyClient == null) {
            synchronized (FunctionClient.class) {
                if (beautyClient == null) {
                    beautyClient = new FunctionClient();
                }
            }
        }
        return beautyClient;
    }

    public static FunctionApi getFunctionApi() {

        return beautyApi;
    }
    public  FunctionApi setFunctionApi() {
        try {
            InputStream inputStream = mContext.getResources().getAssets().open("FunctionApi.xml");
            beautyApi = FunctionApiUtil.getInstance().getFunctionApi(inputStream, FunctionApi.class, "FunctionApi");
            Logger.d(beautyApi);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return beautyApi;
    }
}
