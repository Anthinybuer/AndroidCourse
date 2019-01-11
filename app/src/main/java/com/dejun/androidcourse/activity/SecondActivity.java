package com.dejun.androidcourse.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.dejun.androidcourse.R;
import com.dejun.commonsdk.base.BaseActivity;
import com.dejun.commonsdk.base.mvp.BasePresenter;
import com.dejun.commonsdk.popWindow.RecyvlerViewBottomWindow;
import com.dejun.commonsdk.popWindow.RecyvlerViewBottomWindowAdapter;
import com.dejun.commonsdk.service.JobScheduleService;
import com.dejun.commonsdk.util.AlarmManagerUtil;
import com.dejun.commonsdk.util.GlideUtil;
import com.dejun.commonsdk.util.PermissionsUtil;
import com.dejun.commonsdk.util.PhotosSelect;
import com.dejun.commonsdk.util.PhotosUtil;
import com.dejun.commonsdk.util.TimeUtil;
import com.dejun.commonsdk.wight.WrapLinearLayoutManager;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class SecondActivity extends BaseActivity implements View.OnClickListener {

    /**
     * 测试按钮
     */
    private Button mBtn;
    private ImageView mBtn7;
    private LinearLayout view;

    @Override
    public BasePresenter createPresenter() {
        return null;
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void initView() {
        //JobScheduleService.startService(this);
        //首先要在你要接受EventBus的界面注册，这一步很重要
        EventBus.getDefault().register(this);
        EventBus.getDefault().post("咋回事");
        mBtn = (Button) findViewById(R.id.btn);
        mBtn7 = (ImageView) findViewById(R.id.btn7);
        mBtn.setOnClickListener(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_second;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PermissionsUtil.REQUEST_LOCATION) {
//            boolean permissionRequest = PermissionsUtil.isPermissionRequest(grantResults);
//            if (permissionRequest){
//                showPopup();
//            }
            boolean permissionsRequest = PermissionsUtil.permissionsRequest(this, permissions);
            if (permissionsRequest) {
                PhotosUtil.getInstance().uploadAvatarFromCaptureRequest();
            }
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.btn:

//                showPopup();

                openActivityWithIntent(ThirdActivity.class, null);
                break;
        }
    }

    PhotosSelect photosSelect;

    private void showPopup() {
        PhotosUtil.getInstance().init(this);
        WrapLinearLayoutManager linearLayoutManager = new WrapLinearLayoutManager(this);
        List<String> datas = new ArrayList<>();
        datas.add("相册");
        datas.add("拍照");
        datas.add("取消");
        //File file = new File(FileUtil.getCachePath(this), "user-user_avartar.jpg");
        //photosSelect = new PhotosSelect(this, file);
        photosSelect.setFileZoomCallBack(new PhotosUtil.FileZoomCallBack() {
            @Override
            public void fileZoom(File file) {
                GlideUtil.loadDefaultUrl(SecondActivity.this, file, mBtn7);
            }
        });
        RecyvlerViewBottomWindowAdapter recyvlerViewBottomWindowAdapter = new RecyvlerViewBottomWindowAdapter(datas, this, com.dejun.commonsdk.R.layout.popup_window_text_item);
        final RecyvlerViewBottomWindow recyvlerViewBottomWindow = new RecyvlerViewBottomWindow(this, RecyvlerViewBottomWindow.INVALID_VALUE, RecyvlerViewBottomWindow.INVALID_VALUE, linearLayoutManager, recyvlerViewBottomWindowAdapter, new RecyvlerViewBottomWindow.OnItemClickListener() {
            @Override
            public void onItemClick(Object o, int position) {
                if (position == 0) {//相册
                    //PhotosUtil.getInstance().uploadAvatarFromAlbumRequest();
                    photosSelect.uploadAvatarFromAlbumRequest();
                } else if (position == 1) {//相机
                    boolean hasCameraPermission = PermissionsUtil.hasCameraPermission(SecondActivity.this, PermissionsUtil.REQUEST_CAMERA);
                    if (hasCameraPermission) {
                        photosSelect.uploadAvatarFromPhotoRequest();
                    }

                }
            }
        });
        recyvlerViewBottomWindow.showPopupBottom();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        photosSelect.onActivityResult(requestCode, resultCode, data);
//        PhotosUtil.getInstance().onActivityResult(requestCode, resultCode, data).setFileZoomCallBack(new PhotosUtil.FileZoomCallBack() {
//            @Override
//            public void fileZoom(File file) {
//                GlideUtil.loadDefaultUrl(SecondActivity.this,file,mBtn7);
//            }
//        });
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(String event){
        Logger.d(event);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
//在界面销毁的地方要解绑
        EventBus.getDefault().unregister(this);
    }
}
