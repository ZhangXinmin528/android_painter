package com.example.customview_simple.canvas;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.customview_simple.R;
import com.example.customview_simple.canvas.view.ClipView;

/**
 * Created by ZhangXinmin on 2017/7/18.
 * Copyright (c) 2017 . All rights reserved.
 */

public class ClipDisplayActivity extends AppCompatActivity {
    private Context mContext;
    private ClipView mClipView;
    private String mDrawType;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_clip_detial);

        initParamsAndValues();

        initViews();
    }

    private void initViews() {
        mClipView = (ClipView) findViewById(R.id.clipview);

    }

    private void initParamsAndValues() {
        mContext = this;

        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getBundleExtra("bundle");
            if (bundle != null) {
                mDrawType = bundle.getString("drawType", "clipPath");
            }
        }

    }


}
