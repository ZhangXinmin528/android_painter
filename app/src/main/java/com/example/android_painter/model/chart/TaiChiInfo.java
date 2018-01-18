package com.example.android_painter.model.chart;

/**
 * Created by ZhangXinmin on 2017/5/9.
 * Copyright (c) 2017 . All rights reserved.
 * 太极图：每一个圆上的小蜡烛
 */

public class TaiChiInfo {
    private double degree;//角度
    private int color;//颜色
    private float coordinateX;//X坐标
    private float coordinateY;//Y坐标

    public TaiChiInfo() {
    }

    public TaiChiInfo(double degree, int color, float coordinateX, float coordinateY) {
        this.degree = degree;
        this.color = color;
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
    }

    public double getDegree() {
        return degree;
    }

    public void setDegree(double degree) {
        this.degree = degree;
    }

    public float getCoordinateX() {
        return coordinateX;
    }

    public void setCoordinateX(float coordinateX) {
        this.coordinateX = coordinateX;
    }

    public float getCoordinateY() {
        return coordinateY;
    }

    public void setCoordinateY(float coordinateY) {
        this.coordinateY = coordinateY;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "TaiChiInfo{" +
                "degree=" + degree +
                ", color=" + color +
                ", coordinateX=" + coordinateX +
                ", coordinateY=" + coordinateY +
                '}';
    }
}
