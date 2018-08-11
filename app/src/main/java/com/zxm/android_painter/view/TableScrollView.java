package com.zxm.android_painter.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZhangXinmin on 2018/1/8.
 * Copyright (c) 2018 . All rights reserved.
 * 双向滑动的列表控件
 */

public class TableScrollView extends HorizontalScrollView {
    private static final String TAG = TableScrollView.class.getSimpleName();

    private static final List<TableScrollView> VIEWS = new ArrayList<>();

    public TableScrollView(Context context) {
        this(context, null, 0);
    }

    public TableScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TableScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 添加
     *
     * @param view
     */
    public void addScrollView(@NonNull TableScrollView view) {
        if (!VIEWS.contains(view)) {
            VIEWS.add(view);
        }
    }

    /**
     * 移除
     *
     * @param view
     */
    public void removeScrollView(@NonNull TableScrollView view) {
        if (VIEWS.contains(view)) {
            VIEWS.remove(view);
        }
    }

    public void clearViews() {
        if (!VIEWS.isEmpty()) {
            VIEWS.clear();
        }
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        final int size = VIEWS.size();
        for (int i = 0; i < size; i++) {
            VIEWS.get(i).smoothScrollTo(l, t);
        }
        super.onScrollChanged(l, t, oldl, oldt);
    }
}
