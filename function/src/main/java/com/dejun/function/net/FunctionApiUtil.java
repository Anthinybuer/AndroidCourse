package com.dejun.function.net;

import com.dejun.commonsdk.util.XStreamUtil;
import com.dejun.function.FunctionApi;

import java.io.InputStream;

/**
 * Author:DoctorWei
 * Time:2018/12/5 19:50
 * Description:
 * email:1348172474@qq.com
 */

public class FunctionApiUtil {
     private static FunctionApiUtil functionApiUtil;

         private FunctionApiUtil() {
         }

         /**
          * 懒汉多线程单例
          *
          * @return
          */
         public static FunctionApiUtil getInstance() {
             if (functionApiUtil == null) {
                 synchronized (FunctionApiUtil.class) {
                     if (functionApiUtil == null) {
                         functionApiUtil = new FunctionApiUtil();
                     }
                 }
             }
             return functionApiUtil;
         }
         public FunctionApi getFunctionApi(InputStream inputStream, Class<?> objClass, String rootName){
             Object o = XStreamUtil.getInstance().fromStringToXmlModel(inputStream, objClass, rootName);
             if (o instanceof FunctionApi){
                 return (FunctionApi)o;
             }else{
                 return null;
             }
         }
}
