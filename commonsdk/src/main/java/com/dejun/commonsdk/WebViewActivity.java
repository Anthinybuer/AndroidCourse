package com.dejun.commonsdk;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import com.dejun.commonsdk.base.BaseActivity;
import com.dejun.commonsdk.base.mvp.BasePresenter;
import com.dejun.commonsdk.wight.CustomActionBar;

public class WebViewActivity extends BaseActivity {

    private WebView mCommonsdkWebview;
    private ProgressBar mCommonsdkWebviewPb;
    public static final String WEB_VIEW_URL = "web_view_url";
    private String url;
    public static final int PB_MAX = 100;//进度条的最大值

    public static void startWenViewActivity(BaseActivity fromActivity, String url) {
        Bundle bundle = new Bundle();
        bundle.putString(WEB_VIEW_URL, url);
        fromActivity.openActivityWithIntent(WebViewActivity.class, bundle);
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void initView() {
        customActionBar = (CustomActionBar) findViewById(R.id.custom_action_bar);
        customActionBar.setActionBarListener(new CustomActionBar.ActionBarListener() {
            @Override
            public void ivLeftBackClick(int id) {
                super.ivLeftBackClick(id);
                finish();
            }
        });
        mCommonsdkWebview = (WebView) findViewById(R.id.commonsdk_webview);
        mCommonsdkWebviewPb = (ProgressBar) findViewById(R.id.commonsdk_webview_pb);
        mCommonsdkWebviewPb.setMax(PB_MAX);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        url = getIntent().getStringExtra(WEB_VIEW_URL);
        mCommonsdkWebview.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress >= 100) {
                    mCommonsdkWebviewPb.setVisibility(View.GONE);
                } else {
                    mCommonsdkWebviewPb.setProgress(newProgress);
                }

            }

        });
        mCommonsdkWebview.setWebViewClient(new WebViewClient() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(request.getUrl().toString());
                return super.shouldOverrideUrlLoading(view, request);
            }
        });
        WebSettings settings = mCommonsdkWebview.getSettings();
        settings.setDomStorageEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        settings.setJavaScriptEnabled(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        mCommonsdkWebview.loadUrl(url);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_web_view;
    }
}
