package com.zxm.android_painter.ui.text;

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
import com.zxm.android_painter.ui.paint.PaintItemActivity;

import java.util.ArrayList;
import java.util.List;

import static com.zxm.android_painter.ui.paint.PaintItemActivity.PARAMS_PAINT_INFO;
import static com.zxm.android_painter.ui.text.TextItemActivity.PARAMS_TEXT_INFO;

/**
 * Created by ZhangXinmin on 2018/10/25.
 * Copyright (c) 2018 . All rights reserved.
 * 自定义View:文字绘制
 */
public class TextActivity extends BaseActivity implements BaseQuickAdapter.OnItemClickListener {

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
            actionBar.setTitle(R.string.canvas_text);
        }

        mRecyclerView = findViewById(R.id.recyclerview_paint);
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
                "drawText",
                "Draw the text, with origin at (x,y), using the specified paint.",
                "canvas.drawText()",
                "drawText"));

        mDataList.add(new DescriptionInfo(
                "StaticLayout的使用",
                "Draw the text, with origin at (x,y), using the specified paint.",
                "staticLayout.draw(canvas)",
                "StaticLayout"));

        mDataList.add(new DescriptionInfo(
                "setMultiStyle",
                "设置多种文本样式：删除线，下划线等",
                "--",
                "setMultiStyle"));
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        DescriptionInfo info = (DescriptionInfo) adapter.getItem(position);
        if (info != null) {
            Intent intent = new Intent(mContext, TextItemActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable(PARAMS_TEXT_INFO, info);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }
}
