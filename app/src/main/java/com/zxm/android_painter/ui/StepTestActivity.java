package com.zxm.android_painter.ui;

import com.zxm.android_painter.R;
import com.zxm.android_painter.ui.base.BaseActivity;
import com.zxm.android_painter.view.chart.StepHistoryView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.zxm.android_painter.view.chart.StepHistoryView.TYPE_CHART_MONTH;


/**
 * Created by ZhangXinmin on 2020/7/1.
 * Copyright (c) 2020 . All rights reserved.
 */
public class StepTestActivity extends BaseActivity {

    private StepHistoryView mStepHistoryView;
    private List<Float> mWeekList;
    private List<Float> mMonthList;

    @Override
    protected Object setLayout() {
        return R.layout.activity_step_test;
    }

    @Override
    protected void initParamsAndViews() {
        mWeekList = new ArrayList<>();
        mMonthList = new ArrayList<>();

        final int maxValue = 50000;
        final Random random = new Random();
        for (int i = 0; i < 7; i++) {
            mWeekList.add((float) random.nextInt(maxValue));
        }

        for (int i = 0; i < 30; i++) {
            mMonthList.add((float) random.nextInt(maxValue));
        }
    }

    @Override
    protected void initViews() {
        mStepHistoryView = findViewById(R.id.stepview);
//        mStepHistoryView.setHistoryDada(TYPE_CHART_WEEK, mWeekList);
        mStepHistoryView.setHistoryDada(TYPE_CHART_MONTH, mMonthList);
    }
}
