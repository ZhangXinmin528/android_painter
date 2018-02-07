package com.example.android_painter.app;

import android.app.Application;

import com.example.android_painter.http.OkHttp;
import com.example.android_painter.util.LogUtil;
import com.homilychart.server.modal.DataServer;
import com.homilychart.server.modal.LoginServer;
import com.tencent.bugly.crashreport.CrashReport;

/**
 * Created by ZhangXinmin on 2017/9/17.
 * Copyright (c) 2017 . All rights reserved.
 */

public class PainterApplication extends Application {


    // 登录服务器
    public static final LoginServer loginServer = new LoginServer("sjrzbg.rzfwq.com", 9997);

    // 行情服务器
    public static final DataServer dataServer = new DataServer("sjbg.rzfwq.com", 8815, 1);

    @Override
    public void onCreate() {
        super.onCreate();

        //init okgo
        OkHttp.init(this, "painter");

        //init Bugly:建议在测试阶段建议设置成true，发布时设置为false
        CrashReport.initCrashReport(getApplicationContext(), "211fc19956", true);


    }

}
