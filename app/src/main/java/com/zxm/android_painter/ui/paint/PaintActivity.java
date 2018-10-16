package com.zxm.android_painter.ui.paint;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zxm.android_painter.R;
import com.zxm.android_painter.model.PaintInfo;
import com.zxm.android_painter.ui.adapter.PaintAdapter;
import com.zxm.android_painter.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import static com.zxm.android_painter.ui.paint.PaintItemActivity.PARAMS_PAINT_INFO;

/**
 * Created by ZhangXinmin on 2018/10/9.
 * Copyright (c) 2018 . All rights reserved.
 * 自定义View部分：Paint相关；
 */
public class PaintActivity extends BaseActivity implements BaseQuickAdapter.OnItemClickListener {
    private RecyclerView mRecyclerView;
    private PaintAdapter mAdapter;
    private List<PaintInfo> mDataList;

    @Override
    protected Object setLayout() {
        return R.layout.activity_paint;
    }

    @Override
    protected void initParamsAndViews() {
        mContext = this;
        mDataList = new ArrayList<>();
        initData();
        mAdapter = new PaintAdapter(mContext, mDataList);
        mAdapter.setOnItemClickListener(this);
    }

    @Override
    protected void initViews() {
        Toolbar toolbar = findViewById(R.id.toolbar_paint);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_paint);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
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

    //TODO:测试数据
    private void initData() {
        mDataList.add(new PaintInfo(
                "设置画笔颜色",
                "Set the paint's color.",
                "setColor",
                "setColor"));

        mDataList.add(new PaintInfo(
                "设置画笔颜色",
                "Helper to setColor(), that takes a,r,g,b and constructs the color int",
                "setARGB",
                "setARGB"));
        mDataList.add(new PaintInfo(
                "LinearGradient",
                "Create a shader that draws a linear gradient along a line.",
                "mPaint.setShader(shader)",
                "LinearGradient"));

        mDataList.add(new PaintInfo(
                "RadialGradient",
                "Create a shader that draws a radial gradient given the center and radius.",
                "mPaint.setShader(shader)",
                "RadialGradient"));

        mDataList.add(new PaintInfo(
                "SweepGradient",
                "A Shader that draws a sweep gradient around a center point.",
                "mPaint.setShader(shader)",
                "SweepGradient"));

        mDataList.add(new PaintInfo(
                "BitmapShader",
                "Shader used to draw a bitmap as a texture. The bitmap can be repeated or" +
                        " mirrored by setting the tiling mode.",
                "mPaint.setShader(shader)",
                "BitmapShader"));

        mDataList.add(new PaintInfo(
                "ComposeShader",
                "Create a new compose shader, given shaders A, B, and a combining PorterDuff mode.",
                "mPaint.setShader(shader)",
                "ComposeShader"));

        mDataList.add(new PaintInfo(
                "LightingColorFilter",
                "A color filter that can be used to simulate simple lighting effects.",
                "mPaint.setColorFilter(colorFilter)",
                "LightingColorFilter"));

        mDataList.add(new PaintInfo(
                "PorterDuffColorFilter",
                "A color filter that can be used to tint the source pixels using a single color" +
                        " and a specific {@link PorterDuff Porter-Duff composite mode}.",
                "mPaint.setColorFilter(colorFilter)",
                "PorterDuffColorFilter"));
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        PaintInfo info = (PaintInfo) adapter.getItem(position);
        if (info != null) {
            Intent intent = new Intent(mContext, PaintItemActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable(PARAMS_PAINT_INFO, info);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }
}
