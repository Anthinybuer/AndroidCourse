package com.dejun.commonsdk.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.dejun.commonsdk.R;
import com.dejun.commonsdk.base.BaseRvAdapter;
import com.dejun.commonsdk.base.BaseRvViewHolder;

import java.util.List;

/**
 * Author:DoctorWei
 * Time:2019/1/2 11:14
 * Description:列表dialog适配器
 * email:1348172474@qq.com
 */

public class ListDialogAdapter extends BaseRvAdapter<String>{

    public ListDialogAdapter(List<String> mDatas, Context mContext, int layout) {
        super(mDatas, mContext, layout);
    }

    @Override
    protected void bindDatas(BaseRvViewHolder holder, String s, int position) {
        TextView textView = holder.getView(R.id.tv_list_dialog);
        textView.setText(s);
    }
}
