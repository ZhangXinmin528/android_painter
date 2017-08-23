package com.example.android_painter.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.android_painter.R;
import com.example.android_painter.model.ExploreInfo;

import java.util.List;

/**
 * Created by ZhangXinmin on 2017/8/23.
 * Copyright (c) 2017 . All rights reserved.
 * 探索页面适配器
 */

public class SimpleQuickAdapter extends BaseQuickAdapter<ExploreInfo, BaseViewHolder> {

    public SimpleQuickAdapter(@Nullable List<ExploreInfo> data) {
        super(R.layout.layout_explore_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ExploreInfo item) {
        helper.setText(R.id.tv_title, item.getTitle())
                .setImageResource(R.id.iv_pic, item.getResId())
                .setText(R.id.tv_desc, item.getDesc());
    }
}
