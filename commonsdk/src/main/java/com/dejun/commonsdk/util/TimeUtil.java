package com.dejun.commonsdk.util;

import android.support.annotation.NonNull;

import com.orhanobut.logger.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Author:DoctorWei
 * Time:2018/12/21 13:40
 * Description:时间工具类主要对时间进行格式化
 * email:1348172474@qq.com
 */

public class TimeUtil {
    /**
     * 将毫秒数转换成时间
     *
     * @param longTime
     * @return
     */
    public static String formatLongTime(Long longTime) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formatTime = simpleDateFormat.format(longTime);
        return formatTime;
    }

    /**
     * 将字符串时间转换成毫秒数
     */
    public static Long formatStringTime(String strTime) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            long formatTime = simpleDateFormat.parse(strTime).getTime();
            return formatTime;
        } catch (ParseException e) {
            e.printStackTrace();

        }
        return -1l;
    }

    /**
     *时间戳格式转换
     */

    static String dayNames[] = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
    public static String getChatTime(long timesamp) {
        String result = "";
        Calendar todayCalendar = Calendar.getInstance();
        Calendar otherCalendar = Calendar.getInstance();
        otherCalendar.setTimeInMillis(timesamp);
        String timeFormat="M月d日 HH:mm";
        String yearTimeFormat="yyyy年M月d日 HH:mm";
        String am_pm="";
        int hour=otherCalendar.get(Calendar.HOUR_OF_DAY);
        if(hour>=0&&hour<6){
            am_pm="凌晨";
        }else if(hour>=6&&hour<12){
            am_pm="早上";
        }else if(hour==12){
            am_pm="中午";
        }else if(hour>12&&hour<18){
            am_pm="下午";
        }else if(hour>=18){
            am_pm="晚上";
        }
        boolean yearTemp = todayCalendar.get(Calendar.YEAR)==otherCalendar.get(Calendar.YEAR);
        if(yearTemp){
            int todayMonth=todayCalendar.get(Calendar.MONTH);
            int otherMonth=otherCalendar.get(Calendar.MONTH);
            if(todayMonth==otherMonth){//表示是同一个月
                int temp=todayCalendar.get(Calendar.DATE)-otherCalendar.get(Calendar.DATE);
                switch (temp) {
                    case 0:
                        result = am_pm+getHourAndMin(timesamp);
                        break;
                    case 1:
                        result = "昨天 " + getHourAndMin(timesamp);
                        break;
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                        int dayOfMonth = otherCalendar.get(Calendar.WEEK_OF_MONTH);
                        int todayOfMonth=todayCalendar.get(Calendar.WEEK_OF_MONTH);
                        if(dayOfMonth==todayOfMonth){//表示是同一周
                            int dayOfWeek=otherCalendar.get(Calendar.DAY_OF_WEEK);
                            if(dayOfWeek!=1){//判断当前是不是星期日     如想显示为：周日 12:09 可去掉此判断
                                result = dayNames[otherCalendar.get(Calendar.DAY_OF_WEEK)-1] + getHourAndMin(timesamp);
                            }else{
                                result = getTime(timesamp,timeFormat);
                            }
                        }else{
                            result = getTime(timesamp,timeFormat);
                        }
                        break;
                    default:
                        result = getTime(timesamp,timeFormat);
                        break;
                }
            }else{
                result = getTime(timesamp,timeFormat);
            }
        }else{
            result=getYearTime(timesamp,yearTimeFormat);
        }
        return result;
    }

    /**
     * 当天的显示时间格式
     * @param time
     * @return
     */
    public static String getHourAndMin(long time) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        return format.format(new Date(time));
    }

    /**
     * 不同一周的显示时间格式
     * @param time
     * @param timeFormat
     * @return
     */
    public static String getTime(long time,String timeFormat) {
        SimpleDateFormat format = new SimpleDateFormat(timeFormat);
        return format.format(new Date(time));
    }

    /**
     * 不同年的显示时间格式
     * @param time
     * @param yearTimeFormat
     * @return
     */
    public static String getYearTime(long time,String yearTimeFormat) {
        SimpleDateFormat format = new SimpleDateFormat(yearTimeFormat);
        return format.format(new Date(time));
    }
    /**
     * 月日时分秒，0-9前补0
     */
    @NonNull
    public static String fillZero(int number) {
        return number < 10 ? "0" + number : "" + number;
    }
    /**
     * 截取掉前缀0以便转换为整数
     *
     * @see #fillZero(int)
     */
    public static int trimZero(@NonNull String text) {
        try {
            if (text.startsWith("0")) {
                text = text.substring(1);
            }
            return Integer.parseInt(text);
        } catch (NumberFormatException e) {
            Logger.d(e);
            return 0;
        }
    }
    /**
     * 根据年份及月份计算每月的天数
     */
    public static int calculateDaysInMonth(int year, int month) {
        // 添加大小月月份并将其转换为list,方便之后的判断
        String[] bigMonths = {"1", "3", "5", "7", "8", "10", "12"};
        String[] littleMonths = {"4", "6", "9", "11"};
        List<String> bigList = Arrays.asList(bigMonths);
        List<String> littleList = Arrays.asList(littleMonths);
        // 判断大小月及是否闰年,用来确定"日"的数据
        if (bigList.contains(String.valueOf(month))) {
            return 31;
        } else if (littleList.contains(String.valueOf(month))) {
            return 30;
        } else {
            if (year <= 0) {
                return 29;
            }
            // 是否闰年
            if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
                return 29;
            } else {
                return 28;
            }
        }
    }
}
