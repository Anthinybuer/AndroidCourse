package com.dejun.beauty.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dejun.beauty.R;
import com.dejun.commonsdk.db.model.ResultsBean;
import com.dejun.commonsdk.util.ScreenUtil;

import java.util.List;

/**
 * Author:DoctorWei
 * Time:2018/12/5 18:34
 * Description:
 * email:1348172474@qq.com
 */

public class BeautyAdapter extends RecyclerView.Adapter<BeautyAdapter.BeautyViewHolder> {
    private List<ResultsBean> mDatas;
    private Context mContext;
    private int layout;
    public BeautyAdapter(List<ResultsBean> mDatas, Context mContext, int layout) {
        this.mDatas = mDatas;
        this.mContext = mContext;
        this.layout=layout;
    }
    @NonNull
    @Override
    public BeautyAdapter.BeautyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflateView = LayoutInflater.from(mContext).inflate(layout, parent, false);
        BeautyAdapter.BeautyViewHolder beautyViewHolder=new BeautyAdapter.BeautyViewHolder(inflateView);
        return beautyViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BeautyAdapter.BeautyViewHolder holder, final int position) {
        String url = mDatas.get(position).getUrl();
        RequestOptions requestOptions=new RequestOptions();
        requestOptions.placeholder(R.drawable.social_blacklist_default_bg);
        requestOptions.error(R.drawable.social_blacklist_default_bg);
//        int scaleHeight=0;
//        int ScreenHeight=ScreenUtil.getScreenHeight(mContext);
//        if (position%2==0) {
//            scaleHeight=ScreenHeight/2;
//        }else{
//            scaleHeight=ScreenHeight/3;
//        }
//        RelativeLayout.LayoutParams layoutParams=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,scaleHeight );
//        holder.imageView.setLayoutParams(layoutParams);
        Glide.with(mContext).load(url).apply(requestOptions).into(holder.imageView);
    }

    public List<ResultsBean>  getItems() {
        return mDatas;
    }

    public interface DownLoadListener{
        void downLoad(int position);
    }
    private DownLoadListener downLoadListener;
    public void setDownLoadListener(DownLoadListener downLoadListener){
        this.downLoadListener=downLoadListener;
    }
    @Override
    public int getItemCount() {
        return mDatas.size();
    }
    public static class BeautyViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;
        public BeautyViewHolder(View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.social_chat_bg_iv);
        }
    }
}
