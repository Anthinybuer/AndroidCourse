package com.dejun.commonsdk.cache;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import com.orhanobut.logger.Logger;

import java.io.File;
import java.io.IOException;

/**
 * Author:DoctorWei
 * Time:2018/12/10 15:26
 * Description:用于管理项目的文件目录
 * email:1348172474@qq.com
 */

public class AppCache {
    private static Context mContext;
    public static final String TEXT = "text";
    public static final String PIC = "pic";
    public static final String VIDEO = "video";
    public static final String AUDIO = "audio";
    public static final String USER_PIC = "uesr_pic";


    /**
     * 应用内容文件放置在应用内部 跟随应用一起卸载
     */
    public static void initAppCache(Context context) {//这里最好传application实例
        mContext =context;
    }

    public static String getAppCacheRootDirectory() {
        String rootDirectory=null;
        try {
            if (mContext.getCacheDir()!=null) {//获取应用内部存储目录
                File parentFile = mContext.getCacheDir().getParentFile();//项目包名

                rootDirectory = parentFile.getCanonicalPath();
            }
            if (TextUtils.isEmpty(rootDirectory)){
                if (Environment.getExternalStorageState()==Environment.MEDIA_MOUNTED){
                    rootDirectory = Environment.getExternalStorageDirectory() + File.separator + mContext.getPackageName();
                }
            }
            Logger.d("rootDirectory="+rootDirectory);
            return rootDirectory;
        } catch (IOException e) {
            Logger.d("e="+e.getMessage());
            e.printStackTrace();
            return rootDirectory;
        }
    }
    public static String getAppCachePicDirectory(){
        String appCacheRootDirectory = getAppCacheRootDirectory();
        String appPicDirectory=null;

        if (!TextUtils.isEmpty(appCacheRootDirectory)){
            File filePic=new File(appPicDirectory=appCacheRootDirectory+File.separator+PIC);
            if (!filePic.exists()){
                filePic.mkdir();
            }
            try {
                appPicDirectory=filePic.getCanonicalPath();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return appPicDirectory;
    }

    /**
     * 存储用户信息头像
     * @return
     */
    public static String getUserPicDirectory(){
        String appCacheRootDirectory = getAppCacheRootDirectory();
        String appPicDirectory=null;

        if (!TextUtils.isEmpty(appCacheRootDirectory)){
            File filePic=new File(appPicDirectory=appCacheRootDirectory+File.separator+USER_PIC);
            if (!filePic.exists()){
                filePic.mkdir();
            }
            try {
                appPicDirectory=filePic.getCanonicalPath();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return appPicDirectory;
    }
    /**
     * 优先获取外部头像地址
     */
    public static String getUserPicCacheDirectory(Context context){
        String externalCacheDir=null;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)||Environment.isExternalStorageRemovable()){
             externalCacheDir = context.getExternalCacheDir().getPath();
        }else{
            externalCacheDir=context.getCacheDir().getPath();
        }
        Logger.d("externalCacheDir="+externalCacheDir);
        return externalCacheDir;
    }
}
