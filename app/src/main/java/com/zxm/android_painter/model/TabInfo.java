package com.zxm.android_painter.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/1/17.
 * Copyright (c) 2018 . All rights reserved.
 */

public class TabInfo implements Serializable {
    private String key;
    private String title;

    public TabInfo() {
    }

    public TabInfo(String key, String title) {
        this.key = key;
        this.title = title;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "TabInfo{" +
                "key='" + key + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
