package com.example.android_painter.model;

import java.io.Serializable;

/**
 * Created by ZhangXinmin on 2017/9/18.
 * Copyright (c) 2017 . All rights reserved.
 * 启动页图片类
 */

public class ImageInfo implements Serializable {
    private static final long serialVersionUID = 6753210234564832868L;

    private String date;//开始日期
    private String url;//图片URL
    private String copyright;//描述

    public ImageInfo() {
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    @Override
    public String toString() {
        return "ImageInfo{" +
                "date='" + date + '\'' +
                ", url='" + url + '\'' +
                ", copyright='" + copyright + '\'' +
                '}';
    }
}
