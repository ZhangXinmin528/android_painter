package com.zxm.android_painter.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import java.lang.invoke.MutableCallSite;

/**
 * Created by ZhangXinmin on 2018/10/10.
 * Copyright (c) 2018 . All rights reserved.
 */
public class PaintView extends View {

    private Context mContext;

    private String mDrawType;

    private Paint mTextPaint;
    private Paint mPaint;


    public PaintView(Context context) {
        this(context, null, 0);
    }

    public PaintView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PaintView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initParams(context, attrs, defStyleAttr);
    }

    private void initParams(Context context, AttributeSet attrs, int defStyleAttr) {
        mContext = context;
        mDrawType = "";

        mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setStyle(Paint.Style.STROKE);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (TextUtils.isEmpty(mDrawType))
            return;

        switch (mDrawType){
            case "setColor":
                setColor(canvas);
                break;
            case "setARGB":
                setARGB(canvas);
                break;
            case "LinearGradient":
                setLinearGradientShader(canvas);
                break;
        }
    }

    /**
     *Set or clear the shader object.
     * 1.注意渐变限定区域；
     * 2.注意渐变着色规则{@link android.graphics.Shader.TileMode}
     * @param canvas
     */
    private void setLinearGradientShader(Canvas canvas) {
        mPaint.reset();
        mPaint.setStyle(Paint.Style.FILL);
        Shader shader = new LinearGradient(
                100, 100, 200, 200,
                Color.parseColor("#FF0000"),
                Color.parseColor("#00FFFF"),
                Shader.TileMode.REPEAT);
        mPaint.setShader(shader);

        //绘制矩形
        canvas.drawRect(100, 100, 600, 600,mPaint);

        mPaint.reset();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(2);
        mPaint.setColor(Color.BLUE);
        //绘制矩形
        canvas.drawRect(100, 100, 200, 200,mPaint);
    }

    /**
     * Helper to setColor(), that takes a,r,g,b and constructs the color int
     * @param canvas
     */
    private void setARGB(Canvas canvas) {
        mPaint.reset();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setARGB(100,255,0,0);

        //绘制矩形
        canvas.drawRect(30,30,230,200,mPaint);
    }

    /**
     * Set the paint's color.
     * @param canvas
     */
    private void setColor(Canvas canvas) {
        mPaint.reset();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.parseColor("#009688"));
        //绘制矩形
        canvas.drawRect(30,30,230,200,mPaint);
    }

    /**
     * 设置绘制类型
     * @param drawType
     */
    public void setDrawType(String drawType) {
        this.mDrawType = drawType;
    }
}
