package com.dejun.function.adapter;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.dejun.commonsdk.base.BaseRvAdapter;
import com.dejun.commonsdk.base.BaseRvViewHolder;
import com.dejun.commonsdk.db.model.DataBean;
import com.dejun.commonsdk.util.GlideUtil;
import com.dejun.function.R;

import java.util.List;

/**
 * Author:DoctorWei
 * Time:2018/12/11 10:30
 * Description:
 * email:1348172474@qq.com
 */

public class FunctionAdapter extends BaseRvAdapter<DataBean> {
    public FunctionAdapter(List mDatas, Context mContext, int layout) {
        super(mDatas, mContext, layout);
    }


    @Override
    protected void bindDatas(BaseRvViewHolder holder, DataBean dataBean, int position) {
        String type = dataBean.getType();
        ImageView imageView = holder.getView(R.id.iv_function);
        if (TextUtils.equals(type,"image")){
            imageView.setVisibility(View.VISIBLE);

            GlideUtil.loadDefaultUrl(mContext,dataBean.getImage(),imageView);
        }else if (TextUtils.equals(type,"video")){
            imageView.setVisibility(View.GONE);
        }

    }
}
