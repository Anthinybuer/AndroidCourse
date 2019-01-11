package com.dejun.commonsdk.base;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Author:DoctorWei
 * Time:2018/12/5 18:34
 * Description:
 * email:1348172474@qq.com
 */

public abstract class BaseRvAdapter<T> extends RecyclerView.Adapter<BaseRvViewHolder> {
    private List<T> mDatas;
    protected Context mContext;
    private int layout;
    public BaseRvAdapter(List<T> mDatas, Context mContext,int layout) {
        if (mDatas!=null) {
            this.mDatas = mDatas;
        }else{
            this.mDatas=new ArrayList<>();
        }
        this.mContext = mContext;
        this.layout=layout;
    }

    @NonNull
    @Override
    public BaseRvViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflateView = LayoutInflater.from(mContext).inflate(layout, parent, false);
        BaseRvViewHolder normalRvViewHolder=new BaseRvViewHolder(mContext,inflateView);
        return normalRvViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseRvViewHolder holder, final int position) {
        setListener(holder, position);
        bindDatas(holder,mDatas.get(position),position);
    }

    private void setListener(@NonNull BaseRvViewHolder holder, final int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(mDatas.get(position),position);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                onItemLongClickListener.onItemLongClick(position);
                return true;
            }
        });
    }

    protected abstract void bindDatas(BaseRvViewHolder holder, T t,int position);
    @Override
    public int getItemCount() {
        return mDatas.size();
    }
    public List<T> getItems(){
        return mDatas;
    }
    public void refreshDatas(List<T> refreshDatas){
        if (mDatas!=null){
            mDatas.clear();
            mDatas.addAll(refreshDatas);
            notifyDataSetChanged();
        }
    }
    public void addDatas(List<T> loadDatas){
        if (mDatas!=null){
            mDatas.addAll(loadDatas);
            notifyDataSetChanged();
        }
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener=onItemClickListener;
    }
    public OnItemClickListener<T> onItemClickListener;
    public interface OnItemClickListener<T>{
        void onItemClick(T t,int position);
    }
    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener){
        this.onItemLongClickListener=onItemLongClickListener;
    }
    public OnItemLongClickListener onItemLongClickListener;
    public interface OnItemLongClickListener{
        void onItemLongClick(int position);
    }
}
