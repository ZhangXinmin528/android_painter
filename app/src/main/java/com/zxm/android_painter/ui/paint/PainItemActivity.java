package com.zxm.android_painter.ui.paint;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.zxm.android_painter.R;
import com.zxm.android_painter.model.PaintInfo;
import com.zxm.android_painter.ui.base.BaseActivity;
import com.zxm.android_painter.view.PaintView;

import java.io.Serializable;

/**
 * Created by ZhangXinmin on 2018/10/10.
 * Copyright (c) 2018 . All rights reserved.
 * Paint单项属性解析
 */
public class PainItemActivity extends BaseActivity {

    public static final String PARAMS_PAINT_INFO = "paint_info";
    private PaintInfo mPaintInfo;
    private PaintView mPaintView;

    @Override
    protected Object setLayout() {
        return R.layout.activity_paint_item;
    }

    @Override
    protected void initParamsAndViews() {
        mContext = this;

        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null && bundle.containsKey(PARAMS_PAINT_INFO)) {
                mPaintInfo = (PaintInfo) bundle.getSerializable(PARAMS_PAINT_INFO);
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
            actionBar.setTitle(mPaintInfo.getTitle());
        }

        mPaintView = findViewById(R.id.paintview);
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
