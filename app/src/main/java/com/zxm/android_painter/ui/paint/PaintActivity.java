package com.zxm.android_painter.ui.paint;

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
        mAdapter = new PaintAdapter(mContext, mDataList);
        mAdapter.setOnItemClickListener(this);
        initData();
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
    private List<PaintInfo> initData() {
        List<PaintInfo> list = new ArrayList<>();
        list.add(new PaintInfo("设置颜色",
                "setColor",
                ""));
        return list;
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        PaintInfo info = (PaintInfo) adapter.getItem(position);
        if (info != null) {

        }
    }
}
