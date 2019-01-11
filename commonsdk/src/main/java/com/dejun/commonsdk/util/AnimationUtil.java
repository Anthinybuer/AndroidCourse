package com.dejun.commonsdk.util;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

/**
 * Author:DoctorWei
 * Time:2019/1/10 21:09
 * Description:
 * email:1348172474@qq.com
 */

public class AnimationUtil {
    public static final Long ANIMA_TIME = 300L;
    public static final int DECELERATE = 2;//减速
    public static final int ACCELERATE = 1;//加速sss
    /**
     * 旋转动画
     *
     * @param paramView
     * @param duration
     *            时间
     */
    public static void startRotateAnimation(View paramView,float fromDegrees,float toDegrees,int duration) {
        float[] arrayOfFloat = new float[2];
        arrayOfFloat[0] = fromDegrees;
        arrayOfFloat[1] = toDegrees;
        ObjectAnimator localObjectAnimator = ObjectAnimator.ofFloat(paramView,
                "rotation", arrayOfFloat);
        localObjectAnimator.setDuration(duration);

        localObjectAnimator.setInterpolator(null);
        localObjectAnimator.setRepeatCount(-1);
        localObjectAnimator.start();
    }
    /**
     * 开启Alpha 动画
     *
     * @param view
     * @param fromAlpha
     * @param toAlpha
     */
    public static void startAlphaAnima(View view,
                                       float fromAlpha, float toAlpha, int speedType,int duration) {
        if (view == null) {
            return;
        }
        if (view.getAnimation() != null) {
            view.getAnimation().cancel();
            view.clearAnimation();
            return;
        }
        //-------Alpaha--------
        AnimatorSet set = new AnimatorSet();
        set.play(ObjectAnimator.ofFloat(view, "alpha", fromAlpha, toAlpha));
        set.setDuration(duration);
        if (speedType == ACCELERATE)
            set.setInterpolator(new AccelerateInterpolator());//加速
        else if (speedType == DECELERATE)
            set.setInterpolator(new DecelerateInterpolator());//减速
        set.start();

    }
    /**
     * 清理目标View的动画
     *
     * @param paramView
     */
    public static void clearAnimation(View paramView) {
        if (paramView == null)
            return;

        if (paramView.getAnimation() == null) {
            return;
        }

        paramView.getAnimation().cancel();
        paramView.clearAnimation();
    }

    /**
     * 水平移动
     * @param view
     * @param fromX
     * @param toX
     * @param duration
     */
    public static void startTransXAnimation(View view, int fromX,int toX,int duration) {
        ObjectAnimator translationX = ObjectAnimator.ofFloat(view, "translationX", fromX, toX);
        AnimatorSet animatorSet = new AnimatorSet();  //组合动画
        animatorSet.setInterpolator(new DecelerateInterpolator());
        animatorSet.play(translationX); //设置动画
        animatorSet.setDuration(duration);  //设置动画时间
        animatorSet.start();
    }

    /**
     * 竖直移动
     * @param view
     * @param fromY
     * @param toY
     * @param duration
     */
    public static void startTransYAnimation(View view, int fromY,int toY,int duration) {
        ObjectAnimator translationY = ObjectAnimator.ofFloat(view, "translationY", fromY, toY);
        AnimatorSet animatorSet = new AnimatorSet();  //组合动画
        animatorSet.setInterpolator(new DecelerateInterpolator());
        animatorSet.play( translationY); //设置动画
        animatorSet.setDuration(duration);  //设置动画时间
        animatorSet.start();
    }

    /**
     * 水平和竖直同事移动
     * @param view
     * @param fromX
     * @param toX
     * @param fromY
     * @param toY
     * @param duration
     */
    public static void startTransXYAnimation(View view, int fromX,int toX,int fromY,int toY,int duration) {
        ObjectAnimator translationX = ObjectAnimator.ofFloat(view, "translationX", fromX, toX);
        ObjectAnimator translationY = ObjectAnimator.ofFloat(view, "translationY", fromY, toY);
        AnimatorSet animatorSet = new AnimatorSet();  //组合动画
        animatorSet.setInterpolator(new DecelerateInterpolator());
        animatorSet.playTogether(translationX, translationY); //设置动画
        animatorSet.setDuration(duration);  //设置动画时间
        animatorSet.start();
    }

}
