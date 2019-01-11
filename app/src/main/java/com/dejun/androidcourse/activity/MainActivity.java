package com.dejun.androidcourse.activity;

import android.app.FragmentManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dejun.androidcourse.R;
import com.dejun.androidcourse.extra.Extra;
import com.dejun.androidcourse.fragment.HomeFragment;
import com.dejun.androidcourse.service.ServiceUtil;
import com.dejun.beauty.BeautyFragment;
import com.dejun.beauty.activity.BeautyActivity;
import com.dejun.commonsdk.WebViewActivity;
import com.dejun.commonsdk.base.BaseActivity;
import com.dejun.commonsdk.base.mvp.BasePresenter;
import com.dejun.commonsdk.popWindow.RecyvlerViewBottomWindow;
import com.dejun.commonsdk.popWindow.RecyvlerViewBottomWindowAdapter;
import com.dejun.commonsdk.util.PermissionsUtil;
import com.dejun.function.fragment.FunctionFragment;
import com.dejun.message.fragment.MessageFragment;
import com.dejun.setting.fragment.SettingFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import me.yokeyword.fragmentation.Fragmentation;
import me.yokeyword.fragmentation.SupportFragment;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mAppIvTabMain;
    /**
     * 首页
     */
    private TextView mAppTvTabMain;
    private RelativeLayout mAppRlTabMain;
    private ImageView mAppIvTabBeauty;
    /**
     * 美女
     */
    private TextView mAppTvTabBeauty;
    private RelativeLayout mAppRlTabBeauty;
    private ImageView mAppIvTabFunction;
    /**
     * 功能
     */
    private TextView mAppTvTabFunction;
    private RelativeLayout mAppRlTabFunction;
    private ImageView mAppIvTabVideo;
    /**
     * 消息
     */
    private TextView mAppTvTabVideo;
    private RelativeLayout mAppRlTabVideo;
    private ImageView mAppIvTabSetting;
    /**
     * 我的
     */
    private TextView mAppTvTabSetting;
    private RelativeLayout mAppRlTabSetting;
    private LinearLayout mLlBottom;
    //存储imageView和TextView的集合
    ImageView[] imageViews = new ImageView[5];
    TextView[] textViews = new TextView[5];

    @Override
    public BasePresenter createPresenter() {
        return null;
    }



    @Override
    public void initData(Bundle savedInstanceState) {
        ServiceUtil.startService(this);//启动服务保活手机
        //WebViewActivity.startWenViewActivity(this,"");
        if (savedInstanceState == null) {
            loadRootFragment(R.id.fl_container, HomeFragment.newInstance()); //activity初始加载HomeFragment
        }
    }

    @Override
    public void initView() {
        mAppIvTabMain = (ImageView) findViewById(R.id.app_iv_tab_main);
        mAppTvTabMain = (TextView) findViewById(R.id.app_tv_tab_main);
        imageViews[0] = mAppIvTabMain;
        textViews[0] = mAppTvTabMain;
        mAppRlTabMain = (RelativeLayout) findViewById(R.id.app_rl_tab_main);
        mAppRlTabMain.setOnClickListener(this);
        mAppIvTabBeauty = (ImageView) findViewById(R.id.app_iv_tab_beauty);
        mAppTvTabBeauty = (TextView) findViewById(R.id.app_tv_tab_beauty);
        imageViews[1] = mAppIvTabBeauty;
        textViews[1] = mAppTvTabBeauty;
        mAppRlTabBeauty = (RelativeLayout) findViewById(R.id.app_rl_tab_beauty);
        mAppRlTabBeauty.setOnClickListener(this);
        mAppIvTabFunction = (ImageView) findViewById(R.id.app_iv_tab_function);
        mAppTvTabFunction = (TextView) findViewById(R.id.app_tv_tab_function);
        imageViews[2] = mAppIvTabFunction;
        textViews[2] = mAppTvTabFunction;
        mAppRlTabFunction = (RelativeLayout) findViewById(R.id.app_rl_tab_function);
        mAppRlTabFunction.setOnClickListener(this);
        mAppIvTabVideo = (ImageView) findViewById(R.id.app_iv_tab_video);
        mAppTvTabVideo = (TextView) findViewById(R.id.app_tv_tab_video);
        imageViews[3] = mAppIvTabVideo;
        textViews[3] = mAppTvTabVideo;
        mAppRlTabVideo = (RelativeLayout) findViewById(R.id.app_rl_tab_video);
        mAppRlTabVideo.setOnClickListener(this);
        mAppIvTabSetting = (ImageView) findViewById(R.id.app_iv_tab_setting);
        mAppTvTabSetting = (TextView) findViewById(R.id.app_tv_tab_setting);
        imageViews[4] = mAppIvTabSetting;
        textViews[4] = mAppTvTabSetting;
        mAppRlTabSetting = (RelativeLayout) findViewById(R.id.app_rl_tab_setting);
        mAppRlTabSetting.setOnClickListener(this);
        mLlBottom = (LinearLayout) findViewById(R.id.ll_bottom);

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.app_rl_tab_main:
                HomeFragment homeFragment=findFragment(HomeFragment.class);
                Bundle newBundle=new Bundle();
                newBundle.putString(Extra.FRAGMENT_FROM,"主页来自:"+getTopFragment().getClass());
                start(homeFragment, SupportFragment.SINGLETASK);
                break;
            case R.id.app_rl_tab_beauty:
                BeautyFragment beautyFragment=findFragment(BeautyFragment.class);
                if (beautyFragment==null){
                    popTo(HomeFragment.class, false, new Runnable() {
                        @Override
                        public void run() {
                            start(BeautyFragment.newInstance());
                        }
                    });
                }else{
                    start(beautyFragment,SupportFragment.SINGLETASK);
                }
                break;
            case R.id.app_rl_tab_function:
                FunctionFragment functionFragment=findFragment(FunctionFragment.class);
                if (functionFragment==null){
                    popTo(FunctionFragment.class, false, new Runnable() {
                        @Override
                        public void run() {
                            start(FunctionFragment.newInstance());
                        }
                    });
                }else{
                    start(functionFragment,SupportFragment.SINGLETASK);
                }
                break;
            case R.id.app_rl_tab_video://消息
                MessageFragment messageFragment=findFragment(MessageFragment.class);
                if (messageFragment==null){
                    popTo(MessageFragment.class, false, new Runnable() {
                        @Override
                        public void run() {
                            start(MessageFragment.newInstance());
                        }
                    });
                }else{
                    start(messageFragment,SupportFragment.SINGLETASK);
                }
                break;
            case R.id.app_rl_tab_setting:
                SettingFragment settingFragment=findFragment(SettingFragment.class);
                if (settingFragment==null){
                    popTo(SettingFragment.class, false, new Runnable() {
                        @Override
                        public void run() {
                            start(SettingFragment.newInstance());
                        }
                    });
                }else{
                    start(settingFragment,SettingFragment.SINGLETASK);
                }

                break;
        }
    }
}
