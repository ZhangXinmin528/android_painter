package com.zxm.android_painter.ui.text;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.zxm.android_painter.R;
import com.zxm.android_painter.model.DescriptionInfo;
import com.zxm.android_painter.ui.base.BaseActivity;
import com.zxm.android_painter.view.TextDescView;

/**
 * Created by ZhangXinmin on 2018/10/25.
 * Copyright (c) 2018 . All rights reserved.
 * 文字绘制
 */
public class TextItemActivity extends BaseActivity {
    public static final String PARAMS_TEXT_INFO = "text_info";
    private DescriptionInfo mDescriptionInfo;

    private TextDescView mTextView;

    @Override
    protected Object setLayout() {
        return R.layout.activity_text_item;
    }

    @Override
    protected void initParamsAndViews() {
        mContext = this;

        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null && bundle.containsKey(PARAMS_TEXT_INFO)) {
                mDescriptionInfo = (DescriptionInfo) bundle.getSerializable(PARAMS_TEXT_INFO);
            }
        }
    }

    @Override
    protected void initViews() {
        Toolbar toolbar = findViewById(R.id.toolbar_paint_item);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(mDescriptionInfo.getTitle());
        }

        mTextView = findViewById(R.id.textview);
        if (mDescriptionInfo != null) {
            mTextView.setDrawType(mDescriptionInfo.getType());
        }
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
