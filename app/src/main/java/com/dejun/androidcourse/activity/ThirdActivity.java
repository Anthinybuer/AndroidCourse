package com.dejun.androidcourse.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.text.Editable;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.dejun.androidcourse.R;
import com.dejun.commonsdk.base.BaseActivity;
import com.dejun.commonsdk.base.mvp.BasePresenter;
import com.dejun.commonsdk.popWindow.RecyvlerViewBottomWindow;
import com.dejun.commonsdk.popWindow.RecyvlerViewBottomWindowAdapter;
import com.dejun.commonsdk.util.AnimationUtil;
import com.dejun.commonsdk.util.DensitySizeUtil;
import com.dejun.commonsdk.util.ScreenUtil;
import com.dejun.commonsdk.wight.AlignTextView;
import com.dejun.commonsdk.wight.CustomSearchView;
import com.dejun.commonsdk.wight.WrapLinearLayoutManager;
import com.dejun.commonsdk.wight.numkeyboard.KeyboardUtil;
import com.dejun.commonsdk.wight.wheelpicker.TimePicker;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ThirdActivity extends BaseActivity {
    public static final String TAG = ThirdActivity.class.getSimpleName();
    private CustomSearchView csv;
    private NavigationView navigationview;
    private View main_contaier;
    private AlignTextView cb_align_text_view;
    private DrawerLayout drawerLayout;
    private FrameLayout headerView;
    final String text = "这是一段中英文混合的文本，I am very happy today。 aaaaaaaaaa," +
            "测试TextView文本对齐\n\nAlignTextView可以通过setAlign()方法设置每一段尾行的对齐方式, 默认尾行居左对齐. " +
            "CBAlignTextView可以像原生TextView一样操作, 但是需要使用getRealText()获取文本, 欢迎访问open.codeboy.me";
    @Override
    public BasePresenter createPresenter() {
        return null;
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public void initView() {
        main_contaier=findViewById(R.id.main_contaier);
        cb_align_text_view=findViewById(R.id.cb_align_text_view);
        AnimationUtil.startTransXAnimation(main_contaier,ScreenUtil.getScreenWidth(ThirdActivity.this),0,2000);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                showMsg("执行");
                AnimationUtil.startTransXAnimation(main_contaier,0,ScreenUtil.getScreenWidth(ThirdActivity.this),2000);
            }
        },5000);
        cb_align_text_view.setText(text);
        cb_align_text_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMsg("点击");
            }
        });
        drawerLayout=findViewById(R.id.drawerlayout);
        navigationview=findViewById(R.id.navigationview);
        headerView = (FrameLayout) navigationview.getHeaderView(0);
        navigationview.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_item_one:
                        break;
                    case R.id.navigation_item_two:
                        break;
                    case R.id.navigation_item_three:
                        break;
                    case R.id.navigation_item_four:
                        break;
                    case R.id.navigation_item_five:
                        break;
                }
                return false;
            }
        });
        csv = findViewById(R.id.csv);
        csv.setSearchViewListener(new CustomSearchView.SearchViewListener() {
            @Override
            public void ivLeftBackClick(int id) {
                super.ivLeftBackClick(id);
                drawerLayout.openDrawer(Gravity.LEFT);
            }
            @Override
            public void tvRightLeftBackClick(int id) {
                super.tvRightLeftBackClick(id);
                WrapLinearLayoutManager linearLayoutManager = new WrapLinearLayoutManager(ThirdActivity.this);
                List<String> datas = new ArrayList<>();
                datas.add("相册");
                datas.add("拍照");
                datas.add("取消");
                RecyvlerViewBottomWindowAdapter recyvlerViewBottomWindowAdapter = new RecyvlerViewBottomWindowAdapter(datas, ThirdActivity.this, com.dejun.commonsdk.R.layout.popup_window_text_item);
                final RecyvlerViewBottomWindow recyvlerViewBottomWindow = new RecyvlerViewBottomWindow(ThirdActivity.this, RecyvlerViewBottomWindow.INVALID_VALUE, RecyvlerViewBottomWindow.INVALID_VALUE, linearLayoutManager, recyvlerViewBottomWindowAdapter, new RecyvlerViewBottomWindow.OnItemClickListener() {
                    @Override
                    public void onItemClick(Object o, int position) {
                        if (position == 0) {//相册

                        } else if (position == 1) {//相机


                        }
                    }
                });
                recyvlerViewBottomWindow.showAsDropDown(csv.mTvCancle);
            }

        }).setSearchViewSearchListener(new CustomSearchView.SearchViewSearchListener() {
            @Override
            public void search(String content) {
                TimePicker picker = new TimePicker(ThirdActivity.this, TimePicker.HOUR_24);
                picker.setUseWeight(false);
                picker.setCycleDisable(false);
                picker.setRangeStart(0, 0);//00:00
                picker.setRangeEnd(23, 59);//23:59
                int currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
                int currentMinute = Calendar.getInstance().get(Calendar.MINUTE);
                picker.setSelectedItem(currentHour, currentMinute);
                picker.setTopLineVisible(false);
                picker.setTextPadding(DensitySizeUtil.dp2px(ThirdActivity.this, 15));
                picker.setOnTimePickListener(new TimePicker.OnTimePickListener() {
                    @Override
                    public void onTimePicked(String hour, String minute) {
                        Logger.d(hour + ":" + minute);
                    }
                });
                picker.show();
            }
        });
        csv.setWatchListener(new CustomSearchView.WatchListener() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start,int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Logger.d("watch="+s);
            }
        });
        final KeyboardUtil keyboardUtil = new KeyboardUtil(this, false);

    }

    //    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void getEvent(ProgressModel progressModel){
//        Logger.d(progressModel.getProgress());
//    }
    @Override
    public int getLayoutId() {
        return R.layout.activity_third;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

    }


}
