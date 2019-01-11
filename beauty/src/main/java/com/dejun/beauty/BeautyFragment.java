package com.dejun.beauty;

import android.os.Bundle;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.dejun.beauty.adapter.BeautyAdapter;
import com.dejun.commonsdk.db.model.BeautyRes;
import com.dejun.commonsdk.db.model.ResultsBean;
import com.dejun.beauty.mvp.presenter.BeautyPresenter;
import com.dejun.beauty.mvp.view.BeautyView;
import com.dejun.commonsdk.base.BaseFragment;
import com.dejun.commonsdk.cache.AppCache;
import com.dejun.commonsdk.db.GreenDaoClient;
import com.dejun.commonsdk.db.model.PersonRes;
import com.dejun.commonsdk.listener.OnViewPagerListener;
import com.dejun.commonsdk.util.FileUtil;
import com.dejun.commonsdk.util.RVMoveToPosition;
import com.dejun.commonsdk.wight.ViewPagerLayoutManager;
import com.orhanobut.logger.Logger;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Author:DoctorWei
 * Time:2018/11/3 15:21
 * Description:
 * email:1348172474@qq.com
 */

public class BeautyFragment extends BaseFragment<BeautyView, BeautyPresenter> implements BeautyView {
    private RecyclerView mBeautyRv;
    private SmartRefreshLayout mBeautySrl;

    @Override
    protected BeautyPresenter createPresenter() {
        return new BeautyPresenter();
    }
    @Override
    protected int getLayoutId() {
        return R.layout.beauty_fragment_layout;
    }

    @Override
    protected void init(View view) {
        //greendao操作
        GreenDaoClient greenDaoClient =new GreenDaoClient(_mActivity);
        boolean insert = greenDaoClient.insert(new PersonRes(null, "张三", 20));
        Logger.d("insert="+insert);
        List<PersonRes> personRes = greenDaoClient.queryAll();
        Logger.d(personRes);
        Logger.d("personRes="+personRes);
        mBeautySrl = (SmartRefreshLayout) view.findViewById(R.id.beauty_srl);
        mBeautySrl.setEnableLoadMoreWhenContentNotFull(false);//是否开启在内容不满一页是时启上拉加载的功能
        mBeautyRv =view.findViewById(R.id.beauty_rv);//new RecyclerView(_mActivity);//(RecyclerView) view.findViewById(R.id.beauty_rv);
        try {
            InputStream inputStream=getResources().getAssets().open("BeautyApi.xml");
            String appCachePicDirectory = AppCache.getAppCachePicDirectory();
            FileUtil.copyFile(appCachePicDirectory, System.currentTimeMillis() + ".xml", inputStream, 200, new FileUtil.CopyListener() {
                @Override
                public void startCopy() {

                }

                @Override
                public void progress(int progress) {

                }

                @Override
                public void finish(File file) {

                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        hasViewAndPresenter(new ExcutePresenter<BeautyPresenter>() {
            @Override
            public void run(BeautyPresenter presenter) {
                showPb(null, true);
                presenter.getbeautyRes();
            }
        });
//        mBeautySrl.setOnRefreshListener(new OnRefreshListener() {
//            @Override
//            public void onRefresh(RefreshLayout refreshLayout) {
//                List<ResultsBean> resultsBeans = null;//DaoManager.getInstance().getDaoSession().loadAll(ResultsBean.class);
//
//                    hasViewAndPresenter(new ExcutePresenter<BeautyPresenter>() {
//                        @Override
//                        public void run(BeautyPresenter presenter) {
//                            showPb(null, true);
//                            presenter.getbeautyRes();
//                        }
//                    });
//
//
//            }
//        });
//        mBeautySrl.setOnLoadMoreListener(new OnLoadMoreListener() {
//            @Override
//            public void onLoadMore(RefreshLayout refreshLayout) {
//                finishSmartRefreshLayoutDelay();
//            }
//        });
//        mBeautySrl.autoRefresh();
    }



    public static BeautyFragment newInstance() {
        return new BeautyFragment();
    }
    BeautyAdapter beautyAdapter;
    private ViewPagerLayoutManager mLayoutManager;
    @Override
    public void getBeautySuccess(BeautyRes beautyRes) {
        Logger.d(beautyRes.getResults());
//        final List<ResultsBean> results = beautyRes.getResults();
//        DaoManager.getInstance().getDaoSession().runInTx(new Runnable() {
//            @Override
//            public void run() {
//                for (ResultsBean resultsBean:results){
//                    DaoManager.getInstance().getDaoSession().insertOrReplace(resultsBean);
//                }
//            }
//        });
//        finishSmartRefreshLayout();
        showPb(null, false);
//        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
//        staggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);//优化图片闪烁和错位
//        mBeautyRv.setLayoutManager(staggeredGridLayoutManager);
        mLayoutManager = new ViewPagerLayoutManager(_mActivity, OrientationHelper.VERTICAL);
        mBeautyRv.setLayoutManager(mLayoutManager);
//        mBeautyRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//                boolean move = mBeautyRv.isMove();
//                if (move ){
//                    mBeautyRv.setMove(false) ;
//                    RecyclerView.LayoutManager layoutManager = mBeautyRv.getLayoutManager();
//                    if (layoutManager instanceof LinearLayoutManager){
//                        LinearLayoutManager linearLayoutManager= (LinearLayoutManager) layoutManager;
//                        mBeautyRv.linearLayoutManagerMove(linearLayoutManager);
//                    }
//
//                }
//            }
//        });
        final RVMoveToPosition rvMoveToPosition=RVMoveToPosition.createRVMoveToPosition(mBeautyRv,9);
        rvMoveToPosition.setListener(new RVMoveToPosition.onScrollStateChanged() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            }
        });
//        rvMoveToPosition.smoothMoveToPosition();
       // mBeautyRv.addItemDecoration(new StaggeredDividerItemDecoration(_mActivity, StaggeredDividerItemDecoration.DIRECTION_VERTICAL, 2));
         beautyAdapter = new BeautyAdapter(beautyRes.getResults(), _mActivity, R.layout.beauty_item_layout);
        beautyAdapter.setDownLoadListener(new BeautyAdapter.DownLoadListener() {
            @Override
            public void downLoad(int position) {
                ResultsBean resultsBean = beautyAdapter.getItems().get(position);
                String url = resultsBean.getUrl();
                // TODO: 2018/12/10 下载

            }
        });
        mBeautyRv.setAdapter(beautyAdapter);

        mBeautyRv.post(new Runnable() {
            @Override
            public void run() {
                rvMoveToPosition.smoothMoveToPosition();
            }
        });

    }

//    private void finishSmartRefreshLayout() {
//        if (mBeautySrl!=null){
//            mBeautySrl.finishRefresh(0);
//            mBeautySrl.finishLoadMore(0);
//        }
//    }
//    private void finishSmartRefreshLayoutDelay() {
//        if (mBeautySrl!=null){
//            mBeautySrl.finishRefresh(100);
//            mBeautySrl.finishLoadMore(100);
//        }
//    }
    @Override
    public void getBeautyFailed() {

    }
    private void initListener(){
        mLayoutManager.setOnViewPagerListener(new OnViewPagerListener() {

            @Override
            public void onInitComplete() {
                Logger.d("onInitComplete");
                //playVideo(0);
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
                //releaseVideo(index);
            }

            @Override
            public void onPageSelected(int position,boolean isBottom) {
                Logger.d("选中位置:"+position+"  是否是滑动到底部:"+isBottom);
                //playVideo(0);
            }


        });
    }

}
