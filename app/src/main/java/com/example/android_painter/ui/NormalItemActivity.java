package com.example.android_painter.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.android_painter.R;
import com.example.android_painter.view.NormalView;
import com.example.android_painter.model.ItemInfo;

/**
 * Created by ZhangXinmin on 2017/7/13.
 * Copyright (c) 2017 . All rights reserved.
 */

public class NormalItemActivity extends AppCompatActivity {
    private Context mContext;
    private ItemInfo mInfo;
    private NormalView mNormalView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal_item);

        initParamsAndValues();

        initViews();
    }

    private void initViews() {
        mNormalView = (NormalView) findViewById(R.id.normalview);
        mNormalView.setmDrawType(mInfo.getName());
    }

    private void initParamsAndValues() {
        mContext = this;

        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getBundleExtra("bundle");
            if (bundle != null) {
                if (bundle.getSerializable("item") != null) {
                    mInfo = (ItemInfo) bundle.getSerializable("item");
                }
            }
        }
    }
}
