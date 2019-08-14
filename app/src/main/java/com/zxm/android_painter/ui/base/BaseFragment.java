package com.zxm.android_painter.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by ZhangXinmin on 2017/8/22.
 * Copyright (c) 2017 . All rights reserved.
 * Fragment基类
 */

public abstract class BaseFragment extends Fragment {

    protected Context mContext;

    protected View mRootView;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initParamsAndValues();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (setRootLayout() instanceof Integer) {
            mRootView = inflater.inflate((Integer) setRootLayout(), container, false);
        } else if (setRootLayout() instanceof View) {
            mRootView = (View) setRootLayout();
        } else {
            throw new ClassCastException("You must do setRootLayout method!");
        }
        if (mRootView != null) {
            initViews(mRootView);
        }

        return mRootView;
    }

    /**
     * 设置布局
     *
     * @return
     */
    protected abstract Object setRootLayout();

    /**
     * 初始化参数
     */
    public abstract void initParamsAndValues();

    /**
     * 初始化控件
     *
     * @param rootView
     */
    protected abstract void initViews(View rootView);

    final public <T extends View> T findViewById(@IdRes int id) {
        return mRootView.findViewById(id);
    }

    public void popBack() {
        BaseActivity activity = (BaseActivity) getActivity();
        if (activity != null) {
            activity.popBack(this);
        }
    }
}
