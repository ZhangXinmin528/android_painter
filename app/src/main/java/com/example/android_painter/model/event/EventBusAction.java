package com.example.android_painter.model.event;



/**
 * Created by ZhangXinmin on 2017/4/19.
 * Copyright (c) 2017 . All rights reserved.
 * 图形分析和设置界面：EventBus消息传递类
 */

public class EventBusAction {
    public static final String REMOVE_TAICHI = "remove_taichi";//移除太极图

    private int type;//工具栏类型
    private String msg;//消息

    public EventBusAction(int type) {
        this.type = type;
    }

    public EventBusAction(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getType() {
        return type;
    }

    @Override
    public String toString() {
        return "EventBusAction{" +
                "msg='" + msg + '\'' +
                '}';
    }
}
