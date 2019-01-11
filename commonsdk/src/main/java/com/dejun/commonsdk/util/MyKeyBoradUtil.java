package com.dejun.commonsdk.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.inputmethod.InputMethodManager;

/**
 * Author:DoctorWei
 * Time:2018/12/27 9:13
 * Description:软键盘的管理包括 开启和关闭
 * email:1348172474@qq.com
 */

public class MyKeyBoradUtil {
    /**
     * 此方法需要指定焦点 （所以EditText需要绘制完成并获取焦点）
     * @param activity
     * @param isShow
     */
    public static void showKeyBoard(Activity activity, boolean isShow){
        InputMethodManager inputMethodManager= (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager==null){
            return;
        }
        if (isShow){
            if (activity.getCurrentFocus()!=null){//有焦点打开
                inputMethodManager.showSoftInput(activity.getCurrentFocus(),0);
            }else{//无焦点打开
                inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
            }
        }else{
            if (activity.getCurrentFocus()!=null){//有焦点关闭
                inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
            }else{
                //无焦点关闭
                inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
            }
        }
    }
    /**
     * 隐藏或显示软键盘 无需和焦点相关
     * 如果现在是显示调用后则隐藏 反之则显示
     * @param activity
     */
    public static void showOrHideSoftKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }
    /**
     * 判断软键盘是否显示方法
     * @param activity
     * @return
     */

    public static boolean isSoftShowing(Activity activity) {
        //获取当屏幕内容的高度
        int screenHeight = activity.getWindow().getDecorView().getHeight();
        //获取View可见区域的bottom
        Rect rect = new Rect();
        //DecorView即为activity的顶级view
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        //考虑到虚拟导航栏的情况（虚拟导航栏情况下：screenHeight = rect.bottom + 虚拟导航栏高度）
        //选取screenHeight*2/3进行判断
        return screenHeight * 2 / 3 > rect.bottom + getSoftButtonsBarHeight(activity);//软键盘左顶点饿的高度在屏幕的2/3以内
    }
    /**
     * 底部虚拟按键栏的高度
     * getRealMetrics()和getMetrics()获取到的屏幕信息差别只在于widthPixels或heightPixels的值是否去除虚拟键所占用的像素，和是否全屏和沉浸模式无关。
     * @return
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private static int getSoftButtonsBarHeight(Activity activity) {
        DisplayMetrics metrics = new DisplayMetrics();
        //这个方法获取可能不是真实屏幕的高度 去除了虚拟按键
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int usableHeight = metrics.heightPixels;
        //获取当前屏幕的真实高度
        activity.getWindowManager().getDefaultDisplay().getRealMetrics(metrics);
        int realHeight = metrics.heightPixels;
        if (realHeight > usableHeight) {
            return realHeight - usableHeight;
        } else {
            return 0;
        }
    }
}
