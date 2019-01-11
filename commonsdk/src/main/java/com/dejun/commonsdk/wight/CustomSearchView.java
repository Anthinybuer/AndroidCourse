package com.dejun.commonsdk.wight;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.dejun.commonsdk.R;

/**
 * Author:DoctorWei
 * Time:2018/12/28 9:10
 * Description:自定义搜索框
 * email:1348172474@qq.com
 */

public class CustomSearchView extends RelativeLayout implements View.OnClickListener {
    public LinearLayout mLlSearchItem;//条目背景
    public ImageView mIvSearchBack;//返回键
    public TextView mTvSearchBack;//返回键
    public EditText mDetSearchContent;//编辑框搜索内容
    public TextView mTvCancle;//搜索框取消
    private ImageView mIvCancle;

    public CustomSearchView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }


    private void initView(Context context, AttributeSet attrs) {
        View view = LayoutInflater.from(context).inflate(R.layout.search_view_layout, this);
        mLlSearchItem = view.findViewById(R.id.commonsdk_ll_search_item);
        mIvSearchBack = view.findViewById(R.id.commonsdk_iv_search_back);
        mTvSearchBack = view.findViewById(R.id.commonsdk_tv_search_back);
        mDetSearchContent = view.findViewById(R.id.commonsdk_et_search_content);
        mTvCancle = view.findViewById(R.id.commonsdk_tv_search_cancle);
        mIvCancle = view.findViewById(R.id.commonsdk_iv_search_cancle);

        mIvSearchBack.setOnClickListener(this);
        mTvSearchBack.setOnClickListener(this);
        mTvCancle.setOnClickListener(this);
        mIvCancle.setOnClickListener(this);
        //获取设置属性
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomSearchView);
        String string = typedArray.getString(R.styleable.CustomSearchView_search_hint);//提示文字
        int leftIcon = typedArray.getResourceId(R.styleable.CustomSearchView_search_left_icon, R.drawable.arrow_left_back);
        int rightIcon = typedArray.getResourceId(R.styleable.CustomSearchView_search_right_icon, R.drawable.arrow_left_back);
        String leftText = typedArray.getString(R.styleable.CustomSearchView_search_left_text);
        String rightText = typedArray.getString(R.styleable.CustomSearchView_search_right_text);
        int bgColor = typedArray.getColor(R.styleable.CustomSearchView_search_backgound,context.getResources().getColor(R.color.white));

        if (typedArray.hasValue(R.styleable.CustomSearchView_search_hint)) {
            mDetSearchContent.setHint(string);
        }
        if (typedArray.hasValue(R.styleable.CustomSearchView_search_left_icon)) {
            mIvSearchBack.setImageResource(leftIcon);
        }
        if (typedArray.hasValue(R.styleable.CustomSearchView_search_right_icon)) {
            mIvCancle.setImageResource(rightIcon);
        }
        if (typedArray.hasValue(R.styleable.CustomSearchView_search_left_text)) {
            mTvSearchBack.setText(leftText);
        }
        if (typedArray.hasValue(R.styleable.CustomSearchView_search_right_text)) {
            mTvCancle.setText(rightText);
        }
        if (typedArray.hasValue(R.styleable.CustomSearchView_search_backgound)) {
            mLlSearchItem.setBackgroundColor(bgColor);
        }

        mDetSearchContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (watchListener != null) {
                    watchListener.beforeTextChanged(s, start, count, after);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (watchListener != null) {
                    watchListener.onTextChanged(s, start, count, count);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (watchListener != null) {
                    watchListener.afterTextChanged(s);
                }
            }
        });
        mDetSearchContent.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                        if (searchViewSearchListener != null) {
                            String content = v.getText().toString();
                            searchViewSearchListener.search(content);
                        }
                    return true;
                }
                return false;
            }
        });
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.commonsdk_iv_search_back) {
            searchViewListener.ivLeftBackClick(id);
        } else if (id == R.id.commonsdk_tv_search_back) {
            searchViewListener.tvLeftBackClick(id);
        } else if (id == R.id.commonsdk_iv_search_cancle) {
            searchViewListener.ivRightBackClick(id);
        } else if (id == R.id.commonsdk_tv_search_cancle) {
            searchViewListener.tvRightLeftBackClick(id);
        }
    }

    public WatchListener watchListener;

    public CustomSearchView setWatchListener(WatchListener watchListener) {
        this.watchListener = watchListener;
        return this;
    }

    public interface WatchListener {
        void beforeTextChanged(CharSequence s, int start, int count, int after);

        void onTextChanged(CharSequence s, int start, int before, int count);

        void afterTextChanged(Editable s);
    }

    public SearchViewListener searchViewListener;

    public CustomSearchView setSearchViewListener(SearchViewListener searchViewListener) {
        this.searchViewListener = searchViewListener;
        return this;
    }

    public abstract static class SearchViewListener {
        public void ivLeftBackClick(int id) {

        }

        public void tvLeftBackClick(int id) {

        }

        public void ivRightBackClick(int id) {

        }

        public void tvRightLeftBackClick(int id) {

        }
    }

    public SearchViewSearchListener searchViewSearchListener;

    public CustomSearchView setSearchViewSearchListener(SearchViewSearchListener searchViewSearchListener) {
        this.searchViewSearchListener = searchViewSearchListener;
        return this;
    }

    public interface SearchViewSearchListener {
        void search(String content);
    }

}
