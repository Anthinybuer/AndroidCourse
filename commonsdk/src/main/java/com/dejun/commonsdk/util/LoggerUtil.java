package com.dejun.commonsdk.util;

import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.PrettyFormatStrategy;

/**
 * Author:DoctorWei
 * Time:2018/12/4 19:13
 * Description:
 * email:1348172474@qq.com
 */

public class LoggerUtil {
     private static LoggerUtil loggerUtil;

         private LoggerUtil() {
         }

         /**
          * 懒汉多线程单例
          *
          * @return
          */
         public static LoggerUtil getInstance() {
             if (loggerUtil == null) {
                 synchronized (LoggerUtil.class) {
                     if (loggerUtil == null) {
                         loggerUtil = new LoggerUtil();
                     }
                 }
             }
             return loggerUtil;
         }
         public FormatStrategy getFormatStrategy(){
             FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                     .showThreadInfo(false)  // (Optional) Whether to show thread info or not. Default true
                     .methodCount(0)         // (Optional) How many method line to show. Default 2
                     .methodOffset(7)        // (Optional) Hides internal method calls up to offset. Default 5
                     //.logStrategy(customLog) // (Optional) Changes the log strategy to print out. Default LogCat
                     .tag("WEIQUN")   // (Optional) Global tag for every log. Default PRETTY_LOGGER
                     .build();
             return formatStrategy;
         }
}
