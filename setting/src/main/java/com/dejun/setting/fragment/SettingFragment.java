package com.dejun.setting.fragment;

import android.os.Bundle;
import android.view.View;

import com.dejun.commonsdk.base.BaseFragment;
import com.dejun.commonsdk.base.mvp.BasePresenter;
import com.dejun.setting.R;

/**
 * Author:DoctorWei
 * Time:2018/11/10 14:56
 * Description:
 * email:1348172474@qq.com
 */

public class SettingFragment extends BaseFragment {
    public static SettingFragment newInstance(){
        return new SettingFragment();
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_setting_layout;
    }

    @Override
    protected void init(View view) {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }
}
