package com.example.android_painter.view.chart;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;


import com.example.android_painter.R;
import com.example.android_painter.model.chart.TaiChiInfo;
import com.example.android_painter.model.event.EventBusAction;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;

/**
 * Created by ZhangXinmin on 2017/5/8.
 * Copyright (c) 2017 . All rights reserved.
 */

public class TaiChiChart extends View {
    private static final String TAG = TaiChiChart.class.getSimpleName();

    private Resources resources;
    private Paint backPaint;//绘制背景
    private Paint linePaint;//绘制线条

    private float screenHeight, screenWidth;//屏幕高度，屏幕宽度
    private float padding;
    private float candleWidth;//蜡烛宽度
    private float candleHeight;//蜡烛高度
    private float shadleLength;//影线长度
    private int total;//总个数
    private int bigCount, littleCount;//大圆半圆蜡烛数，小圆半圆蜡烛数;
    private float bRadius;//大圆半径
    private float bCircleX, bCircleY;//大圆圆心坐标
    private float lRadius;//小圆半径
    private float lUpCircleX, lUpCircleY;//小圆圆心坐标
    private float lDownCircleX, lDownCircleY;//小圆圆心坐标
    private float jRadius;//阴阳两级圆半径
    private Handler handler;
    private Random random;
    private boolean isUPShow, isDownShow;

    private List<TaiChiInfo> rightList;//大圆右侧数据
    private List<TaiChiInfo> drawRightList;//大圆右侧数据
    private List<TaiChiInfo> leftList;//大圆左侧数据
    private List<TaiChiInfo> drawLeftList;//大圆左侧数据

    public TaiChiChart(Context context) {
        this(context, null, 0);
        init();
    }

    public TaiChiChart(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
        init();
    }

