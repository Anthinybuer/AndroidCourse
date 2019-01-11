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

import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;

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
        VideoView videoView = holder.getView(R.id.vv_function);
        if (TextUtils.equals(type,"image")){
            imageView.setVisibility(View.VISIBLE);
            videoView.setVisibility(View.GONE);
            GlideUtil.loadDefaultUrl(mContext,dataBean.getImage(),imageView);
        }else if (TextUtils.equals(type,"video")){
            imageView.setVisibility(View.GONE);
            videoView.setVisibility(View.VISIBLE);
            videoView.setVideoURI(Uri.parse(dataBean.getVideo()));
            //设置视频控制器，当然这个可以自定义控制器，我这里使用自带想控制器
            videoView.setMediaController(new MediaController(mContext));
            //获取焦点
            videoView.requestFocus();
//            //设置状态信息监听
//            view.setOnInfoListener(this);
//            //设置数据更新监听
//            view.setOnBufferingUpdateListener(this);
            //进行异步的准备
            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    //播放速率的调节，需要Vitamio 4.0 以上
                    mediaPlayer.setPlaybackSpeed(1.0f);
                }
            });
        }

    }
}
