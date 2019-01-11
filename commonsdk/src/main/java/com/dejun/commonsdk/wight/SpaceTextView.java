package com.dejun.commonsdk.wight;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ScaleXSpan;
import android.util.AttributeSet;
import android.widget.TextView;

import com.dejun.commonsdk.R;

/**
 * Author:DoctorWei
 * Time:2019/1/10 19:10
 * Description:自定义TextView解决中文字换行和中文字问题
 * email:1348172474@qq.com
 */

@SuppressLint("AppCompatCustomView")
public class SpaceTextView extends TextView{
    private float spacing = Spacing.NORMAL;
    private CharSequence originalText = "";
    public SpaceTextView(Context context) {
        super(context);
    }

    public SpaceTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray=context.obtainStyledAttributes(attrs,R.styleable.SpaceTextView);
        float textSize = typedArray.getDimension(R.styleable.SpaceTextView_text_space, 0f);
        String textContent = typedArray.getString(R.styleable.SpaceTextView_text_content);
        setSpacing(textSize);
        setText(textContent);
        typedArray.recycle();
    }

    public SpaceTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * 获取字间距
     */
    public float getSpacing() {
        return this.spacing;
    }

    /**
     * 设置间距
     */
    public void setSpacing(float spacing) {
        this.spacing = spacing;
        applySpacing();
    }

    @Override
    public void setText(CharSequence text, TextView.BufferType type) {
        originalText = text;
        applySpacing();
    }

    @Override
    public CharSequence getText() {
        return originalText;
    }

    /**
     * 扩大文字空间
     */
    private void applySpacing() {
        if (this == null || this.originalText == null) return;
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < originalText.length(); i++) {
            builder.append(originalText.charAt(i));
            if (i + 1 < originalText.length()) {
                // \u00A0 不间断空格 碰见文字追加空格
                if (isEnglish(originalText.charAt(i) + "") && isEnglish(originalText.charAt(i + 1) + "")) {
                    builder.append("\u00A0");
                } else {
                    builder.append("\u00A0");
                }
            }
        }
        // 通过SpannableString类，去设置空格
        SpannableString finalText = new SpannableString(builder.toString());
        // 如果当前TextView内容长度大于1，则进行空格添加
        if (builder.toString().length() > 1) {
            for (int i = 1; i < builder.toString().length(); i += 2) {
                // ScaleXSpan 基于x轴缩放  按照x轴等比例进行缩放 通过字间距+1除以10进行等比缩放
                finalText.setSpan(new ScaleXSpan((spacing + 1) / 10), i, i + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        super.setText(finalText, TextView.BufferType.SPANNABLE);
    }

    public class Spacing {
        public final static float NORMAL = 0;
    }
    /**
     *  判断是否是英语
     */
    public static boolean isEnglish(String charaString) {
        return charaString.matches("^[a-zA-Z]*");
    }
}
