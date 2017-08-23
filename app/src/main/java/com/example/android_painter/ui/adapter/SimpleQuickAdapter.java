package com.example.android_painter.ui.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.android_painter.model.ExploreInfo;

import java.util.List;

/**
 * Created by ZhangXinmin on 2017/8/23.
 * Copyright (c) 2017 . All rights reserved.
 * 简单适配器
 */

public class SimpleQuickAdapter extends BaseQuickAdapter<ExploreInfo,BaseViewHolder> {

    public SimpleQuickAdapter(@LayoutRes int layoutResId, @Nullable List<ExploreInfo> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ExploreInfo item) {

    }
}
