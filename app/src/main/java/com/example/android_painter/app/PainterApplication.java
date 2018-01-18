package com.example.android_painter.app;

import android.app.Application;

import com.example.android_painter.http.OkHttp;
import com.example.android_painter.util.LogUtil;

/**
 * Created by ZhangXinmin on 2017/9/17.
 * Copyright (c) 2017 . All rights reserved.
 */

public class PainterApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        //init okgo
        OkHttp.init(this, "painter");

    }

}
