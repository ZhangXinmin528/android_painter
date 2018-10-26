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
import com.zxm.android_painter.model.DescriptionInfo;
import com.zxm.android_painter.ui.adapter.DescriptionAdapter;
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
    private DescriptionAdapter mAdapter;
    private List<DescriptionInfo> mDataList;

    @Override
    protected Object setLayout() {
        return R.layout.activity_explore_description;
    }

    @Override
    protected void initParamsAndViews() {
        mContext = this;
        mDataList = new ArrayList<>();
        initData();
        mAdapter = new DescriptionAdapter(mContext, mDataList);
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
        mDataList.add(new DescriptionInfo(
                "设置画笔颜色",
                "Set the paint's color.",
                "setColor",
                "setColor"));

        mDataList.add(new DescriptionInfo(
                "设置画笔颜色",
                "Helper to setColor(), that takes a,r,g,b and constructs the color int",
                "setARGB",
                "setARGB"));
        mDataList.add(new DescriptionInfo(
                "LinearGradient",
                "Create a shader that draws a linear gradient along a line.",
                "mPaint.setShader(shader)",
                "LinearGradient"));

        mDataList.add(new DescriptionInfo(
                "RadialGradient",
                "Create a shader that draws a radial gradient given the center and radius.",
                "mPaint.setShader(shader)",
                "RadialGradient"));

        mDataList.add(new DescriptionInfo(
                "SweepGradient",
                "A Shader that draws a sweep gradient around a center point.",
                "mPaint.setShader(shader)",
                "SweepGradient"));

        mDataList.add(new DescriptionInfo(
                "BitmapShader",
                "Shader used to draw a bitmap as a texture. The bitmap can be repeated or" +
                        " mirrored by setting the tiling mode.",
                "mPaint.setShader(shader)",
                "BitmapShader"));

        mDataList.add(new DescriptionInfo(
                "ComposeShader",
                "Create a new compose shader, given shaders A, B, and a combining PorterDuff mode.",
                "mPaint.setShader(shader)",
                "ComposeShader"));

        mDataList.add(new DescriptionInfo(
                "LightingColorFilter",
                "A color filter that can be used to simulate simple lighting effects.",
                "mPaint.setColorFilter(colorFilter)",
                "LightingColorFilter"));

        mDataList.add(new DescriptionInfo(
                "PorterDuffColorFilter",
                "A color filter that can be used to tint the source pixels using a single color" +
                        " and a specific {@link PorterDuff Porter-Duff composite mode}.",
                "mPaint.setColorFilter(colorFilter)",
                "PorterDuffColorFilter"));

        mDataList.add(new DescriptionInfo(
                "ColorMatrixColorFilter",
                "A color filter that transforms colors through a 4x5 color matrix.",
                "mPaint.setColorFilter(colorFilter)",
                "ColorMatrixColorFilter"));

        mDataList.add(new DescriptionInfo(
                "PorterDuffXfermode",
                "Specialized implementation of {@link Paint}'s" +
                        " {@link Paint#setXfermode(Xfermode) transfer mode}.",
                "mPaint.setXfermode(xfermode);",
                "PorterDuffXfermode"));

        mDataList.add(new DescriptionInfo(
                "setAntiAlias",
                "Helper for setFlags(), setting or clearing the ANTI_ALIAS_FLAG bit" +
                        " AntiAliasing smooths out the edges of what is being drawn, but is has" +
                        " no impact on the interior of the shape. ",
                "mPaint.setAntiAlias(aa);",
                "setAntiAlias"));

        mDataList.add(new DescriptionInfo(
                "setStyle",
                "Set the paint's style, used for controlling how primitives'" +
                        " geometries are interpreted (except for drawBitmap, which always assumes" +
                        " Fill).",
                "mPaint.setStyle();",
                "setStyle"));

        mDataList.add(new DescriptionInfo(
                "setStrokeWidth",
                "Set the width for stroking.",
                "mPaint.setStrokeWidth();",
                "setStrokeWidth"));

        mDataList.add(new DescriptionInfo(
                "setStrokeCap",
                "Set the paint's Cap.The default is BUTT.",
                "mPaint.setStrokeWidth();",
                "setStrokeCap"));

        mDataList.add(new DescriptionInfo(
                "setStrokeJoin",
                "Set the paint's Join.The default is MITER.",
                "mPaint.setStrokeJoin();",
                "setStrokeJoin"));

        mDataList.add(new DescriptionInfo(
                "setStrokeMiter",
                "Set the paint's stroke miter value.",
                "mPaint.setStrokeMiter();",
                "setStrokeMiter"));

        mDataList.add(new DescriptionInfo(
                "CornerPathEffect",
                "Set or clear the patheffect{@link CornerPathEffect} object.",
                "mPaint.setPathEffect();",
                "CornerPathEffect"));

        mDataList.add(new DescriptionInfo(
                "DiscretePathEffect",
                "Set or clear the patheffect{@link DiscretePathEffect} object.",
                "mPaint.setPathEffect();",
                "DiscretePathEffect"));

        mDataList.add(new DescriptionInfo(
                "DashPathEffect",
                "Set or clear the patheffect{@link DashPathEffect} object.",
                "mPaint.setPathEffect();",
                "DashPathEffect"));

        mDataList.add(new DescriptionInfo(
                "PathDashPathEffect",
                "Set or clear the patheffect{@link PathDashPathEffect} object.",
                "mPaint.setPathEffect();",
                "PathDashPathEffect"));

        mDataList.add(new DescriptionInfo(
                "SumPathEffect",
                "Set or clear the patheffect{@link android.graphics.SumPathEffect} object.",
                "mPaint.setPathEffect();",
                "SumPathEffect"));

        mDataList.add(new DescriptionInfo(
                "ComposePathEffect",
                "Set or clear the patheffect{@link ComposePathEffect} object.",
                "mPaint.setPathEffect();",
                "ComposePathEffect"));

        mDataList.add(new DescriptionInfo(
                "setShadowLayer",
                "This draws a shadow layer below the main layer, with the specified" +
                        " offset and color, and blur radius.",
                "mPaint.setShadowLayer();",
                "setShadowLayer"));

        mDataList.add(new DescriptionInfo(
                "BlurMaskFilter",
                "Set or clear the maskfilter{@link BlurMaskFilter} object.",
                "mPaint.setMaskFilter();",
                "BlurMaskFilter"));

        mDataList.add(new DescriptionInfo(
                "EmbossMaskFilter",
                "Set or clear the maskfilter{@link EmbossMaskFilter} object.",
                "mPaint.setMaskFilter();",
                "EmbossMaskFilter"));

        mDataList.add(new DescriptionInfo(
                "getFillPath",
                "Applies any/all effects (patheffect, stroking) to src, returning the" +
                        "  result in dst.",
                "mPaint.getFillPath();",
                "getFillPath"));

        mDataList.add(new DescriptionInfo(
                "getTextPath",
                "Return the path (outline) for the specified text.",
                "mPaint.getTextPath();",
                "getTextPath"));
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        DescriptionInfo info = (DescriptionInfo) adapter.getItem(position);
        if (info != null) {
            Intent intent = new Intent(mContext, PaintItemActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable(PARAMS_PAINT_INFO, info);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }
}
