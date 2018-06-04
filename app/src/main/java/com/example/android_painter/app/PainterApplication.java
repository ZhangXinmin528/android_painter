package com.example.android_painter.app;

import android.app.Application;

import com.example.android_painter.http.OkHttp;
import com.tencent.bugly.crashreport.CrashReport;

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

        //init Bugly:建议在测试阶段建议设置成true，发布时设置为false
        CrashReport.initCrashReport(getApplicationContext(), "211fc19956", true);

        //config LeakCanary
//        if (LeakCanary.isInAnalyzerProcess(this)) {
//            // This process is dedicated to LeakCanary for heap analysis.
//            // You should not init your app in this process.
//            return;
//        }
//        LeakCanary.install(this);

    }

}
