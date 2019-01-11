package com.dejun.commonsdk.wight;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dejun.commonsdk.R;

/**
 * Author:DoctorWei
 * Time:2018/12/18 10:20
 * Description:android自定义标题栏
 * email:1348172474@qq.com
 */

public class CustomActionBar extends RelativeLayout implements View.OnClickListener{
    private TextView mTvTitle;
    private ImageView mIvLeftBack;
    private TextView mTvLeftBack;
    private TextView mTvRightMore;
    private ImageView mIvRightMore;
    public CustomActionBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        setId(R.id.custom_action_bar);
        initViews(context,attrs);
    }

    private void initViews(Context context,AttributeSet attrs) {
        View view= LayoutInflater.from(context).inflate(R.layout.custom_actionbar_layout,this);//需要将布局放到自定义view中否则不显示
        mTvTitle=view.findViewById(R.id.tv_center_actionbar_title);//中间标题
        mIvLeftBack=view.findViewById(R.id.iv_left_actionbar_title_back);//图标返回
        mTvLeftBack=view.findViewById(R.id.tv_left_actionbar_title_back);//文字返回
        mIvRightMore=view.findViewById(R.id.iv_right_actionbar_title_more);//图标更多
        mTvRightMore=view.findViewById(R.id.tv_right_actionbar_title_more);//文字更多
        //获取布局中设置的属性
        initAttrData(context,attrs);
    }

    private void initAttrData(Context context,AttributeSet attrs) {
        TypedArray typedArray=context.obtainStyledAttributes(attrs,R.styleable.CustomActionBar);
        String title = typedArray.getString(R.styleable.CustomActionBar_title);
        int leftIcon= typedArray.getResourceId(R.styleable.CustomActionBar_left_icon,R.drawable.arrow_left_back);
        int rightIcon = typedArray.getResourceId(R.styleable.CustomActionBar_right_icon,R.drawable.arrow_left_back);
        String leftText = typedArray.getString(R.styleable.CustomActionBar_left_text);
        String rightText = typedArray.getString(R.styleable.CustomActionBar_right_text);
        if (!TextUtils.isEmpty(title)) {
            mTvTitle.setVisibility(View.VISIBLE);
            mTvTitle.setText(title);
        }else{
            mTvTitle.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(leftText)) {
            mTvLeftBack.setVisibility(View.VISIBLE);
            mTvLeftBack.setText(leftText);
            mIvLeftBack.setVisibility(View.GONE);
        }else{
            mTvLeftBack.setVisibility(View.GONE);
            mIvLeftBack.setVisibility(View.VISIBLE);
            mIvLeftBack.setImageResource(leftIcon);
        }
        if (!TextUtils.isEmpty(rightText)) {
            mTvRightMore.setVisibility(View.VISIBLE);
            mTvRightMore.setText(rightText);
            mIvRightMore.setVisibility(View.GONE);
        }else{
            mTvRightMore.setVisibility(View.GONE);
            mIvRightMore.setVisibility(View.VISIBLE);
            mIvRightMore.setImageResource(rightIcon);
        }





        mIvLeftBack.setOnClickListener(this);
        mTvLeftBack.setOnClickListener(this);
        mIvRightMore.setOnClickListener(this);
        mTvRightMore.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        if (id == R.id.iv_left_actionbar_title_back) {
            actionBarListener.ivLeftBackClick(id);
        }else if (id == R.id.tv_left_actionbar_title_back){
            actionBarListener.tvLeftBackClick(id);
        }else if (id == R.id.iv_right_actionbar_title_more){
            actionBarListener.ivRightBackClick(id);
        }else if (id == R.id.tv_right_actionbar_title_more){
            actionBarListener.tvRightLeftBackClick(id);
        }
    }
    public CustomActionBar setActionBarListener(ActionBarListener actionBarListener){
        this.actionBarListener=actionBarListener;
        return this;
    }
    public ActionBarListener actionBarListener;
    public abstract static class  ActionBarListener{
        public void ivLeftBackClick(int id){

        }
        public void tvLeftBackClick(int id){

        }
        public void ivRightBackClick(int id){

        }
        public void tvRightLeftBackClick(int id){

        }
    }
}
