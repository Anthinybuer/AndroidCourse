package com.dejun.beauty.mvp.view;

import com.dejun.commonsdk.db.model.BeautyRes;
import com.dejun.commonsdk.base.mvp.BaseView;

/**
 * Author:DoctorWei
 * Time:2018/12/4 20:58
 * Description:
 * email:1348172474@qq.com
 */

public interface BeautyView extends BaseView {
    void getBeautySuccess(BeautyRes beautyRes);
    void getBeautyFailed();
}
