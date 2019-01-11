package com.dejun.commonsdk.util;

import android.os.Handler;
import android.os.Message;
import android.widget.TextView;
import java.lang.ref.WeakReference;

/**
 * Author:DoctorWei
 * Time:2018/12/22 14:57
 * Description:
 * email:1348172474@qq.com
 */

public class CountDownTimerUtil {
    private TextView downTimeTv;//倒计时按钮
    public static final int MSG_CODE = 100;
    public static final long WHOLE_TIME = 60000l;//倒计时总时间 单位秒
    public static final long INTERNAL_TIME = 1000l;//倒计时间隔时间
    public static final String RESET_SEND = "重新发送";
    public static final String AFTER_SECONDS = "秒后";
    private long millisInFuture;
    private long countDownInterval;
    private WeakReference<TextView> weakReference;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int what = msg.what;
            if (what == MSG_CODE) {
                if (millisInFuture > 0) {//倒计时 不可点击
                    showTvEnable(false);
                    millisInFuture -= countDownInterval;
                    if (weakReference.get() != null) {
                        sendEmptyMessageDelayed(MSG_CODE, countDownInterval);
                    }
                } else {//结束倒计时
                    showTvEnable(true);
                }
            }
        }
    };

    private void showTvEnable(boolean tvEnable) {
        TextView textView = weakReference.get();
        if (textView != null) {
            textView.setEnabled(tvEnable);
            if (tvEnable) {
                textView.setText(RESET_SEND);
                if (tvEnableFalseTvColor != 0) {
                    textView.setTextColor(tvEnableFalseTvColor);
                }
                if (tvEnableFalseBgColor != 0) {
                    textView.setBackgroundResource(tvEnableFalseBgColor);
                }
            } else {
                textView.setText(millisInFuture / 1000 + AFTER_SECONDS + RESET_SEND);
                if (tvEnableTrueTvColor != 0) {
                    textView.setTextColor(tvEnableTrueTvColor);
                }
                if (tvEnableTrueBgColor != 0) {
                    textView.setBackgroundResource(tvEnableTrueBgColor);
                }

            }
        }
    }

    public CountDownTimerUtil(TextView downTimeTv) {
        this(downTimeTv, WHOLE_TIME, INTERNAL_TIME);
    }

    public CountDownTimerUtil(TextView downTimeTv, long millisInFuture, long countDownInterval) {
        this.downTimeTv = downTimeTv;
        weakReference = new WeakReference<TextView>(downTimeTv);
        this.millisInFuture = millisInFuture;
        this.countDownInterval = countDownInterval;
    }

    public CountDownTimerUtil startCountDownTime() {
        handler.removeMessages(MSG_CODE);
        handler.sendEmptyMessage(MSG_CODE);
        return this;
    }

    private int tvEnableTrueTvColor;
    private int tvEnableFalseTvColor;
    private int tvEnableTrueBgColor;
    private int tvEnableFalseBgColor;

    public CountDownTimerUtil intColor(int tvEnableTrueTvColor, int tvEnableFalseTvColor, int tvEnableTrueBgColor, int tvEnableFalseBgColor) {
        this.tvEnableTrueTvColor = tvEnableTrueTvColor;
        this.tvEnableFalseTvColor = tvEnableFalseTvColor;
        this.tvEnableTrueBgColor = tvEnableTrueBgColor;
        this.tvEnableFalseBgColor = tvEnableFalseBgColor;
        return this;
    }

}
