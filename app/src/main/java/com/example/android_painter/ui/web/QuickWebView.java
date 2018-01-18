package com.example.android_painter.ui.web;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AbsoluteLayout;
import android.widget.ProgressBar;


/**
 * Created by ZhangXinmin on 2018/1/18.
 * Copyright (c) 2018 . All rights reserved.
 * WebView
 */

public class QuickWebView extends WebView {
    private ProgressBar progressbar;  //进度条

    public QuickWebView(Context context) {
        super(context);
    }

    public QuickWebView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);

        initWebViewSettings(context);
    }


    @SuppressLint("SetJavaScriptEnabled")
    private void initWebViewSettings(Context context) {
        WebSettings webSetting = this.getSettings();

        //支持JS脚本
        webSetting.setJavaScriptEnabled(true);
        webSetting.setAllowFileAccess(true);
        webSetting.setSupportMultipleWindows(true);
        webSetting.setGeolocationEnabled(true);
        webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
        webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);
        //适配手机大小
        webSetting.setUseWideViewPort(true);//调整图片适合屏幕
        webSetting.setLoadWithOverviewMode(true);//缩放至屏幕大小
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);//支持内容重新布局，调用该方法会引起页面重绘
        webSetting.setSupportZoom(true);//支持缩放
        webSetting.setBuiltInZoomControls(true);//设置内置的缩放控件。
        webSetting.setDisplayZoomControls(false);//隐藏原生的缩放控件
        webSetting.setJavaScriptCanOpenWindowsAutomatically(true);//支持通过JS打开新窗口
        webSetting.setDomStorageEnabled(true);
        webSetting.setAppCacheEnabled(false);//设置缓存
        webSetting.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSetting.setAllowContentAccess(true);
        webSetting.setDatabaseEnabled(true);
        webSetting.setSavePassword(false);
        webSetting.setSaveFormData(false);
    }


}
