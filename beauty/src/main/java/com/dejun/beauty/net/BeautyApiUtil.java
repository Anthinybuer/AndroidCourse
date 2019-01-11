package com.dejun.beauty.net;

import com.dejun.beauty.BeautyApi;
import com.dejun.commonsdk.util.XStreamUtil;

import java.io.InputStream;

/**
 * Author:DoctorWei
 * Time:2018/12/5 19:50
 * Description:
 * email:1348172474@qq.com
 */

public class BeautyApiUtil {
     private static BeautyApiUtil beautyApiUtil;

         private BeautyApiUtil() {
         }

         /**
          * 懒汉多线程单例
          *
          * @return
          */
         public static BeautyApiUtil getInstance() {
             if (beautyApiUtil == null) {
                 synchronized (BeautyApiUtil.class) {
                     if (beautyApiUtil == null) {
                         beautyApiUtil = new BeautyApiUtil();
                     }
                 }
             }
             return beautyApiUtil;
         }
         public BeautyApi getBeautyApi(InputStream inputStream, Class<?> objClass, String rootName){
             Object o = XStreamUtil.getInstance().fromStringToXmlModel(inputStream, objClass, rootName);
             if (o instanceof BeautyApi){
                 return (BeautyApi)o;
             }else{
                 return null;
             }
         }
}
