package com.dejun.commonsdk.popWindow;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.dejun.commonsdk.R;
import com.dejun.commonsdk.base.BaseRvAdapter;
import com.dejun.commonsdk.base.BaseRvViewHolder;

import java.util.List;

/**
 * Author:DoctorWei
 * Time:2018/12/13 14:37
 * Description:
 * email:1348172474@qq.com
 */

public class RecyvlerViewBottomWindowAdapter extends BaseRvAdapter<String>{
    public RecyvlerViewBottomWindowAdapter(List<String> mDatas, Context mContext, int layout) {
        super(mDatas, mContext, layout);
    }

    @Override
    protected void bindDatas(BaseRvViewHolder holder, String text, int position) {
        TextView view = holder.getView(R.id.tv_bottom_popup_window);
        view.setText(text);
    }
}
