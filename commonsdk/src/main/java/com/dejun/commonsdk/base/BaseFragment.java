package com.dejun.commonsdk.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dejun.commonsdk.base.mvp.BasePresenter;
import com.dejun.commonsdk.base.mvp.BaseView;
import com.dejun.commonsdk.dialog.ProgressDialog;
import com.orhanobut.logger.Logger;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * Author:DoctorWei
 * Time:2018/11/3 11:16
 * Description:基类Fragment
 * email:1348172474@qq.com
 */

public abstract class BaseFragment<V extends BaseView,P extends BasePresenter> extends SupportFragment implements BaseView{
    private P mPresenter;
    private ProgressDialog progressDialog;
    public ViewGroup mView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView= (ViewGroup) inflater.inflate(getLayoutId(),container,false);
        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        initPresenter();
        initData(savedInstanceState);
    }
    protected void hasViewAndPresenter(ExcutePresenter<P> excutePresenter){
        if (isHasViewAndPresenter()){
            excutePresenter.run(mPresenter);
        }else {
            Logger.d("Fragment的View和Presenter未绑定");
        }
    }
    protected boolean isHasViewAndPresenter(){
        if (mPresenter!=null&&mPresenter.isAttachView()){
            return true;
        }else{
            return false;
        }
    }
    protected interface ExcutePresenter<P>{
        void run(P presenter);
    }
    private void initPresenter() {
        mPresenter =createPresenter();
        if (mPresenter !=null){
            mPresenter.attachView(this);
        }
    }

    protected abstract P createPresenter();

    protected abstract int getLayoutId();

    protected abstract void init(View view);

    protected abstract void initData(Bundle savedInstanceState);
    public void toastMsg(String msg){
        Toast.makeText(_mActivity,msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mPresenter !=null){
            mPresenter.dettachView();
        }
    }
    public void showPb(String content,boolean isShow){
        if (isShow) {
            if (progressDialog!=null){
                progressDialog.dismiss();
                progressDialog=null;
            }
            progressDialog =ProgressDialog.createPbDialog(content);
            progressDialog.show(_mActivity.getSupportFragmentManager(), getClass().getSimpleName());
        }else{
            if (progressDialog!=null)
                progressDialog.dismiss();
        }
    }
}
