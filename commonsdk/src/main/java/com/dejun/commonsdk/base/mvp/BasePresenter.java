package com.dejun.commonsdk.base.mvp;

import com.orhanobut.logger.Logger;

import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Author:DoctorWei
 * Time:2018/12/4 20:34
 * Description:
 * email:1348172474@qq.com
 */

public class BasePresenter<V extends BaseView> implements IBasePresenter<V> {
    private WeakReference<V> weakView;
    protected V proxyView;
    public void attachView(V baseView){
        this.weakView=new WeakReference<V>(baseView);
        MvpViewInvocationHandler invocationHandler = new MvpViewInvocationHandler(this.weakView.get());
        // 在这里采用动态代理
        proxyView = (V) Proxy.newProxyInstance(
                baseView.getClass().getClassLoader(), baseView.getClass()
                        .getInterfaces(), invocationHandler);
        Proxy.getProxyClass(baseView.getClass().getClassLoader(),baseView.getClass().getInterfaces());

    }
    private class MvpViewInvocationHandler implements InvocationHandler {

        private BaseView mvpView;

        public MvpViewInvocationHandler(BaseView mvpView) {
            this.mvpView = mvpView;
        }

        @Override
        public Object invoke(Object arg0, Method method, Object[] arg2) throws Throwable {
            if (isAttachView()) {
                return method.invoke(mvpView, arg2);
            }
            return null;
        }

    }
    public void dettachView(){
        if (this.weakView!=null){
            this.weakView.clear();
            this.weakView=null;
        }
    }
    public V getView(){
        return proxyView;
    }
    protected void hasView(ExcuteView<V> excuteView){
        if (proxyView!=null){
            excuteView.run(proxyView);
        }else{
            Logger.d("Fragment的View为空");
        }
    }
    protected interface ExcuteView<V>{
        void run(V view);
    }
    /**
     * 用于检查View是否为空对象
     */
    public boolean isAttachView(){
        return this.weakView!=null&&this.weakView.get()!=null;
    }
}
