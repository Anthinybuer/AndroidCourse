package com.dejun.beauty.activity;

import android.os.Bundle;

import com.dejun.beauty.R;
import com.dejun.commonsdk.db.model.BeautyRes;
import com.dejun.beauty.mvp.presenter.BeautyPresenter;
import com.dejun.beauty.mvp.view.BeautyView;
import com.dejun.commonsdk.base.BaseActivity;

public class BeautyActivity extends BaseActivity<BeautyView,BeautyPresenter> implements BeautyView{


    @Override
    protected BeautyPresenter createPresenter() {
        return new BeautyPresenter();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        hasViewAndPresenter(new ExcutePresenter<BeautyPresenter>() {
            @Override
            public void excute(BeautyPresenter presenter) {
                presenter.getbeautyRes();
            }
        });

    }

    @Override
    protected void initView() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_beauty;
    }


    @Override
    public void getBeautySuccess(BeautyRes beautyRes) {

    }

    @Override
    public void getBeautyFailed() {

    }
}
