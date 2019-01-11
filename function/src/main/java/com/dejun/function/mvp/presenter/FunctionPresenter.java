package com.dejun.function.mvp.presenter;

import com.dejun.commonsdk.base.mvp.BasePresenter;
import com.dejun.commonsdk.db.DaoManager;
import com.dejun.commonsdk.db.model.DataBean;
import com.dejun.commonsdk.db.model.VideoRes;
import com.dejun.commonsdk.net.RetrofitClient;
import com.dejun.function.FunctionClient;
import com.dejun.function.mvp.view.FunctionView;
import com.dejun.function.net.retrofitServer.FunctionService;

import java.util.HashMap;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Author:DoctorWei
 * Time:2018/12/4 20:59
 * Description:
 * email:1348172474@qq.com
 */

public class FunctionPresenter extends BasePresenter<FunctionView> {
    public void getFunctionRes(String type, String page, final int scollType) {
        HashMap<String, String> functions = new HashMap<>();
        functions.put("type", type);
        functions.put("page", page);
        RetrofitClient.getInstance().getRetrofit(FunctionClient.getFunctionApi().function).create(FunctionService.class).getFunction(functions)
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.io())
                .doOnNext(new Action1<VideoRes>() {
                    @Override
                    public void call(VideoRes videoRes) {

                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<VideoRes>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(final VideoRes videoRes) {
                        hasView(new ExcuteView<FunctionView>() {
                            @Override
                            public void run(FunctionView view) {
                                view.getFunctionSuccess(videoRes.getData(), scollType);
                            }
                        });
                    }
                });
    }

//    public List<DataBean> getFunctionResFromDb() {
//        final List<DataBean> dataBeans = DaoManager.getInstance().getDaoSession().loadAll(DataBean.class);
//        return dataBeans;
//
//    }
}

