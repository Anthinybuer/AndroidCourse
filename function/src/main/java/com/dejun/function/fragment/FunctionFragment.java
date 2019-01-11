package com.dejun.function.fragment;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.dejun.commonsdk.base.BaseFragment;
import com.dejun.commonsdk.base.BaseRvAdapter;
import com.dejun.commonsdk.db.model.DataBean;
import com.dejun.commonsdk.listener.OnViewPagerListener;
import com.dejun.commonsdk.wight.ViewPagerLayoutManager;
import com.dejun.function.R;
import com.dejun.function.adapter.FunctionAdapter;
import com.dejun.function.mvp.presenter.FunctionPresenter;
import com.dejun.function.mvp.view.FunctionView;
import com.orhanobut.logger.Logger;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;


/**
 * Author:DoctorWei
 * Time:2018/11/10 15:03
 * Description:
 * email:1348172474@qq.com
 */

public class FunctionFragment extends BaseFragment<FunctionView, FunctionPresenter> implements FunctionView {
    private RecyclerView mRvFunction;
    private FunctionAdapter functionAdapter;
    private SmartRefreshLayout mSrlFunction;
    public static final int REFRESH=0;
    public static final int LOAD_MORE=1;
    public static final String LIST_TYPE="1";
    public  int list_page=1;
    private ViewPagerLayoutManager mLayoutManager;
    public static FunctionFragment newInstance() {
        return new FunctionFragment();
    }

    @Override
    protected FunctionPresenter createPresenter() {
        return new FunctionPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_function_layout;
    }

    @Override
    protected void init(View view) {
        mRvFunction = (RecyclerView) view.findViewById(R.id.rv_function);
        mLayoutManager = new ViewPagerLayoutManager(_mActivity, OrientationHelper.VERTICAL);
        mRvFunction.setLayoutManager(mLayoutManager);
        mSrlFunction = (SmartRefreshLayout) view.findViewById(R.id.srl_function);
        functionAdapter=new FunctionAdapter(null,_mActivity,R.layout.function_item_layout);
        functionAdapter.setOnItemClickListener(new BaseRvAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Object o, int position) {

            }
        });
        mRvFunction.setAdapter(functionAdapter);
        initListener();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mSrlFunction.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                hasViewAndPresenter(new ExcutePresenter<FunctionPresenter>() {
                    @Override
                    public void run(FunctionPresenter presenter) {
                        //List<DataBean> functionResFromDb = presenter.getFunctionResFromDb();
                            list_page=0;
                            hasViewAndPresenter(new ExcutePresenter<FunctionPresenter>() {
                                @Override
                                public void run(FunctionPresenter presenter) {
                                    presenter.getFunctionRes("1", list_page+"",REFRESH);
                                }
                            });

                    }
                });

            }
        });
        mSrlFunction.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                list_page++;
                hasViewAndPresenter(new ExcutePresenter<FunctionPresenter>() {
                    @Override
                    public void run(FunctionPresenter presenter) {
                        presenter.getFunctionRes("1", list_page+"",LOAD_MORE);
                    }
                });
            }
        });
        mSrlFunction.autoRefresh();

    }

    @Override
    public void getFunctionSuccess(final List<DataBean> dataBeans, final int scrollType) {
        finishSmartRefreshLayout();
        Logger.d(dataBeans);
//        DaoManager.getInstance().getDaoSession().runInTx(new Runnable() {
//            @Override
//            public void run() {
//                for (DataBean dataBean:dataBeans) {
//                    DaoManager.getInstance().getDaoSession().insertOrReplace(dataBean);
//                }
//            }
//        });
        if (scrollType==REFRESH){
           functionAdapter.refreshDatas(dataBeans);
       }else{
           functionAdapter.addDatas(dataBeans);
       }
    }

    @Override
    public void getFunctionFailed() {
        finishSmartRefreshLayoutDelay();
    }
    private void finishSmartRefreshLayout() {
        if (mSrlFunction!=null){
            mSrlFunction.finishRefresh(0);
            mSrlFunction.finishLoadMore(0);
        }
    }
    private void finishSmartRefreshLayoutDelay() {
        if (mSrlFunction!=null){
            mSrlFunction.finishRefresh(100);
            mSrlFunction.finishLoadMore(100);
        }
    }
    private void initListener(){
        mLayoutManager.setOnViewPagerListener(new OnViewPagerListener() {

            @Override
            public void onInitComplete() {
                Logger.d("onInitComplete");
                playVideo(0);
            }

            @Override
            public void onPageRelease(boolean isNext,int position) {
                Logger.d("释放位置:"+position +" 下一页:"+isNext);
                int index = 0;
                if (isNext){
                    index = 0;
                }else {
                    index = 1;
                }
                releaseVideo(index);
            }

            @Override
            public void onPageSelected(int position,boolean isBottom) {
                Logger.d("选中位置:"+position+"  是否是滑动到底部:"+isBottom);
                playVideo(0);
            }


        });
    }
    private void playVideo(int position) {
        DataBean dataBean = functionAdapter.getItems().get(position);
        View itemView = mRvFunction.getChildAt(0);
        final MediaPlayer[] mediaPlayer = new MediaPlayer[1];

    }
    private void releaseVideo(int index){
        View itemView = mRvFunction.getChildAt(index);

    }
}
