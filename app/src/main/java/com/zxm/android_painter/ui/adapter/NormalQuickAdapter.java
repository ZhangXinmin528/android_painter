package com.zxm.android_painter.ui.adapter;

import android.annotation.SuppressLint;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zxm.android_painter.R;
import com.zxm.android_painter.model.MethodInfo;

import java.util.List;

/**
 * Created by ZhangXinmin on 2017/8/25.
 * Copyright (c) 2017 . All rights reserved.
 * 正常绘制适配器
 */

public class NormalQuickAdapter extends BaseQuickAdapter<MethodInfo, BaseViewHolder> {

    public NormalQuickAdapter(@Nullable List<MethodInfo> data) {
        super(R.layout.layout_normal_item, data);
    }

    @SuppressLint("StringFormatInvalid")
    @Override
    protected void convert(BaseViewHolder helper, MethodInfo item) {

        helper.setText(R.id.tv_normal_title, item.getName())
                .setImageResource(R.id.iv_icon, item.getIconId())
                .setText(R.id.tv_nomal_method,
                        mContext.getString(R.string.normal_method, item.getMethod()))
                .setText(R.id.tv_normal_desc, mContext.getString(R.string.normal_method_desc, item.getDesc()))
                .getView(R.id.tv_normal_desc).setFocusable(true);
    }
}
