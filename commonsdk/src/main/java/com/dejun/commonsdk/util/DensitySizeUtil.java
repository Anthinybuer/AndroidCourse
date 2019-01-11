package com.dejun.commonsdk.util;

import android.content.Context;
import android.content.res.ColorStateList;
import android.support.annotation.ColorInt;
import android.text.TextUtils;
import android.util.TypedValue;

/**
 * Author:DoctorWei
 * Time:2018/12/10 11:10
 * Description:屏幕尺寸工具类
 * email:1348172474@qq.com
 */

public class DensitySizeUtil {
    /**
     * dp转换成像素
     * @param context
     * @param dpValue
     * @return
     */
    public static int dp2px(Context context,int dpValue){
        float density = context.getResources().getDisplayMetrics().density;
        int pxValue= (int) (dpValue*density+0.5f);
        return pxValue;
    }

    /**
     * 像素转换成dp
     * @param context
     * @param pxValue
     * @return
     */
    public static int px2dp(Context context,int pxValue){
        float density=context.getResources().getDisplayMetrics().density;
        int dpValue= (int) (pxValue/density+0.5f);
        return dpValue;
    }
    /**
     * sp转换成像素
     * @param context
     * @param spValue
     * @return
     */
    public static int sp2px(Context context,int spValue){
        float density = context.getResources().getDisplayMetrics().density;
        int pxValue= (int) (spValue*density+0.5f);
        return pxValue;
    }

    /**
     * 像素转换成sp
     * @param context
     * @param spValue
     * @return
     */
    public static int px2sp(Context context,float spValue){
        float density=context.getResources().getDisplayMetrics().density;
        int dpValue= (int) (spValue/density+0.5f);
        return dpValue;
    }

    /**
     * 可以使用系统的TypeValue来进行转换
     */
    public static int typeValueDp2px(Context context,float dpValue){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dpValue,context.getResources().getDisplayMetrics());
    }
    /**
     * 可以使用系统的TypeValue来进行转换
     */
    public static int typeValueSp2px(Context context,float spValue){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,spValue,context.getResources().getDisplayMetrics());
    }
    /**
     * 可以使用系统的TypeValue来进行转换
     */
    public static int typeValuePx2dp(Context context,float pxValue){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX,pxValue,context.getResources().getDisplayMetrics());
    }
    /**
     * 对TextView、Button等设置不同状态时其文字颜色。
     * 参见：http://blog.csdn.net/sodino/article/details/6797821
     * Modified by liyujiang at 2015.08.13
     */
    public static ColorStateList toColorStateList(@ColorInt int normalColor, @ColorInt int pressedColor,
                                                  @ColorInt int focusedColor, @ColorInt int unableColor) {
        int[] colors = new int[]{pressedColor, focusedColor, normalColor, focusedColor, unableColor, normalColor};
        int[][] states = new int[6][];
        states[0] = new int[]{android.R.attr.state_pressed, android.R.attr.state_enabled};
        states[1] = new int[]{android.R.attr.state_enabled, android.R.attr.state_focused};
        states[2] = new int[]{android.R.attr.state_enabled};
        states[3] = new int[]{android.R.attr.state_focused};
        states[4] = new int[]{android.R.attr.state_window_focused};
        states[5] = new int[]{};
        return new ColorStateList(states, colors);
    }
    public static ColorStateList toColorStateList(@ColorInt int normalColor, @ColorInt int pressedColor) {
        return toColorStateList(normalColor, pressedColor, pressedColor, normalColor);
    }
}
