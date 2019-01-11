package com.dejun.commonsdk.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;

/**
 * Author:DoctorWei
 * Time:2018/12/5 18:37
 * Description:
 * email:1348172474@qq.com
 */

public class BaseRvViewHolder extends RecyclerView.ViewHolder {
    public SparseArray<View> arrayViews;
    public Context mContext;
    protected BaseRvViewHolder(Context context,View itemView) {
        super(itemView);
        arrayViews=new SparseArray<View>();
        mContext=context;
    }

    public  <T extends View> T getView(int viewId){
        View view = arrayViews.get(viewId);
        if (view==null){
            view=itemView.findViewById(viewId);
            arrayViews.put(viewId,view);
        }
        return (T)view;
    }
}
