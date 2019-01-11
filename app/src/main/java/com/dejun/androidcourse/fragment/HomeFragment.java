package com.dejun.androidcourse.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.dejun.androidcourse.R;
import com.dejun.androidcourse.RetrofitServer.BeautyServer;
import com.dejun.androidcourse.mvp.model.res.TodayNewsRes;
import com.dejun.commonsdk.base.BaseFragment;
import com.dejun.commonsdk.base.mvp.BasePresenter;
import com.dejun.commonsdk.net.RetrofitClient;
import com.orhanobut.logger.Logger;

import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Author:DoctorWei
 * Time:2018/11/10 11:28
 * Description:
 * email:1348172474@qq.com
 */

public class HomeFragment extends BaseFragment {
    public static final String BASE_URL = "https://gank.io/api/data/";

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.homefragment_layout;
    }

    @Override
    protected void init(View view) {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        RetrofitClient.getInstance().getRetrofit(BASE_URL).create(BeautyServer.class).getBeauty("福利/10/1")
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.io())
                .doOnNext(new Action1<TodayNewsRes>() {
                    @Override
                    public void call(TodayNewsRes todayNewsRes) {
                        Logger.d(todayNewsRes);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<TodayNewsRes>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(TodayNewsRes todayNewsRes) {
                        toastMsg(todayNewsRes.toString());
                        //Logger.d(todayNewsRes.toString());
                    }
                });

    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        return new DefaultHorizontalAnimator();
    }
}
