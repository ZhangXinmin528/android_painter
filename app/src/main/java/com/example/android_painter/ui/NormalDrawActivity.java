package com.example.android_painter.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.android_painter.R;
import com.example.android_painter.model.MethodInfo;
import com.example.android_painter.ui.adapter.NormalQuickAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZhangXinmin on 2017/7/13.
 * Copyright (c) 2017 . All rights reserved.
 * 常规绘制
 */

public class NormalDrawActivity extends AppCompatActivity implements BaseQuickAdapter.OnItemClickListener {
    private Context mContext;
    private RecyclerView mRecyclerView;
    private NormalQuickAdapter mNormalQuickAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal);

        initParamsAndValues();

        initViews();

    }

    private void initParamsAndValues() {
        mContext = this;

        //init adapter
        mNormalQuickAdapter = new NormalQuickAdapter(initNormalInfo());
        mNormalQuickAdapter.setOnItemClickListener(this);
        mNormalQuickAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);

    }

    private void initViews() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_normal);
        mRecyclerView.setAdapter(mNormalQuickAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));

    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        MethodInfo info = (MethodInfo) adapter.getData().get(position);
        if (info == null) return;
        Intent intent = new Intent(mContext, NormalItemActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("item", info);
        intent.putExtra("bundle", bundle);
        startActivityForResult(intent, 100);
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

    //TODO:测试数据
    private List<MethodInfo> initNormalInfo() {
        List<MethodInfo> list = new ArrayList<>();

        list.add(new MethodInfo("绘制圆弧",
                R.drawable.icon_arc,
                getString(R.string.drawArc_desc),
                "drawArc", "drawArc"));
        list.add(new MethodInfo("绘制Bitmap",
                R.drawable.icon_bitmap,
                getString(R.string.drawBitmap_desc),
                "drawBitmap",
                "drawBitmap"));
        list.add(new MethodInfo("绘制颜色",
                R.drawable.icon_color,
                getString(R.string.drawColor_desc),
                "drawColor",
                "drawColor"));
        list.add(new MethodInfo("绘制圆形",
                R.drawable.icon_circle,
                getString(R.string.drawCircle_desc),
                "drawCircle",
                "drawCircle"));
        list.add(new MethodInfo("绘制线条",
                R.drawable.icon_line,
                getString(R.string.drawLine_desc),
                "drawLine", "drawLine"));
        list.add(new MethodInfo("绘制BitmapMesh",
                R.drawable.icon_empty,
                getString(R.string.drawBitmapMesh_desc),
                "drawBitmapMesh",
                "drawBitmapMesh"));
        list.add(new MethodInfo("绘制椭圆",
                R.drawable.icon_oval,
                getString(R.string.drawOval_desc),
                "drawOval",
                "drawOval"));
        list.add(new MethodInfo("绘制Path",
                R.drawable.icon_path,
                getString(R.string.drawPath_desc),
                "drawPath",
                "drawPath"));
        list.add(new MethodInfo("绘制Point",
                R.drawable.icon_point,
                getString(R.string.drawPoint_desc),
                "drawPoint",
                "drawPoint"));
        list.add(new MethodInfo("绘制Paint",
                R.drawable.icon_empty,
                getString(R.string.drawPaint_desc),
                "drawPaint",
                "drawPaint"));
        list.add(new MethodInfo("绘制Points",
                R.drawable.icon_points,
                getString(R.string.drawPoints_desc),
                "drawPoints",
                "drawPoints"));
        list.add(new MethodInfo("绘制矩形",
                R.drawable.icon_rect,
                getString(R.string.drawRect_desc),
                "drawRect",
                "drawRect"));
        list.add(new MethodInfo("绘制RGB",
                R.drawable.icon_color,
                getString(R.string.drawRGB_desc),
                "drawRGB",
                "drawRGB"));
        list.add(new MethodInfo("绘制圆角矩形",
                R.drawable.icon_rect,
                getString(R.string.drawRoundRect_desc),
                "drawRoundRect",
                "drawRoundRect"));
        list.add(new MethodInfo("绘制文本",
                R.drawable.icon_text,
                getString(R.string.drawText_desc),
                "drawText",
                "drawText"));
        list.add(new MethodInfo("绘制路径文本",
                R.drawable.icon_path_text,
                getString(R.string.drawTextOnPath_desc),
                "drawTextOnPath",
                "drawTextOnPath"));
        list.add(new MethodInfo("绘制TextRun",
                R.drawable.icon_empty,
                getString(R.string.drawTextRun_desc),
                "drawTextRun",
                "drawTextRun"));
        list.add(new MethodInfo("绘制Vertices",
                R.drawable.icon_empty,
                getString(R.string.drawVertices_desc),
                "drawVertices",
                "drawVertices"));
        return list;
    }
}
