package com.dejun.function.mvp.view;
import com.dejun.commonsdk.base.mvp.BaseView;
import com.dejun.commonsdk.db.model.DataBean;
import com.dejun.commonsdk.db.model.VideoRes;

import java.util.List;

/**
 * Author:DoctorWei
 * Time:2018/12/4 20:58
 * Description:
 * email:1348172474@qq.com
 */

public interface FunctionView extends BaseView {
    void getFunctionSuccess(List<DataBean> dataBeans, int scrollType);
    void getFunctionFailed();
}
