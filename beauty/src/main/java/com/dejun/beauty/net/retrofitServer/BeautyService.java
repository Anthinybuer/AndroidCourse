package com.dejun.beauty.net.retrofitServer;

import com.dejun.commonsdk.db.model.BeautyRes;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Author:DoctorWei
 * Time:2018/12/5 20:36
 * Description:
 * email:1348172474@qq.com
 */

public interface BeautyService {
    @GET
    Observable<BeautyRes> getBeauty(@Url String url);
    @Streaming
    @GET
    Observable<ResponseBody> downLoadFile(@Url String url);
}
