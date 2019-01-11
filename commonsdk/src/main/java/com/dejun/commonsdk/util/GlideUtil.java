package com.dejun.commonsdk.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.orhanobut.logger.Logger;

import java.io.File;

import retrofit2.http.Url;

/**
 * Author:DoctorWei
 * Time:2018/12/6 14:03
 * Description:
 * email:1348172474@qq.com
 */

public class GlideUtil {
    public static final int INVALID_VALUE=-1;
    public static void loadUrlImage(Context context, String url, ImageView view,int loadingRes, int errorRes) {
        Glide.with(context).load(url).apply(createOptions(loadingRes,errorRes,false,false)).into(view);
    }
    public static void loadDefaultUrl(Context context, String url, ImageView view){
        Glide.with(context).load(url).apply(createOptions(INVALID_VALUE,INVALID_VALUE,false,false)).into(view);
    }public static void loadDefaultUrlWithScalType(Context context, String url, ImageView view){
        Glide.with(context).load(url).apply(createOptions(INVALID_VALUE,INVALID_VALUE,false,false)).into(view);
    }
    public static void loadDefaultDrawable(Context context,int drawableRes, ImageView view){
        Glide.with(context).load(drawableRes).apply(createOptions(INVALID_VALUE,INVALID_VALUE,false,false)).into(view);
    }
    public static void loadDefaultDrawable(Context context, Drawable drawable, ImageView view){
        Glide.with(context).load(drawable).apply(createOptions(INVALID_VALUE,INVALID_VALUE,false,false)).into(view);
    }
    public static void loadDefaultUrl(Context context, File file, ImageView view) {
        if (file.exists()) {
            Glide.with(context).load(file).apply(createOptions(INVALID_VALUE, INVALID_VALUE, false, false)).into(view);
        }else{
            Logger.d("文件不存在");
        }
    }
    public static RequestOptions createOptions(int loadingRes, int errorRes,boolean centerCrop,boolean isMemoryCache){
        RequestOptions requestOptions=new RequestOptions();
        if (loadingRes!=INVALID_VALUE) {
            requestOptions.placeholder(loadingRes);
        }
        if (loadingRes!=INVALID_VALUE) {
            requestOptions.error(errorRes);
        }
        if (centerCrop) {
            requestOptions.centerCrop();
        }
        if (isMemoryCache) {
            requestOptions.skipMemoryCache(false);//是否跳过缓存
        }else{
            requestOptions.skipMemoryCache(true);
        }
        requestOptions.centerCrop();
        return requestOptions;
    }


}
