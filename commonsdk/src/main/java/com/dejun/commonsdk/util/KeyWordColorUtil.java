package com.dejun.commonsdk.util;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Author:DoctorWei
 * Time:2018/12/24 17:46
 * Description:关键字变色
 * email:1348172474@qq.com
 */

public class KeyWordColorUtil {
    /**
     * 区分大小写的关键字变色
     * @param color
     * @param text
     * @param keyword
     * @return
     */
    public static SpannableString  checkKeyWord(String color,String text,String keyword){
        Pattern pattern=Pattern.compile(keyword);
        Matcher matcher = pattern.matcher(text);
        SpannableString spannableString=new SpannableString(text);
        while (matcher.find()){
            int start = matcher.start();
            int end = matcher.end();
            spannableString.setSpan(new ForegroundColorSpan(Color.parseColor(color)),start,end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return spannableString;
    }
    /**
     * 不区分大小写的关键字变色
     * @param color
     * @param text
     * @param keyword
     * @return
     */
    public static SpannableString  checkKeyWordWithoutUpOrLower(String color,String text,String keyword){
        String toLowerCaseText = text.toLowerCase();
        String toLowerCaseKeyword = keyword.toLowerCase();
        return checkKeyWord(color,toLowerCaseText,toLowerCaseKeyword);
    }

    /**
     * 带关键字点击效果的颜色变化
     * @param color
     * @param text
     * @param keyword
     * @param keyWordClikListner
     * @return
     */
    public static SpannableString checkKeyWordWithoutWithClick(String color,String text,String keyword,NoUnderLineSpan.KeyWordClikListner keyWordClikListner){
        Pattern pattern=Pattern.compile(keyword);
        Matcher matcher = pattern.matcher(text);
        SpannableString spannableString=new SpannableString(text);
        while (matcher.find()){
            int start = matcher.start();
            int end = matcher.end();
            spannableString.setSpan(new NoUnderLineSpan(color,keyWordClikListner),start,end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return spannableString;
    }

}
