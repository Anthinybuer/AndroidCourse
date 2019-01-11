package com.dejun.commonsdk.base;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.dejun.commonsdk.R;
import com.dejun.commonsdk.base.mvp.BasePresenter;
import com.dejun.commonsdk.base.mvp.BaseView;
import com.dejun.commonsdk.dialog.ProgressDialog;
import com.dejun.commonsdk.listener.NetChangeObserver;
import com.dejun.commonsdk.receiver.NetStateReceiver;
import com.dejun.commonsdk.util.NetUtils;
import com.dejun.commonsdk.wight.CustomActionBar;
import com.gyf.barlibrary.ImmersionBar;
import com.orhanobut.logger.Logger;

import me.yokeyword.fragmentation.SupportActivity;

/**
 * Author:DoctorWei
 * Time:2018/11/2 18:33
 * Description:定义一个Activity的基类 用于加载
 * email:1348172474@qq.com
 */
public abstract class BaseActivity<V extends BaseView,P extends BasePresenter> extends SupportActivity implements BaseView{
    public static final String TAG=BaseActivity.class.getSimpleName();
    public P mPresenter;
    private ProgressDialog progressDialog;
    public CustomActionBar customActionBar;
    /**
     * 网络观察者
     * @param savedInstanceState
     */
    protected NetChangeObserver netChangeObserver;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 所有子类都将继承这些相同的属性,请在设置界面之后设置

        //设置全局的网络监听
        netChangeObserver=new NetChangeObserver() {
            @Override
            public void onNetConnected(NetUtils.NetType netType) {
                Logger.d("onNetConnected");
            }

            @Override
            public void onNetDisConnect() {
                Logger.d("onNetDisConnect");
            }
        };
        NetStateReceiver.registerObserver(netChangeObserver);
        setContentView(getLayoutId());
        ImmersionBar.with(this)
                // .statusBarColor(R.color.red)
                .transparentStatusBar()
                //.fitsSystemWindows(true)  //使用该属性必须指定状态栏的颜色，不然状态栏透明，很难看
                .init();
        initView();
        initPresenter();
        initData(savedInstanceState);

    }

    protected void initPresenter() {
        mPresenter=createPresenter();
        if (mPresenter!=null){
            mPresenter.attachView(this);
        }
    }
    protected void hasViewAndPresenter(ExcutePresenter<P> excutePresenter){
        boolean hasViewAndPresenter = mPresenter != null && mPresenter.isAttachView();
        if (hasViewAndPresenter){
            excutePresenter.excute(mPresenter);
        }else{
            Logger.d("mvp未绑定");
        }
    }
    public interface ExcutePresenter<P>{
        void excute(P presenter);
    }
    protected P getPresenter() {
        return mPresenter;
    }
    protected V getView() {
        return (V)this;
    }

    protected abstract P createPresenter();


    protected abstract void initData(Bundle savedInstanceState);

    protected abstract void initView();

    protected abstract int getLayoutId();

    //写一个全局的跳转
    public  void openActivityWithIntent(@NonNull Class activity, Bundle bundle){
        Intent intent=new Intent(this,activity);
        if (bundle!=null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }
    public Bundle getIntentBundle(){
        Bundle bundle = getIntent().getExtras();
        return bundle;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (netChangeObserver!=null) {
            NetStateReceiver.removeObserver(netChangeObserver);
        }
        if (mPresenter!=null){
            mPresenter.dettachView();
        }
        // 必须调用该方法，防止内存泄漏
        ImmersionBar.with(this).destroy();
    }
    public void showPb(String content,boolean isShow){
        if (isShow) {
            if (progressDialog!=null){
                progressDialog.dismiss();
                progressDialog=null;
            }
            progressDialog = ProgressDialog.createPbDialog(content);
            progressDialog.show(getSupportFragmentManager(), getClass().getSimpleName());
        }else{
            if (progressDialog!=null)
            progressDialog.dismiss();
        }
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // 如果你的app可以横竖屏切换，并且适配4.4或者emui3手机请务必在onConfigurationChanged方法里添加这句话
        ImmersionBar.with(this).init();
    }
    public void showMsg(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

}
