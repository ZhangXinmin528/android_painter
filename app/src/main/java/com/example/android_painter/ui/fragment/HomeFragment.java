package com.example.android_painter.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android_painter.R;
import com.example.android_painter.model.TabInfo;
import com.example.android_painter.ui.adapter.NewsTabAdapter;
import com.example.android_painter.util.LogUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2016/10/11.
 * 首页
 */

public class HomeFragment extends BaseFragment {
    public static final String TAG = HomeFragment.class.getSimpleName();

    private NewsTabAdapter mNewsTabAdapter;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    protected Object setRootLayout() {
        return R.layout.fragment_home;
    }

    @Override
    void initParamsAndValues() {
        List<TabInfo> tabList = new ArrayList<>();

        tabList.add(new TabInfo("Android", "Android"));
        tabList.add(new TabInfo("iOS", "IOS"));
        tabList.add(new TabInfo("前端", "前端开发"));
        tabList.add(new TabInfo("拓展资源", "开发杂谈"));

        FragmentManager manager = getChildFragmentManager();
//        LogUtil.logIWithTime(TAG+"-->initParamsAndValues:" + manager);
        mNewsTabAdapter = new NewsTabAdapter(manager, tabList);
    }

    @Override
    void initViews(View rootView) {
        Toolbar toolbar = rootView.findViewById(R.id.toolbar_home);
        toolbar.setTitle(R.string.all_home);
        ViewPager viewPager = rootView.findViewById(R.id.viewpager_home);
        viewPager.setAdapter(mNewsTabAdapter);
        TabLayout tabLayout = rootView.findViewById(R.id.tablayout_home);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setOffscreenPageLimit(2);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
