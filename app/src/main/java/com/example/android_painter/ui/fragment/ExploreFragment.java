package com.example.android_painter.ui.fragment;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.android_painter.R;
import com.example.android_painter.model.ExploreInfo;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2016/10/11.
 * 探索界面
 */

public class ExploreFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = ExploreFragment.class.getSimpleName();

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private Handler mHandler;

    public static ExploreFragment newInstance() {
        return new ExploreFragment();
    }

    @Override
    protected Object setRootLayout() {
        return R.layout.fragment_explore;
    }

    @Override
    void initParamsAndValues() {
        mHandler = new Handler();

    }

    @Override
    void initViews(View rootView) {
        mSwipeRefreshLayout = rootView.findViewById(R.id.swipe_layout);
        //设置颜色
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorLightBlue, R.color.colorCycn,
                R.color.colorPurple);
        mRecyclerView = rootView.findViewById(R.id.recyclerview_explore);
        //init adapter
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_find, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //TODO:生成测试数据
    private List<ExploreInfo> initExploreData() {
        List<ExploreInfo> list = new ArrayList<>();
        //常规绘制
        ExploreInfo common = new ExploreInfo("常规绘制", getString(R.string.desc_common), R.drawable.ic_drawer);
        //裁剪绘制
        ExploreInfo clip = new ExploreInfo("范围裁切", getString(R.string.desc_clip), R.drawable.ic_clip);
        list.add(common);
        list.add(clip);
        return list;
    }

    @Override
    public void onRefresh() {

    }
}
