package com.zxm.android_painter.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.ComposePathEffect;
import android.graphics.ComposeShader;
import android.graphics.CornerPathEffect;
import android.graphics.DashPathEffect;
import android.graphics.DiscretePathEffect;
import android.graphics.EmbossMaskFilter;
import android.graphics.LightingColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.PathEffect;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.PorterDuffXfermode;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SumPathEffect;
import android.graphics.SweepGradient;
import android.graphics.Xfermode;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.zxm.android_painter.R;

/**
 * Created by ZhangXinmin on 2018/10/10.
 * Copyright (c) 2018 . All rights reserved.
 * 画笔相关API{@link Paint} and {@link com.zxm.android_painter.ui.paint.PaintActivity}
 */
public class PaintView extends View {

    private Context mContext;
    private Resources mResources;

    private String mDrawType;

    private Paint mTextPaint;
    private float mTextSize;
    private Paint mPaint;
    private float mTextHeight;//文字高度
    //视图宽度
    private float mViewWidth;
    //半径
    private float mRadius;
    //绘制间隔
    private float mVerticalSpaceing;

    private float mViewPadding;

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
        mResources = getResources();
        mDrawType = "";

        //文字画笔
        mTextSize = mResources.getDimensionPixelSize(R.dimen.textsize_16sp);
        mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setColor(Color.BLACK);
        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        mTextHeight = fontMetrics.bottom - fontMetrics.top;

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);

        mVerticalSpaceing = mResources.getDimensionPixelSize(R.dimen.draw_space);
        mRadius = mResources.getDimensionPixelSize(R.dimen.draw_radius);

        mViewPadding = mResources.getDimensionPixelSize(R.dimen.draw_padding);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(width, height);
        mViewWidth = getMeasuredWidth();
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
            //PorterDuffXfermode
            case "PorterDuffXfermode":
                setXfermode(canvas);
                break;
            //setAntiAlias
            case "setAntiAlias":
                setAntiAlias(canvas);
                break;
            //setStyle
            case "setStyle":
                setStyle(canvas);
                break;
            //setStrokeWidth
            case "setStrokeWidth":
                setStrokeWidth(canvas);
                break;
            //setStrokeCap
            case "setStrokeCap":
                setStrokeCap(canvas);
                break;
            //setStrokeJoin
            case "setStrokeJoin":
                setStrokeJoin(canvas);
                break;
            //setStrokeMiter
            case "setStrokeMiter":
                setStrokeMiter(canvas);
                break;
            //CornerPathEffect
            case "CornerPathEffect":
                setCornerPathEffect(canvas);
                break;
            //DiscretePathEffect
            case "DiscretePathEffect":
                setDiscretePathEffect(canvas);
                break;
            //DashPathEffect
            case "DashPathEffect":
                setDashPathEffect(canvas);
                break;
            //PathDashPathEffect
            case "PathDashPathEffect":
                setPathDashPathEffect(canvas);
                break;
            //SumPathEffect
            case "SumPathEffect":
                setSumPathEffect(canvas);
                break;
            //ComposePathEffect
            case "ComposePathEffect":
                setComposePathEffect(canvas);
                break;
            //setShadowLayer
            case "setShadowLayer":
                setShadowLayer(canvas);
                break;
            //BlurMaskFilter
            case "BlurMaskFilter":
                setBlurMaskFilter(canvas);
                break;
            //EmbossMaskFilter
            case "EmbossMaskFilter":
                setEmbossMaskFilter(canvas);
                break;
            //getFillPath
            case "getFillPath":
                getFillPath(canvas);
                break;
            //getTextPath
            case "getTextPath":
                getTextPath(canvas);
                break;
        }
    }

    /**
     * Return the path (outline) for the specified text.
     * @param canvas
     */
    private void getTextPath(Canvas canvas) {
        drawTitleText("getTextPath的使用", canvas);
        canvas.translate(0, mTextHeight + mVerticalSpaceing);

        final String content = "Android自定义View中Paint详解";

        mPaint.reset();
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(5);
        mPaint.setTextSize(mTextSize);
        //原文字
        drawTitleText("绘制效果", canvas);
        canvas.translate(0, mTextHeight);
        float textWidth = mPaint.measureText(content);
        float x = (mViewWidth - textWidth) / 2;
        canvas.drawText(content, x, 0, mPaint);
        canvas.translate(0, mTextHeight);

        //实际效果
        final Path textPath = new Path();
        mPaint.getTextPath(content, 0, content.length(), x, 0, textPath);

        mPaint.reset();
        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setTextSize(mTextSize);
        drawTitleText("实际效果", canvas);
        canvas.translate(0, mTextHeight);
        canvas.drawPath(textPath, mPaint);
    }

    /**
     * Applies any/all effects (patheffect, stroking) to src, returning the
     * result in dst.
     *
     * @param canvas
     */
    private void getFillPath(Canvas canvas) {
        drawTitleText("getFillPath的使用", canvas);
        canvas.translate(0, mTextHeight + mVerticalSpaceing);

        Path targetPath = new Path();
        targetPath.moveTo(mRadius / 4, mRadius / 4);
        targetPath.lineTo(mRadius, mRadius);
        targetPath.lineTo(mViewWidth / 2, 0);
        targetPath.lineTo(mViewWidth / 2 + mRadius / 2, mRadius);
        targetPath.lineTo(mViewWidth / 2 + mRadius * 2, 0);

        mPaint.reset();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.BLUE);
        mPaint.setStrokeWidth(10);
        drawTitleText("绘制效果", canvas);
        canvas.translate(0, mTextHeight);
        canvas.drawPath(targetPath, mPaint);

        canvas.translate(0, mRadius);
        drawTitleText("实际效果", canvas);
        canvas.translate(0, mTextHeight);
        final Path realAPath = new Path();
        //获取实际Path
        mPaint.getFillPath(targetPath, realAPath);
        mPaint.reset();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(1);
        canvas.drawPath(realAPath, mPaint);

        canvas.translate(0, mRadius + mViewPadding);
        drawTitleText("设置线宽或者PathEffect，绘制效果和实际效果会不同。", canvas);
    }

    /**
     * Set or clear the maskfilter{@link EmbossMaskFilter} object.
     *
     * @param canvas
     */
    private void setEmbossMaskFilter(Canvas canvas) {
        drawTitleText("EmbossMaskFilter的使用", canvas);
        canvas.translate(0, mTextHeight);

        mPaint.reset();
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(5);
        mPaint.setTextSize(mTextSize);
        //原文字
        drawTitleText("原文字", canvas);
        canvas.translate(0, mTextHeight);
        float textWidth = mPaint.measureText("Android自定义View中Paint详解");
        float x = (mViewWidth - textWidth) / 2;
        canvas.drawText("Android自定义View中Paint详解", x, 0, mPaint);

        setLayerType(View.LAYER_TYPE_SOFTWARE, mPaint);

        mPaint.reset();
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(5);
        mPaint.setTextSize(mTextSize);
        mPaint.setMaskFilter(new EmbossMaskFilter(new float[]{50, 50, 50}, 0, 1f, 5));
        canvas.translate(0, mTextHeight);
        //处理后
        drawTitleText("处理后", canvas);
        canvas.translate(0, mTextHeight);
        canvas.drawText("Android自定义View中Paint详解", x, 0, mPaint);
    }

    /**
     * Set or clear the maskfilter{@link BlurMaskFilter} object.
     * 模糊滤镜
     *
     * @param canvas
     */
    private void setBlurMaskFilter(Canvas canvas) {
        drawTitleText("BlurMaskFilter的使用", canvas);
        canvas.translate(0, mTextHeight);

        final float left = mViewWidth / 2 - mRadius / 2;
        final float top = 0;
        final float right = left + mRadius;
        final float bottom = top + mRadius;

        mPaint.reset();
        mPaint.setColor(Color.CYAN);
        //原图
        drawTitleText("原图", canvas);
        canvas.translate(0, mTextHeight);
        canvas.drawRect(left, top, right, bottom, mPaint);

        setLayerType(View.LAYER_TYPE_SOFTWARE, mPaint);

        //添加模糊效果:NORMAL
        canvas.translate(0, mRadius);
        drawTitleText("添加滤镜：NORMAL", canvas);
        canvas.translate(0, mTextHeight);
        mPaint.reset();
        mPaint.setColor(Color.CYAN);
        mPaint.setMaskFilter(new BlurMaskFilter(20, BlurMaskFilter.Blur.NORMAL));
        canvas.drawRect(left, top, right, bottom, mPaint);

        //添加模糊效果:INNER
        canvas.translate(0, mRadius);
        drawTitleText("添加滤镜：INNER", canvas);
        canvas.translate(0, mTextHeight);
        mPaint.reset();
        mPaint.setColor(Color.CYAN);
        mPaint.setMaskFilter(new BlurMaskFilter(20, BlurMaskFilter.Blur.INNER));
        canvas.drawRect(left, top, right, bottom, mPaint);

        //添加模糊效果:SOLID
        canvas.translate(0, mRadius);
        drawTitleText("添加滤镜：SOLID", canvas);
        canvas.translate(0, mTextHeight);
        mPaint.reset();
        mPaint.setColor(Color.CYAN);
        mPaint.setMaskFilter(new BlurMaskFilter(20, BlurMaskFilter.Blur.SOLID));
        canvas.drawRect(left, top, right, bottom, mPaint);

        //添加模糊效果:OUTER
        canvas.translate(0, mRadius);
        drawTitleText("添加滤镜：OUTER", canvas);
        canvas.translate(0, mTextHeight);
        mPaint.reset();
        mPaint.setColor(Color.CYAN);
        mPaint.setMaskFilter(new BlurMaskFilter(25, BlurMaskFilter.Blur.SOLID));
        canvas.drawRoundRect(new RectF(left, top, right, bottom), 10, 10, mPaint);

    }

    /**
     * This draws a shadow layer below the main layer, with the specified
     * offset and color, and blur radius.
     *
     * <p>
     * 1.若开启硬件加速，setShadowLayer()只支持文字的绘制；
     * 2.若要支持文字之外的绘制则需要关闭硬件加速；
     * 3.若shadowColor 是半透明的，阴影的透明度就使用 shadowColor 自己的透明度；
     * 4.如果 shadowColor 是不透明的，阴影的透明度就使用 paint 的透明度；
     * </p>
     *
     * @param canvas
     */
    private void setShadowLayer(Canvas canvas) {
        drawTitleText("ComposePathEffect的使用", canvas);
        canvas.translate(0, mTextHeight + mVerticalSpaceing);

        mPaint.reset();
        mPaint.setShadowLayer(10, 0, 0, Color.CYAN);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(2);
        mPaint.setTextSize(mTextSize);
        drawTitleText("绘制阴影", canvas);
        canvas.translate(0, mTextHeight);
        float textWidth = mPaint.measureText("Android自定义View中Paint详解");
        float x = (mViewWidth - textWidth) / 2;
        canvas.drawText("Android自定义View中Paint详解", x, 0, mPaint);
        //清除阴影
        mPaint.clearShadowLayer();
    }

    /**
     * Set or clear the patheffect{@link ComposePathEffect} object.
     *
     * @param canvas
     */
    private void setComposePathEffect(Canvas canvas) {
        drawTitleText("ComposePathEffect的使用", canvas);
        canvas.translate(0, mTextHeight + mVerticalSpaceing);

        Path targetPath = new Path();
        targetPath.moveTo(mRadius / 4, mRadius / 4);
        targetPath.lineTo(mRadius, mRadius);
        targetPath.lineTo(mViewWidth / 2, 0);
        targetPath.lineTo(mViewWidth / 2 + mRadius / 2, mRadius);
        targetPath.lineTo(mViewWidth / 2 + mRadius * 2, 0);

        //DiscretePathEffect
        PathEffect discretePathEffect = new DiscretePathEffect(10, 6);

        //CornerPathEffect
        PathEffect cornerPathEffect = new CornerPathEffect(20);

        //SumPathEffect:参数均非空
        final ComposePathEffect composePathEffect = new ComposePathEffect(discretePathEffect, cornerPathEffect);

        mPaint.reset();
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(2);
        mPaint.setPathEffect(discretePathEffect);
        drawTitleText("DiscretePathEffect图形", canvas);
        canvas.translate(0, mTextHeight);
        canvas.drawPath(targetPath, mPaint);

        canvas.translate(0, mRadius * 2);
        mPaint.reset();
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(2);
        mPaint.setPathEffect(cornerPathEffect);
        drawTitleText("CornerPathEffect图形", canvas);
        canvas.translate(0, mTextHeight);
        canvas.drawPath(targetPath, mPaint);

        canvas.translate(0, mRadius * 2);
        mPaint.reset();
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(2);
        mPaint.setPathEffect(composePathEffect);
        drawTitleText("ComposePathEffect图形", canvas);
        canvas.translate(0, mTextHeight);
        canvas.drawPath(targetPath, mPaint);
    }

    /**
     * Set or clear the patheffect{@link android.graphics.SumPathEffect} object.
     * 进行叠加绘制
     *
     * @param canvas
     */
    private void setSumPathEffect(Canvas canvas) {
        drawTitleText("SumPathEffect的使用", canvas);
        canvas.translate(0, mTextHeight + mVerticalSpaceing);

        Path targetPath = new Path();
        targetPath.moveTo(mRadius / 4, mRadius / 4);
        targetPath.lineTo(mRadius, mRadius);
        targetPath.lineTo(mViewWidth / 2, 0);
        targetPath.lineTo(mViewWidth / 2 + mRadius / 2, mRadius);
        targetPath.lineTo(mViewWidth / 2 + mRadius * 2, 0);

        //DiscretePathEffect
        PathEffect discretePathEffect = new DiscretePathEffect(10, 6);

        //CornerPathEffect
        PathEffect cornerPathEffect = new CornerPathEffect(20);

        //SumPathEffect:参数均非空
        final SumPathEffect sumPathEffect = new SumPathEffect(discretePathEffect, cornerPathEffect);

        mPaint.reset();
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(2);
        mPaint.setPathEffect(discretePathEffect);
        drawTitleText("DiscretePathEffect图形", canvas);
        canvas.translate(0, mTextHeight);
        canvas.drawPath(targetPath, mPaint);

        canvas.translate(0, mRadius * 2);
        mPaint.reset();
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(2);
        mPaint.setPathEffect(cornerPathEffect);
        drawTitleText("CornerPathEffect图形", canvas);
        canvas.translate(0, mTextHeight);
        canvas.drawPath(targetPath, mPaint);

        canvas.translate(0, mRadius * 2);
        mPaint.reset();
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(2);
        mPaint.setPathEffect(sumPathEffect);
        drawTitleText("SumPathEffect图形", canvas);
        canvas.translate(0, mTextHeight);
        canvas.drawPath(targetPath, mPaint);
    }

    /**
     * Set or clear the patheffect{@link PathDashPathEffect} object.
     *
     * @param canvas
     */
    private void setPathDashPathEffect(Canvas canvas) {
        drawTitleText("PathDashPathEffect的使用", canvas);
        canvas.translate(0, mTextHeight + mVerticalSpaceing);

        Path targetPath = new Path();
        targetPath.moveTo(mRadius / 4, mRadius / 4);
        targetPath.lineTo(mRadius, mRadius);
        targetPath.lineTo(mViewWidth / 2, 0);
        targetPath.lineTo(mViewWidth / 2 + mRadius / 2, mRadius);
        targetPath.lineTo(mViewWidth / 2 + mRadius * 2, 0);

        final Path shapePath = new Path();
        shapePath.addCircle(0, 0, 10, Path.Direction.CCW);

        PathEffect pathEffect = new PathDashPathEffect(shapePath, 30, 0,
                PathDashPathEffect.Style.TRANSLATE);

        mPaint.reset();
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(2);
        drawTitleText("原图形", canvas);
        canvas.translate(0, mTextHeight);
        canvas.drawPath(targetPath, mPaint);

        canvas.translate(0, mRadius * 2);
        mPaint.reset();
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(2);
        mPaint.setPathEffect(pathEffect);
        drawTitleText("新图形", canvas);
        canvas.translate(0, mTextHeight);
        canvas.drawPath(targetPath, mPaint);
    }

    /**
     * Set or clear the patheffect{@link DashPathEffect} object.
     *
     * @param canvas
     */
    private void setDashPathEffect(Canvas canvas) {
        drawTitleText("DiscretePathEffect的使用", canvas);
        canvas.translate(0, mTextHeight + mVerticalSpaceing);

        Path targetPath = new Path();
        targetPath.moveTo(mRadius / 4, mRadius / 4);
        targetPath.lineTo(mRadius, mRadius);
        targetPath.lineTo(mViewWidth / 2, 0);
        targetPath.lineTo(mViewWidth / 2 + mRadius / 2, mRadius);
        targetPath.lineTo(mViewWidth / 2 + mRadius * 2, 0);

        PathEffect pathEffect = new DashPathEffect(new float[]{20, 8, 6, 8}, 0);

        mPaint.reset();
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(2);
        drawTitleText("原图形", canvas);
        canvas.translate(0, mTextHeight);
        canvas.drawPath(targetPath, mPaint);

        canvas.translate(0, mRadius * 2);
        mPaint.reset();
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(2);
        mPaint.setPathEffect(pathEffect);
        drawTitleText("新图形", canvas);
        canvas.translate(0, mTextHeight);
        canvas.drawPath(targetPath, mPaint);
    }

    /**
     * Set or clear the patheffect{@link DiscretePathEffect} object.
     *
     * @param canvas
     */
    private void setDiscretePathEffect(Canvas canvas) {
        drawTitleText("DiscretePathEffect的使用", canvas);
        canvas.translate(0, mTextHeight + mVerticalSpaceing);

        Path targetPath = new Path();
        targetPath.moveTo(mRadius / 4, mRadius / 4);
        targetPath.lineTo(mRadius, mRadius);
        targetPath.lineTo(mViewWidth / 2, 0);
        targetPath.lineTo(mViewWidth / 2 + mRadius / 2, mRadius);
        targetPath.lineTo(mViewWidth / 2 + mRadius * 2, 0);

        PathEffect pathEffect = new DiscretePathEffect(10, 2);

        mPaint.reset();
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(2);
        drawTitleText("原图形", canvas);
        canvas.translate(0, mTextHeight);
        canvas.drawPath(targetPath, mPaint);

        canvas.translate(0, mRadius * 2);
        mPaint.reset();
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(2);
        mPaint.setPathEffect(pathEffect);
        drawTitleText("新图形", canvas);
        canvas.translate(0, mTextHeight);
        canvas.drawPath(targetPath, mPaint);
    }

    /**
     * Set or clear the patheffect{@link CornerPathEffect} object.
     *
     * @param canvas
     */
    private void setCornerPathEffect(Canvas canvas) {
        drawTitleText("CornerPathEffect的使用", canvas);
        canvas.translate(0, mTextHeight + mVerticalSpaceing);

        Path targetPath = new Path();
        targetPath.moveTo(mRadius / 4, mRadius / 4);
        targetPath.lineTo(mRadius, mRadius);
        targetPath.lineTo(mViewWidth / 2, 0);
        targetPath.lineTo(mViewWidth / 2 + mRadius / 2, mRadius);
        targetPath.lineTo(mViewWidth / 2 + mRadius * 2, 0);

        PathEffect pathEffect = new CornerPathEffect(20);

        mPaint.reset();
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(2);
        drawTitleText("原图形", canvas);
        canvas.translate(0, mTextHeight);
        canvas.drawPath(targetPath, mPaint);

        canvas.translate(0, mRadius * 2);
        mPaint.reset();
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(2);
        mPaint.setPathEffect(pathEffect);
        drawTitleText("新图形", canvas);
        canvas.translate(0, mTextHeight);
        canvas.drawPath(targetPath, mPaint);
    }

    /**
     * Set the paint's stroke miter value.
     *
     * @param canvas
     */
    private void setStrokeMiter(Canvas canvas) {
        drawTitleText("setStrokeJoin的使用", canvas);
        canvas.translate(0, mTextHeight + mVerticalSpaceing);

        Path pathA = new Path();
        pathA.moveTo(mViewWidth / 2 - mRadius, 0);
        pathA.lineTo(mViewWidth / 2 + mRadius, 0);
        pathA.lineTo(mViewWidth / 2, mRadius);

        Path pathB = new Path();
        pathB.moveTo(mViewWidth / 2 - mRadius, 0);
        pathB.lineTo(mViewWidth / 2 + mRadius, 0);
        pathB.lineTo(mViewWidth / 2 - mRadius, mRadius);

        mPaint.reset();
        mPaint.setColor(Color.RED);
        canvas.drawLine(mViewWidth / 2 + mRadius,
                0,
                mViewWidth / 2 + mRadius,
                mRadius * 7,
                mPaint);

        //PathA-默认补偿:4.0
        drawTitleText("Path(A)默认补偿:4.0", canvas);
        canvas.translate(0, mTextHeight);
        mPaint.reset();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.BLUE);
        mPaint.setStrokeWidth(25);
        canvas.drawPath(pathA, mPaint);


        canvas.translate(0, mRadius);
        //PathA-默认线头类型:BEVEL
        drawTitleText("Path(A)补偿值:10.0", canvas);
        canvas.translate(0, mTextHeight);
        mPaint.reset();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.BLUE);
        mPaint.setStrokeWidth(25);
        mPaint.setStrokeMiter(10);
        canvas.drawPath(pathA, mPaint);

        canvas.translate(0, (mRadius * 2));
        //PathB-默认补偿:4.0
        drawTitleText("Path(B)默认补偿:4.0", canvas);
        canvas.translate(0, mTextHeight);
        mPaint.reset();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.BLUE);
        mPaint.setStrokeWidth(25);
        canvas.drawPath(pathB, mPaint);


        canvas.translate(0, mRadius);
        //PathB-
        drawTitleText("Path(B)补偿值:10.0", canvas);
        canvas.translate(0, mTextHeight);
        mPaint.reset();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.BLUE);
        mPaint.setStrokeWidth(25);
        mPaint.setStrokeMiter(10);
        canvas.drawPath(pathB, mPaint);
    }

    /**
     * Set the paint's Join.The default is MITER.
     * 设置拐角的形状。有三个值可以选择：MITER 尖角、 BEVEL 平角和 ROUND 圆角。默认为 MITER
     *
     * @param canvas
     */
    private void setStrokeJoin(Canvas canvas) {
        drawTitleText("setStrokeJoin的使用", canvas);
        canvas.translate(0, mTextHeight + mVerticalSpaceing);

        final float left = mViewWidth / 2 - mRadius;
        final float top = 0;
        final float right = left + mRadius * 2;
        final float bottom = top + mRadius;

        //默认线头类型:MITER
        drawTitleText("默认连接线型:MITER", canvas);
        canvas.translate(0, mTextHeight);
        mPaint.reset();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.BLUE);
        mPaint.setStrokeWidth(25);
        canvas.drawRect(left, top, right, bottom, mPaint);

        canvas.translate(0, mRadius * 2);
        //默认线头类型:BEVEL
        drawTitleText("连接线型:BEVEL", canvas);
        canvas.translate(0, mTextHeight);
        mPaint.reset();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.BLUE);
        mPaint.setStrokeWidth(25);
        mPaint.setStrokeJoin(Paint.Join.BEVEL);
        canvas.drawRect(left, top, right, bottom, mPaint);

        canvas.translate(0, mRadius * 2);
        //默认线头类型:ROUND
        drawTitleText("连接线型:ROUND", canvas);
        canvas.translate(0, mTextHeight);
        mPaint.reset();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.BLUE);
        mPaint.setStrokeWidth(25);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        canvas.drawRect(left, top, right, bottom, mPaint);

    }

    /**
     * Set the paint's Cap.The default is BUTT.
     * 线头形状有三种：BUTT 平头、ROUND 圆头、SQUARE 方头
     *
     * @param canvas
     */
    private void setStrokeCap(Canvas canvas) {
        drawTitleText("setStrokeCap的使用", canvas);
        canvas.translate(0, mTextHeight + mVerticalSpaceing);

        final float startX = mVerticalSpaceing;
        final float startY = 0;
        final float stopX = startX + mViewWidth / 2;
        final float stopY = startY;

        //默认线头类型:BUTT
        drawTitleText("默认线条宽度", canvas);
        canvas.translate(0, mTextHeight);
        mPaint.reset();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(15);
        canvas.drawLine(startX, startY, stopX, stopY, mPaint);
        //绘制终点线
        mPaint.reset();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.RED);
        canvas.drawLine(stopX, startY - 100, stopX, startY + 300, mPaint);

        //线头类型:ROUND
        drawTitleText("线头类型:ROUND", canvas);
        canvas.translate(0, mTextHeight);
        mPaint.reset();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(15);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        canvas.drawLine(startX, startY, stopX, stopY, mPaint);

        //线头类型:SQUARE
        drawTitleText("线头类型:SQUARE", canvas);
        canvas.translate(0, mTextHeight);
        mPaint.setStrokeCap(Paint.Cap.SQUARE);
        canvas.drawLine(startX, startY, stopX, stopY, mPaint);

    }

    /**
     * Set the width for stroking.
     * 默认的情况下，画笔绘制线条宽度是0.0。
     * Google 在文档中把线条宽度为 0 时称作「hairline mode（发际线模式）」
     *
     * @param canvas
     */
    private void setStrokeWidth(Canvas canvas) {
        drawTitleText("setStrokeWidth的使用", canvas);
        canvas.translate(0, mTextHeight + mVerticalSpaceing);

        //默认线条宽度：0.0
        drawTitleText("默认线条宽度", canvas);
        canvas.translate(0, mTextHeight + mVerticalSpaceing);
        mPaint.reset();
        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Paint.Style.STROKE);
        final float cx = mViewWidth / 2;
        final float cy = mRadius;
        canvas.drawCircle(cx, cy, mRadius, mPaint);
        canvas.translate(0, mRadius * 2 + mVerticalSpaceing);

        //线条宽度:1
        drawTitleText("setStrokeWidth(1)", canvas);
        canvas.translate(0, mTextHeight + mVerticalSpaceing);
        mPaint.reset();
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(1);
        canvas.drawCircle(cx, cy, mRadius, mPaint);
        canvas.translate(0, mRadius * 2 + mVerticalSpaceing);

        //线条宽度:5
        drawTitleText("setStrokeWidth(5)", canvas);
        canvas.translate(0, mTextHeight + mVerticalSpaceing);
        mPaint.reset();
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5);
        canvas.drawCircle(cx, cy, mRadius, mPaint);
        canvas.translate(0, mRadius * 2 + mVerticalSpaceing);
    }

    /**
     * Set the paint's style, used for controlling how primitives'
     * geometries are interpreted (except for drawBitmap, which always assumes
     * Fill).
     * 如果是绘制文字那么FILL_AND_STROKE和FILL两种模式的区别就会显现了
     *
     * @param canvas
     */
    private void setStyle(Canvas canvas) {
        drawTitleText("setStyle的使用", canvas);
        canvas.translate(0, mTextHeight + mVerticalSpaceing);

        //填充模式
        drawTitleText("Paint.Style.FILL模式", canvas);
        canvas.translate(0, mTextHeight + mVerticalSpaceing);
        mPaint.reset();
        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Paint.Style.FILL);
        final float cx = mViewWidth / 2;
        final float cy = mRadius;
        canvas.drawCircle(cx, cy, mRadius, mPaint);
        canvas.translate(0, mRadius * 2 + mVerticalSpaceing);

        //描边模式
        drawTitleText("Paint.Style.STROKE模式", canvas);
        canvas.translate(0, mTextHeight + mVerticalSpaceing);
        mPaint.reset();
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(cx, cy, mRadius, mPaint);
        canvas.translate(0, mRadius * 2 + mVerticalSpaceing);

        //填充+描边模式
        drawTitleText("Paint.Style.FILL_AND_STROKE模式", canvas);
        canvas.translate(0, mTextHeight + mVerticalSpaceing);
        mPaint.reset();
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.drawCircle(cx, cy, mRadius, mPaint);
        canvas.translate(0, mRadius * 2 + mVerticalSpaceing);

    }

    /**
     * Helper for setFlags(), setting or clearing the ANTI_ALIAS_FLAG bit
     * AntiAliasing smooths out the edges of what is being drawn, but is has
     * no impact on the interior of the shape.
     *
     * @param canvas
     */
    private void setAntiAlias(Canvas canvas) {
        drawTitleText("setAntiAlias的使用", canvas);
        canvas.translate(0, mTextHeight + mVerticalSpaceing);
        final Paint paint = new Paint();
        //抗锯齿关闭
        drawTitleText("抗锯齿关闭", canvas);
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.FILL);
        canvas.translate(0, mTextHeight + mVerticalSpaceing);
        final float cx = mViewWidth / 2;
        final float cy = mRadius;
        canvas.drawCircle(cx, cy, mRadius, paint);
        canvas.translate(0, mRadius * 2 + mVerticalSpaceing);
        //抗锯齿开启
        drawTitleText("抗锯齿开启", canvas);
        canvas.translate(0, mTextHeight + mVerticalSpaceing);
        paint.reset();
        paint.setAntiAlias(true);
        canvas.drawCircle(cx, cy, mRadius, paint);
        canvas.translate(0, mRadius * 2 + mVerticalSpaceing);
        drawTitleText("仔细观察你就会发现画笔边缘还是有区别的。", canvas);
    }

    /**
     * Set or clear the transfer mode object. A transfer mode defines how
     * source pixels (generate by a drawing command) are composited with
     * the destination pixels (content of the render target).
     *
     * @param canvas
     */
    private void setXfermode(Canvas canvas) {
        drawTitleText("mPaint.setXfermode(xfermode)的使用", canvas);

        canvas.saveLayerAlpha(0, 0, getWidth(), getHeight(), 255, Canvas.ALL_SAVE_FLAG);
        mPaint.reset();
        mPaint.setColor(Color.YELLOW);
        canvas.drawCircle(getWidth() / 2 - mRadius / 2, getHeight() / 2 - mRadius / 2, mRadius, mPaint);

        final Xfermode xfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT);
        mPaint.setXfermode(xfermode);
        mPaint.setColor(Color.BLUE);
        canvas.drawCircle(getWidth() / 2 + mRadius / 2, getHeight() / 2 + mRadius / 2, mRadius, mPaint);
        mPaint.setXfermode(null);
    }

    /**
     * Set or clear the paint's colorfilter{@link ColorMatrixColorFilter}, returning the parameter.
     *
     * @param canvas
     */
    private void setColorMatrixColorFilter(Canvas canvas) {
        drawTitleText("ColorMatrixColorFilter的使用", canvas);
        drawTitleText("未完待续...", canvas);
    }

    /**
     * Set or clear the paint's colorfilter{@link PorterDuffColorFilter}, returning the parameter.
     *
     * @param canvas
     */
    private void setPorterDuffColorFilter(Canvas canvas) {
        drawTitleText("PorterDuffColorFilter的使用", canvas);
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
        drawTitleText("LightingColorFilter的使用", canvas);
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
        drawTitleText("ComposeShader的使用", canvas);
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
        drawTitleText("BitmapShader的使用", canvas);
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
        drawTitleText("SweepGradient的使用", canvas);
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
        drawTitleText("RadialGradient的使用", canvas);
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
        drawTitleText("LinearGradient的使用", canvas);
        mPaint.reset();
        Shader shader = new LinearGradient(
                300, 100, 300, 600,
                Color.parseColor("#5498FF"),
                Color.parseColor("#ffffff"),
                Shader.TileMode.CLAMP);
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
        drawTitleText("mPaint.setARGB的使用", canvas);
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
        drawTitleText("mPaint.setColor的使用", canvas);

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

    /**
     * 绘制Title文本
     *
     * @param content
     * @param canvas
     * @return
     */
    private void drawTitleText(String content, Canvas canvas) {
        float textWidth = mTextPaint.measureText(content);
        float x = (mViewWidth - textWidth) / 2;
        canvas.translate(0, mTextHeight);
        canvas.drawText(content, x, 0, mTextPaint);
    }
}
