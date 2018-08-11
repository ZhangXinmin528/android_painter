package com.zxm.android_painter.model;

import java.io.Serializable;

/**
 * Created by ZhangXinmin on 2017/8/23.
 * Copyright (c) 2017 . All rights reserved.
 */

public class ExploreInfo implements Serializable {
    private String title;
    private String desc;
    private int resId;
    private String url;

    public ExploreInfo(String title, String desc) {
        this.title = title;
        this.desc = desc;
    }

    public ExploreInfo(String title, String desc, String url) {
        this.title = title;
        this.desc = desc;
        this.url = url;
    }

    public ExploreInfo(String title, String desc, int resId) {
        this.title = title;
        this.desc = desc;
        this.resId = resId;
    }

    public ExploreInfo(String title, String desc, int resId, String url) {
        this.title = title;
        this.desc = desc;
        this.resId = resId;
        this.url = url;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    @Override
    public String toString() {
        return "ExploreInfo{" +
                "title='" + title + '\'' +
                ", desc='" + desc + '\'' +
                ", resId=" + resId +
                ", url='" + url + '\'' +
                '}';
    }
}
