package com.dejun.androidcourse.RetrofitServer;

import com.dejun.androidcourse.mvp.model.res.TodayNewsRes;

import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Author:DoctorWei
 * Time:2018/12/4 17:07
 * Description:
 * email:1348172474@qq.com
 */

public interface BeautyServer {
    @GET
    Observable<TodayNewsRes> getBeauty(@Url String url);
}
