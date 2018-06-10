package com.example.android_painter.ui.base;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

/**
 * Created by ZhangXinmin on 2017/9/17.
 * Copyright (c) 2017 . All rights reserved.
 * The base class of activity.
 */

public abstract class BaseActivity extends AppCompatActivity {


    protected Context mContext;
    protected Resources mResources;

    /**
     * set layout for activity
     *
     * @return
     */
    protected abstract Object setLayout();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //init base setting
        init();

        initParamsAndViews();

        if (setLayout() instanceof Integer) {
            setContentView((Integer) setLayout());
        } else if (setLayout() instanceof View) {
            setContentView((View) setLayout());
        } else {
            throw new RuntimeException("You must use the method of 'setLayout()' " +
                    "to bind view for activity! ");
        }


        initViews();

        requestDataFromNet();
    }

    /**
     * init params and values for activity
     */
    protected abstract void initParamsAndViews();

    /**
     * init views for activity
     */
    protected abstract void initViews();

    //init base setting
    private void init() {
        mContext = this;
        mResources = getResources();
        Logger.addLogAdapter(new AndroidLogAdapter());
    }

    /**
     * 请求网络数据
     */
    protected void requestDataFromNet() {

    }
}
