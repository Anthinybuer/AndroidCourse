package com.dejun.beauty.mvp.presenter;

import com.dejun.beauty.BeautyClient;
import com.dejun.commonsdk.db.model.BeautyRes;
import com.dejun.beauty.mvp.view.BeautyView;
import com.dejun.beauty.net.retrofitServer.BeautyService;
import com.dejun.commonsdk.base.mvp.BasePresenter;
import com.dejun.commonsdk.net.RetrofitClient;
import com.dejun.commonsdk.util.FileUtil;

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

public class BeautyPresenter extends BasePresenter<BeautyView> {
    public void getbeautyRes() {
        String baseUrl = BeautyClient.getBeautyApi().baseUrl;
        String beautyGirl = BeautyClient.getBeautyApi().beautyGirl;
        RetrofitClient.getInstance().getRetrofit(baseUrl).create(BeautyService.class).getBeauty(beautyGirl)
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.io())
                .doOnNext(new Action1<BeautyRes>() {
                    @Override
                    public void call(BeautyRes beautyRes) {

                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BeautyRes>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(final BeautyRes beautyRes) {
                        hasView(new ExcuteView<BeautyView>() {
                            @Override
                            public void run(BeautyView view) {
                                view.getBeautySuccess(beautyRes);
                            }
                        });
                    }
                });

    }

    private long totalLength = 0;

    /**
     * 文件下载
     */
    public void downLoadFile(String url, final FileUtil.CopyListener copyListener) {

//        RetrofitClient.getInstance().getRetrofit().create(BeautyService.class).downLoadFile(url)
//                .subscribeOn(Schedulers.io())
//                .unsubscribeOn(Schedulers.io())
//                .map(new Func1<ResponseBody, InputStream>() {
//                    @Override
//                    public InputStream call(ResponseBody responseBody) {
//                        totalLength = responseBody.contentLength();
//                        return responseBody.byteStream();
//                    }
//                }).observeOn(Schedulers.computation())
//                .doOnNext(new Action1<InputStream>() {
//
//                    @Override
//                    public void call(InputStream inputStream) {
//                        FileUtil.copyFile(AppCache.getAppCachePicDirectory(), System.currentTimeMillis() + "", inputStream, totalLength, copyListener);
//                    }
//
//                })
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeOn()
    }
}

