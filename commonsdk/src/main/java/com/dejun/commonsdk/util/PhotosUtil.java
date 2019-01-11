package com.dejun.commonsdk.util;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.widget.Toast;

import com.dejun.commonsdk.R;
import com.dejun.commonsdk.cache.AppCache;
import com.dejun.commonsdk.constant.ActivityCode;

import java.io.File;

/**
 * Author:DoctorWei
 * Time:2018/12/19 16:35
 * Description:图片获取工具类
 * email:1348172474@qq.com
 */

public class PhotosUtil {
    private File file;//需要保存的图片
    private Uri uri;//获取到的图片uri
    private Activity activity;
    private static PhotosUtil photosUtil;

    public void init(Activity activity) {
        this.activity=activity;
        if (this.file==null){
            this.file= new File(AppCache.getUserPicCacheDirectory(activity),"user_avartar.jpg");
        }
        if (Build.VERSION.SDK_INT<Build.VERSION_CODES.N){
            this.uri = Uri.fromFile(file);
        }else{
            this.uri = FileProvider.getUriForFile(activity.getApplicationContext(), "com.dejun.androidcourse", this.file);
        }

    }

    /**
     * 懒汉多线程单例
     *
     * @return
     */
    public static PhotosUtil getInstance() {
        if (photosUtil == null) {
            synchronized (PhotosUtil.class) {
                if (photosUtil == null) {
                    photosUtil = new PhotosUtil();
                }
            }
        }
        return photosUtil;
    }
    /**
     * 相册
     */
    public void uploadAvatarFromAlbumRequest() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        if (activity!=null&&!activity.isFinishing()) {
            activity.startActivityForResult(photoPickerIntent, ActivityCode.REQUEST_ALBUM);
        }
    }
    /**
     * 拍照
     */
    public void uploadAvatarFromCaptureRequest() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        if (activity!=null&&!activity.isFinishing()) {
            activity.startActivityForResult(intent, ActivityCode.REQUEST_CAPTURE);
        }
    }
    /**
     * 裁剪拍照裁剪
     * @param uri
     */
    private void startPhotoZoom(Uri uri) {
        if (activity!=null&&!activity.isFinishing()) {
            Intent intent = new Intent("com.android.camera.action.CROP");
            intent.setDataAndType(uri, "image/*");
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);//读权限
            intent.putExtra("crop", "true");// crop=true 有这句才能出来最后的裁剪页面.
            intent.putExtra("aspectX", 1);// 这两项为裁剪框的比例.
            intent.putExtra("aspectY", 1);// x:y=fingerprint_01:fingerprint_01
            intent.putExtra("outputX", 600);//图片输出大小 原来是400
            intent.putExtra("outputY", 600);
            intent.putExtra("output", Uri.fromFile(file));
            intent.putExtra("outputFormat", "JPEG");// 返回格式
            activity.startActivityForResult(intent, ActivityCode.REQUEST_PHOTO_ZOOM);
        }
    }
    /**
     * 处理图片和照相的返回结果
     */
    public PhotosUtil onActivityResult(int requestCode, int resultCode, Intent data){
        if (activity!=null&&!activity.isFinishing()) {
            if (resultCode != -1) {
                return this;
            }
            if (requestCode == ActivityCode.REQUEST_ALBUM && data != null) {
                Uri newUri;
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                    newUri = Uri.parse("file:///" + CropUtils.getPath(activity, data.getData()));
                } else {
                    newUri = data.getData();
                }
                if (newUri != null) {
                    startPhotoZoom(newUri);
                } else {
                    Toast.makeText(activity, R.string.dont_get_album_photo, Toast.LENGTH_LONG).show();
                }
            } else if (requestCode == ActivityCode.REQUEST_CAPTURE) {
                startPhotoZoom(uri);

            } else if (requestCode == ActivityCode.REQUEST_PHOTO_ZOOM) {
                // TODO: 2018/12/19 压缩图片
                if (fileZoomCallBack!=null){
                    fileZoomCallBack.fileZoom(file);
                }
            }
        }
        return this;
    }
    public void setFileZoomCallBack(FileZoomCallBack fileZoomCallBack){
        this.fileZoomCallBack=fileZoomCallBack;
    }
    public FileZoomCallBack fileZoomCallBack;
    public interface FileZoomCallBack{
        void fileZoom(File file);
    }
}
