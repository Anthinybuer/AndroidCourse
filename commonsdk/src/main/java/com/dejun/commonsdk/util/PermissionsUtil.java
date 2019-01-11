package com.dejun.commonsdk.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.SimpleArrayMap;

import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Author:DoctorWei
 * Time:2018/12/17 14:19
 * Description:动态权限申请工具类 一般的软件一般需要文件读写权限 访问通讯录权限 定位权限 相机拍照 录音等权限
 * email:1348172474@qq.com
 */

public class PermissionsUtil {
    public static int REQUEST_CAMERA = 1;
    public static int REQUEST_EXTERNAL_STOREGE = 2;
    public static int REQUEST_CONTACTS = 3;
    public static int REQUEST_LOCATION = 4;
    private static final SimpleArrayMap<String, Integer> MIN_SDK_PERMISSIONS;

    static {
        MIN_SDK_PERMISSIONS = new SimpleArrayMap<>(8);//超过8就进行扩容
        MIN_SDK_PERMISSIONS.put("com.android.voicemail.permission.ADD_VOICEMAIL", 14);
        MIN_SDK_PERMISSIONS.put("android.permission.BODY_SENSORS", 20);
        MIN_SDK_PERMISSIONS.put("android.permission.READ_CALL_LOG", 16);
        MIN_SDK_PERMISSIONS.put("android.permission.READ_EXTERNAL_STORAGE", 16);
        MIN_SDK_PERMISSIONS.put("android.permission.USE_SIP", 9);
        MIN_SDK_PERMISSIONS.put("android.permission.WRITE_CALL_LOG", 16);
        MIN_SDK_PERMISSIONS.put("android.permission.SYSTEM_ALERT_WINDOW", 23);
        MIN_SDK_PERMISSIONS.put("android.permission.WRITE_SETTINGS", 23);
    }

    private static boolean permissionExists(String permission) {
        Integer minVersion = MIN_SDK_PERMISSIONS.get(permission);
        return minVersion == null || Build.VERSION.SDK_INT >= minVersion;
    }

    /**
     * 手否有拍照权限
     *
     * @param activity
     * @return
     */
    public static boolean hasCameraPermission(Activity activity,int requestCode) {
        Logger.d("hasCameraPermission");
        if (!permissionExists(Manifest.permission.CAMERA)) {//无需动态申请权限
            Logger.d("your system does not suppport" + Manifest.permission.CAMERA + " permission");
            return false;
        }
        boolean requestPermission = requestPermission(activity, Manifest.permission.CAMERA,requestCode );
        Logger.d("requestPermission="+requestPermission);
        return requestPermission;
    }

    /**
     * 是否有地理位置权限
     */
    public static boolean hasLocationPermission(Activity activity,int requestCode) {
        boolean requestPermissions = requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},requestCode);//允许应用访问精确(如GPS)性的定位 获取粗糙位置权限
        return requestPermissions;
    }
    /**
     * 动态获取写的权限
     */
    public static boolean hasExternalWritePermission(Activity activity,int requestCode) {
        boolean requestPermissions = requestPermissions(activity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},requestCode);//允许应用访问精确(如GPS)性的定位 获取粗糙位置权限
        return requestPermissions;
    }
    /**
     * 检测是否需要一个权限
     */
    public static boolean checkSinglePermisssion(Context context,String permission){
        if (ContextCompat.checkSelfPermission(context,permission)==PackageManager.PERMISSION_GRANTED) {
            return true;
        }else {
            return false;
        }
    }
    /**
     * 请求权限
     */
    public static boolean requestPermission(Activity activity, String permission, int requestCode) {
        if (checkSinglePermisssion(activity,permission)){
            return true;
        }else{
            ActivityCompat.requestPermissions( activity, new String[]{permission}, requestCode);
            return false;
        }

    }
    public static boolean requestPermissions(Activity activity, String[] permissions, int requestCode) {
        List<String> unGrantedPermissions = checkMorePermisssions(activity, permissions);
        if (unGrantedPermissions.size()==0){//无需请求
            return true;
        }else{
            requestMorePermissions(activity, unGrantedPermissions, requestCode);
            return false;
        }
    }

    private static void requestMorePermissions(Activity activity, List<String> unGrantedPermissions, int requestCode) {
        String[] permissions = (String[]) unGrantedPermissions.toArray(new String[unGrantedPermissions.size()]);
        requestMorePermissions(activity, permissions, requestCode);
    }
    /**
     * 请求多个权限
     */
    public static void requestMorePermissions(Activity activity, String[] permissions, int requestCode) {
        ActivityCompat.requestPermissions(activity, permissions, requestCode);
    }
    /**
     * 检测多个权限 查询出未授权的权限
     * @param context
     * @param permissions
     * @return
     */
    public static List<String> checkMorePermisssions(Context context,String[]  permissions){
        List<String> unGrantedPermissions=new ArrayList<>();
        for (int i = 0; i <permissions.length ; i++) {
            String permission = permissions[i];
            if (!checkSinglePermisssion(context,permission)){
                unGrantedPermissions.add(permission);
            }
        }
       return unGrantedPermissions;

    }
    /**
     * 判断权限是否申请成功
     */
    public static boolean permissionRequest(int[] grantResults) {
        if (grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            return true;
        else
            return false;
    }
    /**
     * 用户申请多个权限返回
     */
    public static boolean permissionsRequest(Context context, String[] permissions) {
        List<String> permissionList = checkMorePermisssions(context, permissions);
        if (permissionList.size() == 0) {
            return true;
        } else {
            return false;
        }

    }
}
