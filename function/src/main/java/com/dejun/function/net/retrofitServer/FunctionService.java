package com.dejun.function.net.retrofitServer;

import com.dejun.commonsdk.db.model.VideoRes;

import java.util.HashMap;

import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Author:DoctorWei
 * Time:2018/12/5 20:36
 * Description:
 * email:1348172474@qq.com
 */

public interface FunctionService {
    @GET("satinGodApi")
    Observable<VideoRes> getFunction(@QueryMap HashMap<String,String> function);
}
