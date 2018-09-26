package com.zxm.android_painter.ui;

import android.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.view.MenuItem;

import com.zxm.android_painter.R;
import com.zxm.android_painter.ui.base.BaseActivity;
import com.zxm.android_painter.ui.fragment.SettingFragment;

/**
 * Created by ZhangXinmin on 2018/8/15.
 * Copyright (c) 2018 . All rights reserved.
 * 设置页面
 */
public class SettingActivity extends BaseActivity {
    @Override
    protected Object setLayout() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initParamsAndViews() {

    }

    @Override
    protected void initViews() {
        getFragmentManager().beginTransaction()
                .replace(R.id.framelayout_setting, SettingFragment.newInstance())
                .commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
