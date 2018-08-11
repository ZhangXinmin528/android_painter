package com.zxm.android_painter.model;

import java.io.Serializable;

/**
 * Created by ZhangXinmin on 2017/7/13.
 * Copyright (c) 2017 . All rights reserved.
 */

public class MethodInfo implements Serializable {
    private String name;//姓名
    private int iconId;//图片
    private String desc;//描述
    private String method;//方法
    private String model;//类型

    public MethodInfo() {
    }

    public MethodInfo(String name, int icon, String desc, String method, String model) {
        this.name = name;
        this.iconId = icon;
        this.desc = desc;
        this.method = method;
        this.model = model;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
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

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }

    @Override
    public String toString() {
        return "MethodInfo{" +
                "name='" + name + '\'' +
                ", iconId=" + iconId +
                ", desc='" + desc + '\'' +
                ", method='" + method + '\'' +
                ", model='" + model + '\'' +
                '}';
    }
}
