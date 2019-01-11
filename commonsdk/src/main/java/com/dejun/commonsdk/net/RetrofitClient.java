package com.dejun.commonsdk.net;

import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Author:DoctorWei
 * Time:2018/12/4 16:37
 * Description:
 * email:1348172474@qq.com
 */

public class RetrofitClient {
     private static RetrofitClient retrofitClient;

         private RetrofitClient() {
         }

         /**
          * 懒汉多线程单例
          *
          * @return
          */
         public static RetrofitClient getInstance() {
             if (retrofitClient == null) {
                 synchronized (RetrofitClient.class) {
                     if (retrofitClient == null) {
                         retrofitClient = new RetrofitClient();
                     }
                 }
             }
             return retrofitClient;
         }
         public Retrofit getRetrofit(String baseUrl){
             Retrofit retrofit=new Retrofit.Builder()
                     .client(setOkHttpClick())
                     .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                     .addConverterFactory(GsonConverterFactory.create())
                     .baseUrl(baseUrl)
                     .build();
             return retrofit;
         }
    public Retrofit getRetrofit(){
        Retrofit retrofit=new Retrofit.Builder()
                .client(setOkHttpClick())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }

    private OkHttpClient setOkHttpClick() {
             OkHttpClient okHttpClient=new OkHttpClient().newBuilder()
                     .connectTimeout(15, TimeUnit.SECONDS)
                     .readTimeout(15, TimeUnit.SECONDS)
                     .writeTimeout(15,TimeUnit.SECONDS)
                     .addInterceptor(getLoggingInterceptor())
                     .retryOnConnectionFailure(true)
                     .build();
             return okHttpClient;
    }
    private static Interceptor getLoggingInterceptor() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(true ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);
        return loggingInterceptor;
    }
}
