package com.zxm.android_painter.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.ComposeShader;
import android.graphics.LightingColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.zxm.android_painter.R;

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
        mTextPaint.setStrokeWidth(2);
        mTextPaint.setTextSize(16);
        mTextPaint.setColor(Color.BLACK);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (TextUtils.isEmpty(mDrawType))
            return;

        switch (mDrawType) {
            case "setColor":
                setColor(canvas);
                break;
            case "setARGB":
                setARGB(canvas);
                break;
            //线性渐变
            case "LinearGradient":
                setLinearGradientShader(canvas);
                break;
            //辐射渐变
            case "RadialGradient":
                setRadialGradientShader(canvas);
                break;
            //扫描渐变
            case "SweepGradient":
                setSweepGradientShader(canvas);
                break;
            //图片着色器
            case "BitmapShader":
                setBitmapShader(canvas);
                break;
            //组合着色器
            case "ComposeShader":
                setComposeShader(canvas);
                break;
            //LightingColorFilter
            case "LightingColorFilter":
                setLightingColorFilter(canvas);
                break;
            //PorterDuffColorFilter
            case "PorterDuffColorFilter":
                setPorterDuffColorFilter(canvas);
                break;
            //ColorMatrixColorFilter
            case "ColorMatrixColorFilter":
                setColorMatrixColorFilter(canvas);
                break;
        }
    }

    private void setColorMatrixColorFilter(Canvas canvas) {
    }

    /**
     * Set or clear the paint's colorfilter{@link PorterDuffColorFilter}, returning the parameter.
     *
     * @param canvas
     */
    private void setPorterDuffColorFilter(Canvas canvas) {
        PorterDuffColorFilter colorFilter = new PorterDuffColorFilter(Color.RED, PorterDuff.Mode.SCREEN);
        mPaint.reset();
        mPaint.setColorFilter(colorFilter);
        Bitmap girl = BitmapFactory.decodeResource(getResources(), R.drawable.head_portrait);
        canvas.drawBitmap(girl, 50, 50, mPaint);
    }

    /**
     * Set or clear the paint's colorfilter{@link LightingColorFilter}, returning the parameter.
     *
     * @param canvas
     */
    private void setLightingColorFilter(Canvas canvas) {
        LightingColorFilter colorFilter = new LightingColorFilter(Color.CYAN, Color.BLUE);
        mPaint.reset();
        mPaint.setColorFilter(colorFilter);
        Bitmap girl = BitmapFactory.decodeResource(getResources(), R.drawable.head_portrait);
        canvas.drawBitmap(girl, 50, 50, mPaint);
    }

    /**
     * Set or clear the shader object,while the shader is ComposeShader.
     * 是不是有点滤镜的效果
     *
     * @param canvas
     */
    private void setComposeShader(Canvas canvas) {
        //girl
        Bitmap girl = BitmapFactory.decodeResource(getResources(), R.drawable.head_portrait);
        Shader girlShader = new BitmapShader(girl, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);

        //heart
        Shader shader = new LinearGradient(
                50, 50, 650, 650,
                Color.parseColor("#FF0000"),
                Color.parseColor("#00FFFF"),
                Shader.TileMode.CLAMP);

        Shader composeShader = new ComposeShader(girlShader, shader, PorterDuff.Mode.LIGHTEN);
        mPaint.reset();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setShader(composeShader);

        canvas.drawRect(50, 50, 850, 850, mPaint);

        //重叠部分
        mPaint.reset();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(2);

        canvas.drawRect(50, 50, 650, 650, mPaint);
    }

    /**
     * Set or clear the shader object,while the shader is BitmapShader.
     * 和{@link Canvas}的canvas.drawBitmap()实现效果一致，是不是用这个方法可是轻松实现自定义头像的操作
     *
     * @param canvas
     */
    private void setBitmapShader(Canvas canvas) {
        mPaint.reset();
        mPaint.setStyle(Paint.Style.FILL);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.head_portrait);
        Shader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        mPaint.setShader(shader);

        canvas.drawCircle(400, 350, 250, mPaint);
    }

    /**
     * Set or clear the shader object,while the shader is SweepGradient.
     *
     * @param canvas
     */
    private void setSweepGradientShader(Canvas canvas) {
        mPaint.reset();
        mPaint.setStyle(Paint.Style.FILL);
        Shader shader = new SweepGradient(300, 300, Color.parseColor("#E91E63"),
                Color.parseColor("#2196F3"));
        mPaint.setShader(shader);
        canvas.drawCircle(300, 300, 200, mPaint);
    }

    /**
     * Set or clear the shader object,while the shader is RadialGradient.
     *
     * @param canvas
     */
    private void setRadialGradientShader(Canvas canvas) {
        mPaint.reset();
        mPaint.setStyle(Paint.Style.FILL);
        Shader shader = new RadialGradient(
                400, 400, 100,
                Color.parseColor("#E91E63"),
                Color.parseColor("#2196F3"), Shader.TileMode.REPEAT);
        mPaint.setShader(shader);
        //绘制圆
        canvas.drawCircle(400, 400, 400, mPaint);
    }


    /**
     * Set or clear the shader object,while the shader is LinearGradient.
     * 1.注意渐变限定区域；
     * 2.注意渐变着色规则{@link android.graphics.Shader.TileMode}
     *
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
        canvas.drawRect(100, 100, 600, 600, mPaint);

        mPaint.reset();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(2);
        mPaint.setColor(Color.BLUE);
        //绘制矩形
        canvas.drawRect(100, 100, 200, 200, mPaint);
    }

    /**
     * Helper to setColor(), that takes a,r,g,b and constructs the color int
     *
     * @param canvas
     */
    private void setARGB(Canvas canvas) {
        mPaint.reset();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setARGB(100, 255, 0, 0);

        //绘制矩形
        canvas.drawRect(30, 30, 230, 200, mPaint);
    }

    /**
     * Set the paint's color.
     *
     * @param canvas
     */
    private void setColor(Canvas canvas) {
        mPaint.reset();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.parseColor("#009688"));
        //绘制矩形
        canvas.drawRect(30, 30, 230, 200, mPaint);
    }

    /**
     * 设置绘制类型
     *
     * @param drawType
     */
    public void setDrawType(String drawType) {
        this.mDrawType = drawType;
    }
}
