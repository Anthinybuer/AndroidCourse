package com.dejun.commonsdk.util;

import android.graphics.Color;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

/**
 * Author:DoctorWei
 * Time:2018/12/24 18:20
 * Description:带点击的关键字变色
 * email:1348172474@qq.com
 */

public class NoUnderLineSpan extends ClickableSpan {
    private String mColor;
    private KeyWordClikListner keyWordClikListner;

    public NoUnderLineSpan(String color, KeyWordClikListner keyWordClikListner) {
        mColor = color;
        this.keyWordClikListner = keyWordClikListner;
    }
    @Override
    public void updateDrawState(TextPaint ds) {
        super.updateDrawState(ds);
        //去掉链接的下划线
        ds.setColor(Color.parseColor(mColor));//context.getResources().getColor(color)
        ds.setUnderlineText(false);
        //需要在布局文件的textView中设置textColorLink点击selctor效果
        ds.clearShadowLayer();
    }

    @Override
    public void onClick(View widget) {
        //需要给textView设置tv.setMovementMethod(LinkMovementMethod.getInstance())否则没有点击效果
        if (keyWordClikListner != null) {
            keyWordClikListner.keyWordClick();
        }
    }

    public interface KeyWordClikListner {
        void keyWordClick();
    }
}
