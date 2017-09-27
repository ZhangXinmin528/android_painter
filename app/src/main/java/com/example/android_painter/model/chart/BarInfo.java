package com.example.android_painter.model.chart;

import java.io.Serializable;

/**
 * Created by ZhangXinmin on 2017/2/12.
 * Copyright (c) 2017 . All rights reserved.
 * 3D柱状图实体类
 */

public class BarInfo implements Serializable {
    private String mLable;//标签
    private float mValue;//值
    private boolean isMarked;//是否沪深大盘

    public BarInfo(String lable, float value, boolean isMarked) {
        this.mLable = lable;
        this.mValue = value;
        this.isMarked = isMarked;
    }

    public boolean isMarked() {
        return isMarked;
    }

    public void setMarked(boolean marked) {
        isMarked = marked;
    }

    public String getmLable() {
        return mLable;
    }

    public void setmLable(String mLable) {
        this.mLable = mLable;
    }

    public float getmValue() {
        return mValue;
    }

    public void setmValue(float mValue) {
        this.mValue = mValue;
    }

    @Override
    public String toString() {
        return "BarInfo:" + mValue;
    }
}
