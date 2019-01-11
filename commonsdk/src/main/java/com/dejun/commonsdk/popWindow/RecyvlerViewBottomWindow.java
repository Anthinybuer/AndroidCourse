package com.dejun.commonsdk.popWindow;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.dejun.commonsdk.R;
import com.dejun.commonsdk.base.BaseRvAdapter;

import java.util.List;

/**
 * Author:DoctorWei
 * Time:2018/12/13 11:08
 * Description:列表显示的PopupWindow
 * email:1348172474@qq.com
 */

public class RecyvlerViewBottomWindow<T> extends PopupWindow {
    public static final int INVALID_VALUE=-1;
    private Activity mActivity;
    public RecyvlerViewBottomWindow(Activity mActivity, int width, int height, RecyclerView.LayoutManager layoutManager, BaseRvAdapter adapter, final OnItemClickListener onItemClickListener) {
        this.mActivity = mActivity;
        LinearLayout view= (LinearLayout) LayoutInflater.from(mActivity).inflate(R.layout.popup_window_layout,null);
        //动态添加recyclerView
        RecyclerView recyclerView=new RecyclerView(mActivity);
        RelativeLayout.LayoutParams layoutParams=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        recyclerView.setLayoutParams(layoutParams);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setBackgroundColor(mActivity.getResources().getColor(R.color.cardview_dark_background));
        view.addView(recyclerView);
        setContentView(view);
        setFocusable(true);//设置焦点
        setTouchable(true);//设置可触摸
        setOutsideTouchable(true);//设置点击外部消失
        setBackgroundDrawable(new ColorDrawable(0x00000000));//点击返回键消失popupWindow 上面三个也得设置才有效果
        setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {//销毁PopupWindow监听

            }
        });
        if (width>0) {
            setWidth(width);
        }else{
            setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        }
        if (height>0) {
            setHeight(height);
        }else{
            setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        adapter.setOnItemClickListener(new BaseRvAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Object o, int position) {
                if (onItemClickListener!=null) {
                    onItemClickListener.onItemClick(o, position);
                    dismiss();
                }
            }
        });

    }

    public void showPopupBottom(){
        showAtLocation(mActivity.getWindow().getDecorView(), Gravity.BOTTOM,0,0);
        showAlpha(0.5f);
    }
    public void showAsViewBottom(View view,int xoff,int yoff){
        showAsDropDown(view,0,0);
        showAlpha(0.5f);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        showAlpha(1f);
    }

    /**
     * 设置窗口透明度
     * @param alpha
     */
    private void showAlpha(float alpha) {
        WindowManager.LayoutParams layoutParams=mActivity.getWindow().getAttributes();
        layoutParams.alpha=alpha;
        mActivity.getWindow().setAttributes(layoutParams);
    }

    public OnItemClickListener<T> onItemClickListener;
    public interface OnItemClickListener<T>{
        void onItemClick(T t,int position);
    }
}
