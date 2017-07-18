package com.example.customview_simple.canvas;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.customview_simple.R;
import com.example.customview_simple.canvas.view.NormalView;
import com.example.customview_simple.model.ItemInfo;

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
