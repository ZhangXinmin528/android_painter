package com.example.android_painter.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by ZhangXinmin on 2018/2/7.
 * Copyright (c) 2018 . All rights reserved.
 * 市场行情页面适配器
 */

public class QuotationTabAdapter extends FragmentStatePagerAdapter {
    private List<String> dataList;

    public QuotationTabAdapter(FragmentManager fm, List<String> dataList) {
        super(fm);
        this.dataList = dataList;
    }

    @Override
    public Fragment getItem(int position) {
        return null;
    }

    @Override
    public int getCount() {
        return 0;
    }
}
