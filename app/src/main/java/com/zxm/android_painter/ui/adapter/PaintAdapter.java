package com.zxm.android_painter.ui.adapter;

import android.content.Context;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zxm.android_painter.R;
import com.zxm.android_painter.model.PaintInfo;
import com.zxm.android_painter.ui.paint.PaintActivity;

import java.util.List;

/**
 * Created by ZhangXinmin on 2018/10/10.
 * Copyright (c) 2018 . All rights reserved.
 * Adapter for PaintActivity{@link PaintActivity}.
 */
public class PaintAdapter extends BaseQuickAdapter<PaintInfo, BaseViewHolder> {

    private Context mContext;

    public PaintAdapter(Context context, @Nullable List<PaintInfo> data) {
        super(R.layout.layout_paint_item, data);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, PaintInfo item) {
        helper.setText(R.id.tv_paint_title, item.getTitle())
                .setText(R.id.tv_paint_method, mContext.getString(R.string.normal_method, item.getMethod()))
                .setText(R.id.tv_paint_method, mContext.getString(R.string.normal_method_desc, item.getDesc()));

    }
}
