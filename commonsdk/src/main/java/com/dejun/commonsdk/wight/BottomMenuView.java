package com.dejun.commonsdk.wight;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dejun.commonsdk.R;
import com.dejun.commonsdk.util.DensitySizeUtil;

/**
 * Author:DoctorWei
 * Time:2018/12/29 9:11
 * Description:底部菜单选择项
 * email:1348172474@qq.com
 */

public class BottomMenuView extends LinearLayout implements View.OnClickListener{
    private ImageView mIvFirst;
    private TextView mTvFirst;
    private ImageView mIvSecond;
    private TextView mTvSecond;
    private ImageView mIvThird;
    private TextView mTvThird;
    private ImageView mIvFourth;
    private TextView mTvFourth;
    private ImageView mIvFifth;
    private TextView mTvFifth;
    private RelativeLayout mRlFirst;
    private RelativeLayout mRlSecond;
    private RelativeLayout mRlThird;
    private RelativeLayout mRlFourth;
    private RelativeLayout mRlFifth;
    public BottomMenuView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        //设置中间按钮不受父容器控制
        setClipChildren(false);
        initViews(context,attrs);
    }

    private void initViews(Context context, AttributeSet attrs) {
        View view= LayoutInflater.from(context).inflate(R.layout.bottom_menu_layout,this);
        TypedArray typedArray=context.obtainStyledAttributes(attrs,R.styleable.BottomMenuView);
        int firstIcon=typedArray.getResourceId(R.styleable.BottomMenuView_first_drawable,R.drawable.tab_better_pressed);
        int secondIcon=typedArray.getResourceId(R.styleable.BottomMenuView_second_drawable,R.drawable.tab_better_pressed);
        int thirdIcon=typedArray.getResourceId(R.styleable.BottomMenuView_third_drawable,R.drawable.tab_better_pressed);
        int fourthIcon=typedArray.getResourceId(R.styleable.BottomMenuView_fourth_drawable,R.drawable.tab_better_pressed);
        int fifthIcon=typedArray.getResourceId(R.styleable.BottomMenuView_fifth_drawable,R.drawable.tab_better_pressed);
        String firstText=typedArray.getString(R.styleable.BottomMenuView_first_text);
        String secondText=typedArray.getString(R.styleable.BottomMenuView_second_text);
        String thirdText=typedArray.getString(R.styleable.BottomMenuView_third_text);
        String fourthText=typedArray.getString(R.styleable.BottomMenuView_fourth_text);
        String fifthText=typedArray.getString(R.styleable.BottomMenuView_fifth_text);
        int textColor=typedArray.getColor(R.styleable.BottomMenuView_menu_text_color,context.getResources().getColor(R.color.cardview_dark_background));
        float textSize=typedArray.getDimension(R.styleable.BottomMenuView_menu_text_size,0.0f);


        mIvFirst=view.findViewById(R.id.iv_bottom_menu_first);
        mTvFirst=view.findViewById(R.id.tv_bottom_menu_first);
        mIvSecond=view.findViewById(R.id.iv_bottom_menu_second);
        mTvSecond=view.findViewById(R.id.tv_bottom_menu_second);
        mIvThird=view.findViewById(R.id.iv_bottom_menu_third);
        mTvThird=view.findViewById(R.id.tv_bottom_menu_third);
        mIvFourth=view.findViewById(R.id.iv_bottom_menu_fourth);
        mTvFourth=view.findViewById(R.id.tv_bottom_menu_fourth);
        mIvFifth=view.findViewById(R.id.iv_bottom_menu_fifth);
        mTvFifth=view.findViewById(R.id.tv_bottom_menu_fifth);
        mRlFirst=view.findViewById(R.id.rl_bottom_menu_first);
        mRlSecond=view.findViewById(R.id.rl_bottom_menu_second);
        mRlThird=view.findViewById(R.id.rl_bottom_menu_third);
        mRlFourth=view.findViewById(R.id.rl_bottom_menu_fourth);
        mRlFifth=view.findViewById(R.id.rl_bottom_menu_fifth);
        setRes(typedArray, firstIcon, secondIcon, thirdIcon, fourthIcon, fifthIcon, firstText, secondText, thirdText, fourthText, fifthText);
        setText(context,typedArray, textColor, textSize);
        setListeners();
    }



    private void setRes(TypedArray typedArray, int firstIcon, int secondIcon, int thirdIcon, int fourthIcon, int fifthIcon, String firstText, String secondText, String thirdText, String fourthText, String fifthText) {
        if (typedArray.hasValue(R.styleable.BottomMenuView_first_drawable)){
            mIvFirst.setImageResource(firstIcon);
        }
        if (typedArray.hasValue(R.styleable.BottomMenuView_first_text)){
            mTvFirst.setText(firstText);
        }
        if (typedArray.hasValue(R.styleable.BottomMenuView_second_drawable)){
            mIvSecond.setImageResource(secondIcon);
        }
        if (typedArray.hasValue(R.styleable.BottomMenuView_second_text)){
            mTvSecond.setText(secondText);
        }
        if (typedArray.hasValue(R.styleable.BottomMenuView_third_drawable)){
            mIvThird.setImageResource(thirdIcon);
        }
        if (typedArray.hasValue(R.styleable.BottomMenuView_third_text)){
            mTvThird.setText(thirdText);
        }
        if (typedArray.hasValue(R.styleable.BottomMenuView_fourth_drawable)){
            mIvFourth.setImageResource(fourthIcon);
        }
        if (typedArray.hasValue(R.styleable.BottomMenuView_fourth_text)){
            mTvFourth.setText(fourthText);
        }
        if (typedArray.hasValue(R.styleable.BottomMenuView_fifth_drawable)){
            mIvFifth.setImageResource(fifthIcon);
        }
        if (typedArray.hasValue(R.styleable.BottomMenuView_fifth_text)){
            mTvFifth.setText(fifthText);
        }
        if (typedArray.hasValue(R.styleable.BottomMenuView_menu_center_visible)){
            boolean centerVisibale=typedArray.getBoolean(R.styleable.BottomMenuView_menu_center_visible,false);
            if (centerVisibale) {
                mRlThird.setVisibility(View.VISIBLE);
            }else{
                mRlThird.setVisibility(View.GONE);
            }
        }
    }

    private void setText(Context context,TypedArray typedArray, int textColor, float textSize) {
        if (typedArray.hasValue(R.styleable.BottomMenuView_menu_text_color)){
            mTvFirst.setTextColor(textColor);
            mTvSecond.setTextColor(textColor);
            mTvThird.setTextColor(textColor);
            mTvFourth.setTextColor(textColor);
            mTvFifth.setTextColor(textColor);
        }
        if (typedArray.hasValue(R.styleable.BottomMenuView_menu_text_size)){
            mTvFirst.setTextSize(DensitySizeUtil.px2sp(context,textSize));
            mTvSecond.setTextSize(DensitySizeUtil.px2sp(context,textSize));
            mTvThird.setTextSize(DensitySizeUtil.px2sp(context,textSize));
            mTvFourth.setTextSize(DensitySizeUtil.px2sp(context,textSize));
            mTvFifth.setTextSize(DensitySizeUtil.px2sp(context,textSize));
        }
    }
    private void setListeners() {
        mRlFirst.setOnClickListener(this);
        mRlSecond.setOnClickListener(this);
        mRlThird.setOnClickListener(this);
        mRlFourth.setOnClickListener(this);
        mRlFifth.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (onClickListener!=null) {
            if (id == R.id.rl_bottom_menu_first) {
                onClickListener.firstClick();
            } else if (id == R.id.rl_bottom_menu_second) {
                onClickListener.secondClick();
            } else if (id == R.id.rl_bottom_menu_third) {
                onClickListener.thirdClick();
            } else if (id == R.id.rl_bottom_menu_fourth) {
                onClickListener.fourthClick();
            } else if (id == R.id.rl_bottom_menu_fifth) {
                onClickListener.fifthClick();
            }
        }
    }
    public OnClickListener onClickListener;
    public void setOnClickListener(OnClickListener onClickListener){
         this.onClickListener=onClickListener;
    }
    public interface OnClickListener{
        void firstClick();
        void secondClick();
        void thirdClick();
        void fourthClick();
        void fifthClick();
    }
}
