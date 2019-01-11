package com.dejun.commonsdk.dialog;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dejun.commonsdk.R;
import com.dejun.commonsdk.adapter.ListDialogAdapter;
import com.dejun.commonsdk.base.BaseRvAdapter;
import com.dejun.commonsdk.wight.ListDividerItemDecoration;

import java.util.ArrayList;

/**
 * Author:DoctorWei
 * Time:2018/12/8 13:57
 * Description:退出dialog
 * email:1348172474@qq.com
 */

public class ListDialog extends DialogFragment{
    public static final String DIALOG_LIST="dialog_list";
    private RecyclerView mRvDialog;

    public static ListDialog creatConfirmDialog(Bundle bundle){
        ListDialog confirmDialog=new ListDialog();
        if (bundle!=null){
            confirmDialog.setArguments(bundle);
        }
        return confirmDialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.list_dialog_layout,null);
        mRvDialog=view.findViewById(R.id.rv_dialog);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Bundle arguments = getArguments();
        ArrayList<String> stringArrayList = arguments.getStringArrayList(DIALOG_LIST);
        if (stringArrayList!=null&&!stringArrayList.isEmpty()){
           //设置数据
            ListDialogAdapter listDialogAdapter=new ListDialogAdapter(stringArrayList,getActivity(),R.layout.list_dialog_item);
            mRvDialog.setLayoutManager(new LinearLayoutManager(getActivity()));
            mRvDialog.setAdapter(listDialogAdapter);
            listDialogAdapter.setOnItemClickListener(new BaseRvAdapter.OnItemClickListener<String>() {
                @Override
                public void onItemClick(String s, int position) {
                    if (onItemClickListener!=null){
                        onItemClickListener.onItemClick(position);
                    }
                    dismiss();
                }
            });
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
    public void setOnClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener=onItemClickListener;
    }
    public OnItemClickListener onItemClickListener;


    public interface OnItemClickListener{
        void onItemClick(int position);
    }
}
