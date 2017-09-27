package com.example.android_painter.model.chart;

import java.io.Serializable;

/**
 * Created by ZhangXinmin on 2017/3/31.
 * Copyright (c) 2017 . All rights reserved.
 * 大战略-大盘柱状图数据类:包含值和颜色两个属性
 */

public class HistogramInfo implements Serializable {
    private int type;//颜色梯度类型：0--{blue-green}，1--{red-yellow}；
    private float value;//取值

    public HistogramInfo() {
    }

    public HistogramInfo(int type, float value) {
        this.type = type;
        this.value = value;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }
}
