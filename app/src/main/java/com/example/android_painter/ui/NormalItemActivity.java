package com.example.android_painter.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.android_painter.R;
import com.example.android_painter.model.MethodInfo;
import com.example.android_painter.ui.base.BaseActivity;
import com.example.android_painter.view.NormalView;

/**
 * Created by ZhangXinmin on 2017/7/13.
 * Copyright (c) 2017 . All rights reserved.
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
            Bundle bundle = intent.getBundleExtra("bundle");
            if (bundle != null) {
                if (bundle.getSerializable("item") != null) {
                    mInfo = (MethodInfo) bundle.getSerializable("item");
                }
            }
        }

    }

    @Override
    protected void initViews() {
        Toolbar toolbar = findViewById(R.id.toolbar_normal_item);
        if (mInfo != null) {
            toolbar.setTitle(mInfo.getName());
        }
        mNormalView = (NormalView) findViewById(R.id.normalview);
        mNormalView.setmDrawType(mInfo.getModel());
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onContextItemSelected(item);
    }
}
