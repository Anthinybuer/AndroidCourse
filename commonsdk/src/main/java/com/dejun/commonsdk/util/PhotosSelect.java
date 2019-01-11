package com.dejun.commonsdk.util;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.widget.Toast;

import com.dejun.commonsdk.R;
import com.dejun.commonsdk.constant.ActivityCode;

import java.io.File;

/**
 * Author:DoctorWei
 * Time:2018/12/20 16:56
 * Description:
 * email:1348172474@qq.com
 */

public class PhotosSelect {
    private File file;
    private Uri uri;
    private Activity activity;
    public PhotosSelect(Activity activity, File file) {
        this.file = file;
        this.activity=activity;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            uri = Uri.fromFile(file);
        } else {
            //通过FileProvider创建一个content类型的Uri(android fingerprint_07.0需要这样的方法跨应用访问)
            uri = FileProvider.getUriForFile(activity.getApplicationContext(), "com.dejun.androidcourse", file);
        }
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != -1) {
            return;
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
            //final File compressFile = FileUtil.getSmallBitmap(this, file.getPath());
            fileZoomCallBack.fileZoom(file);

        }
    }
    /**
     * 裁剪拍照裁剪
     *
     * @param uri
     */
    public void startPhotoZoom(Uri uri) {

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.putExtra("crop", "true");// crop=true 有这句才能出来最后的裁剪页面.
        intent.putExtra("aspectX", 1);// 这两项为裁剪框的比例.
        intent.putExtra("aspectY", 1);// x:y=fingerprint_01:fingerprint_01
        intent.putExtra("outputX", 600);//图片输出大小 原来是400
        intent.putExtra("outputY", 600);
        intent.putExtra("output", Uri.fromFile(file));
        intent.putExtra("outputFormat", "JPEG");// 返回格式
        activity.startActivityForResult(intent, ActivityCode.REQUEST_PHOTO_ZOOM);
    }
    /**
     * nim_message_plus_photo_normal
     */
    public void uploadAvatarFromPhotoRequest() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        activity.startActivityForResult(intent, ActivityCode.REQUEST_CAPTURE);
    }
    /**
     * album
     */
    public void uploadAvatarFromAlbumRequest() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        activity.startActivityForResult(photoPickerIntent, ActivityCode.REQUEST_ALBUM);
    }
    public void setFileZoomCallBack(PhotosUtil.FileZoomCallBack fileZoomCallBack){
        this.fileZoomCallBack=fileZoomCallBack;
    }
    public PhotosUtil.FileZoomCallBack fileZoomCallBack;
    public interface FileZoomCallBack{
        void fileZoom(File file);
    }
}
