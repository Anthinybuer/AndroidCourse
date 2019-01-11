package com.dejun.commonsdk.dialog;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.dejun.commonsdk.R;

/**
 * Author:DoctorWei
 * Time:2018/12/8 13:58
 * Description:圆形进度
 * email:1348172474@qq.com
 */

public class ProgressDialog extends DialogFragment {
    public static final String EXTRA = "extraContent";
    public TextView mTvContent;
    public static ProgressDialog createPbDialog(String content) {
        ProgressDialog progressDialog = new ProgressDialog();
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA, content);
        progressDialog.setArguments(bundle);
        return progressDialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        getDialog().setCanceledOnTouchOutside(false);
        setCancelable(false);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable());
        View dialogView = inflater.inflate(R.layout.progress_dialog_layout, null);
        mTvContent=dialogView.findViewById(R.id.commonsdk_pb_tv);
        return dialogView;
    }

    @Override
    public void onStart() {
        super.onStart();
        Bundle arguments = getArguments();
        String content = (String) arguments.get(EXTRA);
        if (mTvContent!=null){
            if (!TextUtils.isEmpty(content)){
                mTvContent.setVisibility(View.VISIBLE);
                mTvContent.setText(content);
            }else{
                mTvContent.setVisibility(View.GONE);
            }
        }
    }
}



