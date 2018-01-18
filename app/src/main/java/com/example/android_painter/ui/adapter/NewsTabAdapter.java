package com.example.android_painter.ui.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.android_painter.model.TabInfo;
import com.example.android_painter.ui.fragment.NewsFragment;

import java.util.List;

/**
 * Created by Administrator on 2018/1/17.
 * Copyright (c) 2018 . All rights reserved.
 * 首页资讯导航适配器
 */

public class NewsTabAdapter extends FragmentStatePagerAdapter {
    private List<TabInfo> mDataList;

    public NewsTabAdapter(FragmentManager fm, List<TabInfo> dataList) {
        super(fm);
        this.mDataList = dataList;
    }

    @Override
    public Fragment getItem(int position) {
        return NewsFragment.newInstance(mDataList.get(position));
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mDataList.get(position).getTitle();
    }
}
