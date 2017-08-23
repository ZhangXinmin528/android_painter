package com.example.android_painter.model;

import java.io.Serializable;

/**
 * Created by ZhangXinmin on 2017/7/13.
 * Copyright (c) 2017 . All rights reserved.
 */

public class ItemInfo implements Serializable {
    private String name;//姓名
    private String model;//类型

    public ItemInfo(String name, String model) {
        this.name = name;
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

    @Override
    public String toString() {
        return "ItemInfo{" +
                "name='" + name + '\'' +
                ", model='" + model + '\'' +
                '}';
    }
}
