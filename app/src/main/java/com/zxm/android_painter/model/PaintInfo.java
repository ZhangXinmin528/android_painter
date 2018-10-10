package com.zxm.android_painter.model;

import android.support.annotation.IdRes;
import android.support.annotation.NonNull;

import java.io.Serializable;

/**
 * Created by ZhangXinmin on 2018/10/10.
 * Copyright (c) 2018 . All rights reserved.
 * Entity for PaintActivity.
 */
public final class PaintInfo implements Serializable {
    private String title;
    private String desc;
    private String method;

    public PaintInfo() {
    }

    public PaintInfo(@NonNull String title, @NonNull String desc, @NonNull String method) {
        this.title = title;
        this.desc = desc;
        this.method = method;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    @Override
    public String toString() {
        return "PaintInfo{" +
                "title='" + title + '\'' +
                ", desc='" + desc + '\'' +
                ", method='" + method + '\'' +
                '}';
    }
}
