package com.dejun.commonsdk.util;

import android.support.v7.widget.RecyclerView;

/**
 * Author:DoctorWei
 * Time:2018/12/17 20:22
 * Description:
 * email:1348172474@qq.com
 */

public class RVMoveToPosition {
    //目标项是否在最后一个可见项之后
    private boolean mShouldScroll;
    //记录目标项位置
    private int position;
    private RecyclerView mRecyclerView;
    public RVMoveToPosition( RecyclerView mRecyclerView, final int position) {
        this.mRecyclerView=mRecyclerView;
        this.position = position;
    }
    public static RVMoveToPosition createRVMoveToPosition(RecyclerView mRecyclerView, final int position){
        return new RVMoveToPosition(mRecyclerView,position);
    }
    /**
     * 滑动到指定位置
     */

    public void smoothMoveToPosition() {
        // 第一个可见位置
        int firstItem = mRecyclerView.getChildLayoutPosition(mRecyclerView.getChildAt(0));
        // 最后一个可见位置
        int lastItem = mRecyclerView.getChildLayoutPosition(mRecyclerView.getChildAt(mRecyclerView.getChildCount() - 1));
        if (position < firstItem) {
            // 第一种可能:跳转位置在第一个可见位置之前
            mRecyclerView.smoothScrollToPosition(position);
        } else if (position <= lastItem) {
            // 第二种可能:跳转位置在第一个可见位置之后
            int movePosition = position - firstItem;
            if (movePosition >= 0 && movePosition < mRecyclerView.getChildCount()) {
                int top = mRecyclerView.getChildAt(movePosition).getTop();
                mRecyclerView.smoothScrollBy(0, top);
            }
        } else {
            // 第三种可能:跳转位置在最后可见项之后
            mRecyclerView.smoothScrollToPosition(position);

            mShouldScroll = true;
        }
    }
     public void setListener( final onScrollStateChanged onScrollStateChanged){
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (onScrollStateChanged!=null){
                    onScrollStateChanged.onScrollStateChanged(recyclerView, newState);
                }
                if (mShouldScroll&& RecyclerView.SCROLL_STATE_IDLE == newState) {
                    mShouldScroll = false;
                    smoothMoveToPosition();
                }
            }
        });
    }
    private onScrollStateChanged onScrollStateChanged;
    public interface onScrollStateChanged{
        void onScrollStateChanged(RecyclerView recyclerView, int newState);
    }

}
