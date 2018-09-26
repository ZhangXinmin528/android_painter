package com.zxm.android_painter.ui.web;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AbsoluteLayout;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.zxm.android_painter.R;
import com.zxm.android_painter.ui.base.BaseActivity;
import com.tencent.sonic.sdk.SonicConfig;
import com.tencent.sonic.sdk.SonicEngine;
import com.tencent.sonic.sdk.SonicSession;
import com.tencent.sonic.sdk.SonicSessionConfig;
import com.zxm.libcommon.Logger;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Created by ZhangXinmin on 2018/1/18.
 * Copyright (c) 2018 . All rights reserved.
 * 网页加载页面：提升加载速度
 */

public class WebActivity extends BaseActivity {
    private static final String TAG = WebActivity.class.getSimpleName();

    public static final String PARAMS_URL = "url";

    private SonicSession mSession;
    private QuickWebView mWebView;
    private String mUrl;
    private ProgressBar mLoading;
    private SonicSessionClientImpl mSessionClient;
    private Intent mIntent;

    @Override
    protected Object setLayout() {
        return R.layout.activity_web;
    }

    @Override
    protected void initParamsAndViews() {
        mIntent = getIntent();
        if (mIntent != null) {
            mUrl = mIntent.getStringExtra(PARAMS_URL);
        }

        if (TextUtils.isEmpty(mUrl)) {
            finish();
        }

        //硬件加速
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);

        // init sonic engine if necessary, or maybe u can do this when application created
        if (!SonicEngine.isGetInstanceAllowed()) {
            SonicEngine.createInstance(new SonicRuntimeImpl(getApplication()), new SonicConfig.Builder().build());
        }

        mSession = SonicEngine.getInstance().createSession(mUrl, new SonicSessionConfig.Builder().build());
        if (null != mSession) {
            mSession.bindClient(mSessionClient = new SonicSessionClientImpl());
        } else {
            // this only happen when a same sonic session is already running,
            // u can comment following codes to feedback as a default mode.
            Toast.makeText(mContext, "create sonic session fail", Toast.LENGTH_LONG).show();
            Logger.e(TAG, ":create session fail");
        }
    }

    @SuppressLint("AddJavascriptInterface")
    @Override
    protected void initViews() {
        Toolbar toolbar = findViewById(R.id.toolbar_web);
        toolbar.setTitle(R.string.all_news_title);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        //创建进度条
        mLoading = new ProgressBar(mContext, null, android.R.attr.progressBarStyleHorizontal);
        final int progressHeight = 5;
        mLoading.setLayoutParams(new AbsoluteLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT, progressHeight, 0, 0));//设置高度
        Drawable drawable = mContext.getResources().getDrawable(R.drawable.loading_webview);
        mLoading.setProgressDrawable(drawable);

        mWebView = findViewById(R.id.webview);
        mWebView.addView(mLoading);//添加进度条
        mWebView.setWebViewClient(webViewClient);
        mWebView.setWebChromeClient(webChromeClient);
        mWebView.removeJavascriptInterface("searchBoxJavaBridge_");
        mIntent.putExtra(SonicJavaScriptInterface.PARAM_LOAD_URL_TIME, System.currentTimeMillis());
        mWebView.addJavascriptInterface(new SonicJavaScriptInterface(mSessionClient, mIntent), "sonic");

        // step 5: webview is ready now, just tell session client to bind
        if (mSessionClient != null) {
            mSessionClient.bindWebView(mWebView);
            mSessionClient.clientReady();
        } else { // default mode
            mWebView.loadUrl(mUrl);
        }
    }

    private WebViewClient webViewClient = new WebViewClient() {
        /**
         * 防止加载网页时调起系统浏览器
         */
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView webView, String url) {
            super.onPageFinished(webView, url);
            if (mSession != null) {
                mSession.getSessionClient().pageFinish(url);
            }
        }

        @TargetApi(21)
        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
            return shouldInterceptRequest(view, request.getUrl().toString());
        }

        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
            if (mSession != null) {
                //step 6: Call sessionClient.requestResource when host allow the application
                // to return the local data .
                return (WebResourceResponse) mSession.getSessionClient().requestResource(url);
            }
            return null;
        }
    };


    private WebChromeClient webChromeClient = new WebChromeClient() {
        @Override
        public void onProgressChanged(WebView webView, int progress) {
            if (progress == 100) {
                mLoading.setVisibility(GONE);
            } else {
                if (!mLoading.isShown())
                    mLoading.setVisibility(VISIBLE);
                mLoading.setProgress(progress);
            }
            super.onProgressChanged(webView, progress);
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        if (null != mSession) {
            mSession.destroy();
            mSession = null;
        }
        super.onDestroy();
    }
}
