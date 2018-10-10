package com.zxm.android_painter.ui.normal;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.zxm.android_painter.R;
import com.zxm.android_painter.model.MethodInfo;
import com.zxm.android_painter.ui.base.BaseActivity;
import com.zxm.android_painter.view.NormalView;

/**
 * Created by ZhangXinmin on 2017/7/13.
 * Copyright (c) 2017 . All rights reserved.
 * 正常绘制展示页
 */

public class NormalItemActivity extends BaseActivity {
    private MethodInfo mInfo;
    private NormalView mNormalView;

    @Override
    protected Object setLayout() {
        return R.layout.activity_normal_item;
    }

    @Override
    protected void initParamsAndViews() {

        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                if (bundle.containsKey("item")) {
                    mInfo = (MethodInfo) bundle.getSerializable("item");
                }
            }
        }

    }

    @Override
    protected void initViews() {

        Toolbar toolbar = findViewById(R.id.toolbar_normal_item);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(mInfo.getName());
        }
        mNormalView = (NormalView) findViewById(R.id.normalview);
        mNormalView.setmDrawType(mInfo.getModel());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
