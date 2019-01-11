package com.dejun.commonsdk.dialog;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dejun.commonsdk.R;

/**
 * Author:DoctorWei
 * Time:2018/12/8 13:57
 * Description:退出dialog
 * email:1348172474@qq.com
 */

public class ConfirmDialog extends DialogFragment implements View.OnClickListener{
    public static final String CONFIRM_TITLE="confirm_title";
    public static final String CONFIRM_CONTENT="confirm_content";
    public static final String CONFIRM_CONFIRM="confirm_confirm";
    public static final String CONFIRM_CANCLE="confirm_cancle";
    private TextView mTvTitle;
    private TextView mTvContent;
    private TextView mTvConfirm;
    private TextView mTvCancle;
    public static ConfirmDialog creatConfirmDialog(Activity activity,Bundle bundle){
        ConfirmDialog confirmDialog=new ConfirmDialog();
        if (bundle!=null){
            confirmDialog.setArguments(bundle);
        }
        return confirmDialog;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.confirm_dialog_layout,null);
        mTvTitle=view.findViewById(R.id.commonsdk_dialog_title);
        mTvContent=view.findViewById(R.id.commonsdk_dialog_content);
        mTvCancle=view.findViewById(R.id.commonsdk_dialog_cancel);
        mTvConfirm=view.findViewById(R.id.commonsdk_dialog_confirm);
        mTvConfirm.setOnClickListener(this);
        mTvCancle.setOnClickListener(this);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Bundle arguments = getArguments();
        String confirm_title = arguments.getString(CONFIRM_TITLE);
        if (!TextUtils.isEmpty(confirm_title)){
            mTvTitle.setText(confirm_title);
        }
        String confirm_content = arguments.getString(CONFIRM_CONTENT);
        if (!TextUtils.isEmpty(confirm_content)){
            mTvContent.setVisibility(View.VISIBLE);
            mTvContent.setText(confirm_content);
        }else{
            mTvContent.setVisibility(View.GONE);
        }
        String confirm_confirm = arguments.getString(CONFIRM_CONFIRM);
        if (!TextUtils.isEmpty(confirm_confirm)){
            mTvConfirm.setText(confirm_confirm);
        }
        String confirm_cancle = arguments.getString(CONFIRM_CANCLE);
        if (!TextUtils.isEmpty(confirm_cancle)){
            mTvCancle.setText(confirm_cancle);
        }
    }
    @Override
    public void show(FragmentManager manager, String tag) {//解决dialog显示程序退到后台的崩溃
        try {
            FragmentTransaction ft = manager.beginTransaction();
            ft.add(this, tag);
            ft.commitAllowingStateLoss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void dismiss() {//解决activity被销毁dialog的状态异常
        super.dismissAllowingStateLoss();
    }
    public void setOnClickListener(OnClickListener onClickListener){
        this.onClickListener=onClickListener;
    }
    public OnClickListener onClickListener;

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id==R.id.commonsdk_dialog_confirm)
            onClickListener.confirmClick();
        else if(id==R.id.commonsdk_dialog_cancel)
            onClickListener.cancleClick();
        dismiss();

    }

    public interface OnClickListener{
        void confirmClick();
        void cancleClick();
    }
}
