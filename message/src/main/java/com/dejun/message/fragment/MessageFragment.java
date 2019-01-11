package com.dejun.message.fragment;

import android.os.Bundle;
import android.view.View;

import com.dejun.commonsdk.base.BaseFragment;
import com.dejun.commonsdk.base.mvp.BasePresenter;
import com.dejun.message.R;

/**
 * Author:DoctorWei
 * Time:2018/11/10 14:58
 * Description:
 * email:1348172474@qq.com
 */

public class MessageFragment extends BaseFragment {
    public static MessageFragment newInstance(){
        return new MessageFragment();
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_message_layout;
    }

    @Override
    protected void init(View view) {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }
}
