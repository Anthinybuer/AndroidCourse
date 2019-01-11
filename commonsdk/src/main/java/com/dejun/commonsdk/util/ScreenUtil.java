package com.dejun.commonsdk.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;

import com.orhanobut.logger.Logger;

/**
 * Author:DoctorWei
 * Time:2018/12/10 11:01
 * Description:获取屏幕相关参数工具类
 * email:1348172474@qq.com
 */

public class ScreenUtil {
    /**
     * 获取屏幕的宽
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context){
        DisplayMetrics displayMetrics=context.getResources().getDisplayMetrics();
        if (displayMetrics!=null){
            return displayMetrics.widthPixels;
        }else{
            return 0;
        }
    }

    /**
     * 获取屏幕的高
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context){
        DisplayMetrics displayMetrics=context.getResources().getDisplayMetrics();
        if (displayMetrics!=null){
            return displayMetrics.heightPixels;
        }else{
            return 0;
        }
    }

    /**
     * 判断屏幕是否是竖屏
     * @param context
     * @return
     */
    public static boolean isPortrait(Context context){
        boolean isPortrait=context.getResources().getConfiguration().orientation== Configuration.ORIENTATION_PORTRAIT;
        return isPortrait;
    }

    /**
     * 判断是否是平板
     * @param context
     * @return
     */
    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }
    private static boolean isFullScreen = false;
    private static DisplayMetrics dm = null;

    public static DisplayMetrics displayMetrics(Context context) {
        if (null != dm) {
            return dm;
        }
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(dm);
        Logger.d("screen width=" + dm.widthPixels + "px, screen height=" + dm.heightPixels
                + "px, densityDpi=" + dm.densityDpi + ", density=" + dm.density);
        return dm;
    }

    public static int widthPixels(Context context) {
        return displayMetrics(context).widthPixels;
    }

    public static int heightPixels(Context context) {
        return displayMetrics(context).heightPixels;
    }

    public static float density(Context context) {
        return displayMetrics(context).density;
    }

    public static int densityDpi(Context context) {
        return displayMetrics(context).densityDpi;
    }

    public static boolean isFullScreen() {
        return isFullScreen;
    }

    public static void toggleFullScreen(Activity activity) {
        Window window = activity.getWindow();
        int flagFullscreen = WindowManager.LayoutParams.FLAG_FULLSCREEN;
        if (isFullScreen) {
            window.clearFlags(flagFullscreen);
            isFullScreen = false;
        } else {
            window.setFlags(flagFullscreen, flagFullscreen);
            isFullScreen = true;
        }
    }

    /**
     * 保持屏幕常亮
     */
    public static void keepBright(Activity activity) {
        //需在setContentView前调用
        int keepScreenOn = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
        activity.getWindow().setFlags(keepScreenOn, keepScreenOn);
    }
}
