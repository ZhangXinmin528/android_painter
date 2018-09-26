package com.zxm.android_painter.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zxm.android_painter.R;
import com.zxm.android_painter.api.HttpApi;
import com.zxm.android_painter.model.NewsInfo;
import com.zxm.android_painter.model.TabInfo;
import com.zxm.android_painter.ui.adapter.NewsRecyclerAdapter;
import com.zxm.android_painter.ui.web.WebActivity;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zxm.libcommon.Logger;

import java.util.ArrayList;
import java.util.List;

import static com.zxm.android_painter.ui.web.WebActivity.PARAMS_URL;

/**
 * Created by Administrator on 2018/1/17.
 * Copyright (c) 2018 . All rights reserved.
 * 新闻列表
 */

public class NewsFragment extends BaseFragment implements OnRefreshListener, OnLoadMoreListener
        , BaseQuickAdapter.OnItemClickListener {

    private static final String TAG = NewsFragment.class.getSimpleName();

    public static final String PARAMS_ARGS = "args";

    private static final int PAGE_SIZE = 10;
    private int mPage = 1;

    private TabInfo mTabInfo;
    private SmartRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    private NewsRecyclerAdapter mAdapter;
    private List<NewsInfo> mDataList;

    public static NewsFragment newInstance(TabInfo tabInfo) {

        NewsFragment fragment = new NewsFragment();
        Bundle args = new Bundle();
        args.putSerializable(PARAMS_ARGS, tabInfo);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected Object setRootLayout() {
        return R.layout.fragment_news;
    }

    @Override
    public void initParamsAndValues() {
        Bundle args = getArguments();
        if (args != null) {
            if (args.containsKey(PARAMS_ARGS)) {
                mTabInfo = (TabInfo) args.getSerializable(PARAMS_ARGS);
            }
        }

        if (mTabInfo == null) {
            mTabInfo = new TabInfo();
        }

        mDataList = new ArrayList<>();
        mAdapter = new NewsRecyclerAdapter(mContext, mDataList);

    }

    @Override
    public void initViews(View rootView) {
        mRefreshLayout = rootView.findViewById(R.id.srLayout_news);
        mRefreshLayout.setEnableHeaderTranslationContent(false);//刷新时内容不偏移
        MaterialHeader materialHeader = (MaterialHeader) mRefreshLayout.getRefreshHeader();
        if (materialHeader != null) {
            materialHeader.setShowBezierWave(false);//关闭刷新时背景
        }
        mRefreshLayout.setOnRefreshListener(this);//刷新监听
        mRefreshLayout.setOnLoadMoreListener(this);//加载更多监听

        mRefreshLayout.setEnableLoadMore(true);//开启上拉加载更多
        mRefreshLayout.setEnableLoadMore(false);//惯性滑动底部自动加载更多
        mRefreshLayout.autoRefresh(200);

        mRecyclerView = rootView.findViewById(R.id.recyclerview_news);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);
    }

    /**
     * 请求网络数据
     *
     * @param isRefresh 是否自动刷新
     * @hide
     */
    private void requestDataFromNet(final boolean isRefresh) {

        if (TextUtils.isEmpty(mTabInfo.getKey())) {
            return;
        }
        final String url = HttpApi.NEWS_API + mTabInfo.getKey() + "/" + PAGE_SIZE + "/" + mPage;
        OkGo.<String>get(url)
                .execute(new StringCallback() {

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        Toast.makeText(mContext, "网络请求失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        if (response != null) {
                            parseNewsData(response.body(), isRefresh);

                        }
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        if (isRefresh) {
                            mRefreshLayout.finishRefresh();
                            mRefreshLayout.resetNoMoreData();
                        } else {
                            mRefreshLayout.finishLoadmore();
                        }
                    }
                });
    }

    /**
     * 解析请求的数据
     *
     * @param body
     */
    private void parseNewsData(@NonNull String body, @NonNull boolean isRefresh) {
        if (!TextUtils.isEmpty(body)) {
            JSONObject json = JSON.parseObject(body);
            final boolean error = json.getBooleanValue("error");
            if (!error) {
                final JSONArray results = json.getJSONArray("results");
                final List<NewsInfo> list = JSON.parseArray(results.toJSONString(), NewsInfo.class);
                if (list != null && !list.isEmpty()) {
                    if (isRefresh && !mDataList.isEmpty()) {
                        mDataList.clear();
                    }
                    mDataList.addAll(list);
                    mAdapter.notifyDataSetChanged();
                    mPage++;
                }
            } else {
                Logger.e(TAG, "未请求到数据");
            }
        } else {
            Logger.e(TAG, "未请求到数据");
        }

    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        mPage = 1;
        requestDataFromNet(true);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        NewsInfo info = (NewsInfo) adapter.getData().get(position);
        if (info != null) {
            Intent web = new Intent(mContext, WebActivity.class);
            web.putExtra(PARAMS_URL, info.getUrl());
            startActivity(web);
        }
    }

    @Override
    public void onLoadMore(RefreshLayout refreshLayout) {
        requestDataFromNet(false);
    }
}
