package com.zxm.android_painter.ui.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.zxm.android_painter.R;
import com.zxm.android_painter.ui.adapter.QuotationTabAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Created by Administrator on 2016/10/11.
 * 行情界面：
 * 主要为最新的沪深行情
 */

public class QuotationsFragment extends BaseFragment {
    public static final String TAG = QuotationsFragment.class.getName();

    private QuotationTabAdapter mAdapter;

    public static QuotationsFragment newInstance() {
        return new QuotationsFragment();
    }

    @Override
    protected Object setRootLayout() {
        return R.layout.fragment_quotation;
    }


    @Override
    public void initParamsAndValues() {

        final List<String> title = new ArrayList<>();
        String[] arr = mContext.getResources().getStringArray(R.array.arrays_quotation);
        title.addAll(Arrays.asList(arr));
        mAdapter = new QuotationTabAdapter(getChildFragmentManager(), title);
    }

    @Override
    public void initViews(View rootView) {
        Toolbar toolbar = rootView.findViewById(R.id.toolbar_quotation);
        toolbar.setTitle(R.string.all_quotation_title);

        ViewPager viewPager = rootView.findViewById(R.id.viewpager_quotation);
        viewPager.setAdapter(mAdapter);
        TabLayout tabLayout = rootView.findViewById(R.id.tablayout_quotation);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setOffscreenPageLimit(2);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_quotation, menu);
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

}
