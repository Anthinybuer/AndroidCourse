package com.dejun.commonsdk.util;

import com.thoughtworks.xstream.XStream;

import java.io.InputStream;

/**
 * Author:DoctorWei
 * Time:2018/12/5 19:28
 * Description:
 * email:1348172474@qq.com
 */

public class XStreamUtil {
     private static XStreamUtil xStreamUtil;

         private XStreamUtil() {
         }

         /**
          * 懒汉多线程单例
          *
          * @return
          */
         public static XStreamUtil getInstance() {
             if (xStreamUtil == null) {
                 synchronized (XStreamUtil.class) {
                     if (xStreamUtil == null) {
                         xStreamUtil = new XStreamUtil();
                     }
                 }
             }
             return xStreamUtil;
         }
         public String toXmlString(){
             return "";
         }
    public String toXmlModel(String content){
        return "";
    }
    public Object fromStringToXmlModel(InputStream inputStream,Class<?> objClass,String rootName){
        XStream xStream=new XStream();
        xStream.alias( rootName,objClass);
        Object object =  xStream.fromXML(inputStream);
        return object;
    }
}