    public TaiChiChart(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    //初始化参数
    private void init() {
        resources = getResources();
        //初始化画笔
        backPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        backPaint.setColor(Color.BLACK);
        backPaint.setStyle(Paint.Style.FILL);
        linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeWidth(2);
        handler = new Handler();
        random = new Random();

        total = 210;
        bigCount = total / 3;
        littleCount = bigCount / 2;
        candleWidth = resources.getDimensionPixelSize(R.dimen.candle_width);
        candleHeight = resources.getDimensionPixelSize(R.dimen.candle_height);
        padding = resources.getDimensionPixelSize(R.dimen.candle_padding);
        shadleLength = resources.getDimension(R.dimen.shadle_length);
        //数据集合
        rightList = new ArrayList<>();
        drawRightList = new ArrayList<>();
        leftList = new ArrayList<>();
        drawLeftList = new ArrayList<>();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int measureHeight = MeasureSpec.getSize(heightMeasureSpec);
        int measureWidth = MeasureSpec.getSize(widthMeasureSpec);

        //必须调用该方法，否则会报运行时异常
        setMeasuredDimension(measureWidth, measureHeight);

        //控件宽高
        screenHeight = getMeasuredHeight();
        screenWidth = getMeasuredWidth();
        //计算圆半径
        float len = screenHeight > screenWidth ? screenWidth : screenHeight;
        //大圆
        bRadius = len / 2 - padding;//大圆半径
        bCircleX = screenWidth / 2;
        bCircleY = screenHeight / 2;
        //小圆
        lRadius = bRadius / 2;//小圆半径
        lUpCircleX = bCircleX;//up
        lUpCircleY = padding + lRadius;
        lDownCircleX = bCircleX;
        lDownCircleY = bCircleY + lRadius;

        jRadius = bRadius / 6;//阴阳两级圆半径
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (rightList.isEmpty() || leftList.isEmpty())
            return;


        drawBackground(canvas);//绘制背景
        drawRight(canvas);//绘制右侧图形
        drawLeft(canvas);//绘制左侧图形

    }

    /**
     * 绘制左侧图形：
     * 包括：大圆左侧圆弧，上部小圆圆弧，极点圆。
     *
     * @param canvas
     */
    private void drawLeft(Canvas canvas) {
        //绘制左侧大圆弧+上部小圆弧
        int len = drawLeftList.size();
        for (int i = 0; i < len; i++) {
            TaiChiInfo info = drawLeftList.get(i);
            float coordinateX = info.getCoordinateX();
            float coordinateY = info.getCoordinateY();
            int color = info.getColor();
            //计算坐标
            float left = coordinateX - candleWidth / 2;
            float top = coordinateY - candleHeight / 2/* + random.nextInt(3)*/;
            float right = left + candleWidth;
            float bottom = top + candleHeight/* - random.nextInt(3)*/;
            linePaint.setColor(color);
            switch (color) {
                case Color.RED:
                    linePaint.setStyle(Paint.Style.FILL);
                    break;
                case Color.CYAN:
                    linePaint.setStyle(Paint.Style.STROKE);
                    break;
            }

            if (i % 3 == 0 && i % 5 == 0) {
                canvas.drawLine(coordinateX - candleWidth / 2, coordinateY,
                        coordinateX + candleWidth / 2, coordinateY, linePaint);

                canvas.drawLine(coordinateX, coordinateY - shadleLength - random.nextInt(2),
                        coordinateX, coordinateY + shadleLength + random.nextInt(2), linePaint);
            } else {
                canvas.drawRect(left, top, right, bottom, linePaint);
                //上影线
                canvas.drawLine(coordinateX, top - shadleLength - random.nextInt(4), coordinateX, top, linePaint);
                //上影线
                canvas.drawLine(coordinateX, bottom, coordinateX, bottom + shadleLength - random.nextInt(4), linePaint);
            }

        }

        if (len == leftList.size()) {
            //绘制上部极点
            backPaint.reset();
            backPaint.setColor(Color.CYAN);
            backPaint.setStyle(Paint.Style.FILL);
            backPaint.setAntiAlias(true);

            canvas.drawCircle(lUpCircleX, lUpCircleY, jRadius, backPaint);
        }

    }

    /**
     * 绘制右侧图形；
     * 包括：大圆右侧圆弧，下部小圆圆弧，极点圆。
     *
     * @param canvas
     */
    private void drawRight(Canvas canvas) {

        //绘制右侧大圆弧+下部小圆弧
        int len = drawRightList.size();

        for (int i = 0; i < len; i++) {
            TaiChiInfo info = drawRightList.get(i);
            float coordinateX = info.getCoordinateX();
            float coordinateY = info.getCoordinateY();
            int color = info.getColor();
            //计算坐标
            float left = coordinateX - candleWidth / 2;
            float top = coordinateY - candleHeight / 2/* + random.nextInt(3)*/;
            float right = left + candleWidth;
            float bottom = top + candleHeight/* - random.nextInt(3)*/;
            switch (color) {
                case Color.RED:
                    linePaint.setStyle(Paint.Style.FILL);
                    break;
                case Color.CYAN:
                    linePaint.setStyle(Paint.Style.STROKE);
                    break;
            }
            linePaint.setColor(color);

            if (i % 2 == 0 && i % 5 == 0) {
                canvas.drawLine(coordinateX - candleWidth / 2, coordinateY,
                        coordinateX + candleWidth / 2, coordinateY, linePaint);

                canvas.drawLine(coordinateX, coordinateY - shadleLength - random.nextInt(2),
                        coordinateX, coordinateY + shadleLength + random.nextInt(2), linePaint);
            } else {
                canvas.drawRect(left, top, right, bottom, linePaint);
                //上影线
                canvas.drawLine(coordinateX, top - shadleLength + random.nextInt(2), coordinateX, top, linePaint);
                //下影线
                canvas.drawLine(coordinateX, bottom, coordinateX, bottom + shadleLength - random.nextInt(2), linePaint);
            }
        }
        if (len == rightList.size()) {
            //绘制下部极点
            backPaint.reset();
            backPaint.setColor(Color.RED);
            backPaint.setStyle(Paint.Style.FILL);
            backPaint.setAntiAlias(true);

            canvas.drawCircle(lDownCircleX, lDownCircleY, jRadius, backPaint);
        }

    }

    //绘制背景
    private void drawBackground(Canvas canvas) {
        canvas.drawARGB(Color.alpha(Color.BLACK),
                Color.red(Color.BLACK),
                Color.green(Color.BLACK),
                Color.blue(Color.BLACK));
    }

    /**
     * 初始化TaiChi数据
     */
    public void initTaiChiData() {
        if (!drawLeftList.isEmpty()) drawLeftList.clear();
        if (!drawRightList.isEmpty()) drawRightList.clear();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                initRightArcData();//right
                initLeftArcData();//left
            }
        }, 250);

    }

    public void dynamicTaichiData(Timer timer) {
        int leftSize = drawLeftList.size();
        int rightSize = drawRightList.size();

        if (leftSize < leftList.size() && rightSize < rightList.size()) {
            drawLeftList.add(leftList.get(leftSize));
            drawRightList.add(rightList.get(rightSize));
        } else {
            timer.cancel();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    EventBus.getDefault().post(
                            new EventBusAction(EventBusAction.REMOVE_TAICHI));
                }
            }, 1000);
        }
        postInvalidate();
    }

    /**
     * 获取右侧圆弧蜡烛数据
     */
    private void initRightArcData() {
        if (!rightList.isEmpty()) rightList.clear();
        float perBig = 180.0f / bigCount;
        float perLittle = 180.0f / littleCount;
        //右侧大圆弧
        for (int i = 0; i < bigCount; i++) {
            TaiChiInfo taiChiInfo = new TaiChiInfo();
            double degree = perBig * i;
            taiChiInfo.setDegree(degree);
            taiChiInfo.setCoordinateX((float) (bCircleX + bRadius * Math.sin(degree * Math.PI / 180)));
            taiChiInfo.setCoordinateY((float) (bCircleY - bRadius * Math.cos(degree * Math.PI / 180)));
            if (random.nextInt(i + 1) % 2 == 0)
                taiChiInfo.setColor(Color.CYAN);
            else
                taiChiInfo.setColor(Color.RED);
            rightList.add(taiChiInfo);
        }
        //底部小圆弧
        for (int i = 0; i < littleCount; i++) {
            TaiChiInfo taiChiInfo = new TaiChiInfo();
            double degree = perLittle * i;
            taiChiInfo.setDegree(degree);
            taiChiInfo.setCoordinateX((float) (bCircleX - lRadius * Math.sin(degree * Math.PI / 180)));
            taiChiInfo.setCoordinateY((float) (bCircleY + lRadius + lRadius * Math.cos(degree * Math.PI / 180)));
            if (random.nextInt(i + 1) % 2 == 0)
                taiChiInfo.setColor(Color.CYAN);
            else
                taiChiInfo.setColor(Color.RED);
            rightList.add(taiChiInfo);
        }

    }

    /**
     * 获取左侧圆弧蜡烛数据
     */
    private void initLeftArcData() {
        if (!leftList.isEmpty()) leftList.clear();
        float perBig = 180.0f / bigCount;
        //左侧大圆弧
        for (int i = 0; i < bigCount; i++) {
            TaiChiInfo taiChiInfo = new TaiChiInfo();
            double degree = perBig * i;
            taiChiInfo.setDegree(degree);
            taiChiInfo.setCoordinateX((float) (bCircleX - bRadius * Math.sin(degree * Math.PI / 180)));
            taiChiInfo.setCoordinateY((float) (bCircleY + bRadius * Math.cos(degree * Math.PI / 180)));
            if (random.nextInt(i + 1) % 2 == 0)
                taiChiInfo.setColor(Color.CYAN);
            else
                taiChiInfo.setColor(Color.RED);
            leftList.add(taiChiInfo);
        }

        float perLittle = 180.0f / littleCount;
        //上部小圆弧
        for (int i = 0; i < littleCount; i++) {
            TaiChiInfo taiChiInfo = new TaiChiInfo();
            double degree = perLittle * i;
            taiChiInfo.setDegree(degree);
            taiChiInfo.setCoordinateX((float) (bCircleX + lRadius * Math.sin(degree * Math.PI / 180)));
            taiChiInfo.setCoordinateY((float) (bCircleY - lRadius - lRadius * Math.cos(degree * Math.PI / 180)));
            if (random.nextInt(i + 1) % 2 == 0)
                taiChiInfo.setColor(Color.CYAN);
            else
                taiChiInfo.setColor(Color.RED);
            leftList.add(taiChiInfo);
        }
    }

}
