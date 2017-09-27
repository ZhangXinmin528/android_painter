package com.example.android_painter.view.chart;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import com.example.android_painter.R;

/**
 * Created by ZhangXinmin on 2017/2/12.
 * Copyright (c) 2017 . All rights reserved.
 * 图表基类
 */

public class BaseChart extends View {

    protected Resources mResources;
    private int mOffset;//偏移量
    private float fontSize;//字号
    //画笔
    private Paint mLinePaint;//绘制线条
    private Paint mBackPaint;//轴面绘制
    private Paint mTextPaint;//绘制文字
    //屏幕尺寸数据
    private int mScreenWidth;
    private int mScreenHeight;
    private int mTopPadding;//上间距
    private int mBottomPadding;//下间距
    private int mLeftPadding;//左间距
    private int mRightPadding;//间距
    private int mXZHeight;//XZ面高度
    private int mXZWidth;//XZ面宽度
    private int mXYHeight;//XY面的高度
    private int mXYWidth;//XY面的宽度
    private int mYZHeight;//YZ坐标面高度
    private int mYZWidth;//YZ坐标面宽度

    public BaseChart(Context context) {
        super(context);
        initChart();
    }

    public BaseChart(Context context, AttributeSet attrs) {
        super(context, attrs);
        initChart();
    }

    public BaseChart(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initChart();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int measureWidth = measure(widthMeasureSpec);
        int measureHeigh = measure(heightMeasureSpec);

        setMeasuredDimension(measureWidth, measureHeigh);//设置宽高；
    }

    private int measure(int measureSpec) {

        int result = 0;
        //对测量说明进行解码
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode != MeasureSpec.UNSPECIFIED) {
            result = specSize;
        }
        return result;
    }

    //初始化一些属性
    private void initChart() {

        mResources = getResources();
        fontSize = getResources().getDimensionPixelSize(R.dimen.textsize_12sp);//字号
        //线条绘制画笔
        mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLinePaint.setStrokeWidth(1);//线宽
        mLinePaint.setStyle(Paint.Style.STROKE);//样式
        mLinePaint.setColor(Color.BLACK);//画笔颜色
        //背景绘制画笔
        mBackPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        //文字绘制画笔
        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextSize(fontSize);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mScreenHeight = getMeasuredHeight();
        mScreenWidth = getMeasuredWidth();

        mTopPadding = (int) (mScreenHeight * 0.06);
        mBottomPadding = mTopPadding;
        mRightPadding = mTopPadding;
        mLeftPadding = (int) (mRightPadding + mTextPaint.measureText("1000"));

        int height = mScreenHeight - mTopPadding - mBottomPadding;//图表有效高度
        int width = mScreenWidth - mLeftPadding - mRightPadding;//图表的有效宽度

        //获取XY坐标面的宽高
        mXYHeight = (int) (height * 0.9);
        mXYWidth = (int) (width * 0.97);

        //获取XZ坐标面的宽高
        mXZHeight = height - mXYHeight;
        mXZWidth = mXYWidth;

        //获取ZY坐标面的宽高
        mYZHeight = mXYHeight;
        mYZWidth = width - mXYWidth;

        mOffset = (int) (mScreenWidth * 0.02);// 已经包含padding

        drawXYSurface(canvas);//绘制XY坐标面
        drawYZSurface(canvas);//绘制XY坐标面
        drawXZSurface(canvas);//绘制XZ坐标面
    }

    /**
     * 绘制XZ坐标面
     *
     * @param canvas
     */
    private void drawXZSurface(Canvas canvas) {
        mBackPaint.setColor(Color.WHITE);//背景色

        //绘制平行四边形
        Path path = new Path();
        path.moveTo(mLeftPadding + mYZWidth, mTopPadding + mXYHeight);
        path.lineTo(mLeftPadding + mYZWidth + mXZWidth, mTopPadding + mXYHeight);
        path.lineTo(mLeftPadding + mXZWidth, mOffset + mYZHeight);
        path.lineTo(mLeftPadding, mOffset + mYZHeight);
        path.close();

        canvas.drawPath(path, mBackPaint);
        canvas.drawPath(path, mLinePaint);
    }

    /**
     * 绘制YZ坐标平面
     *
     * @param canvas
     */
    private void drawYZSurface(Canvas canvas) {
        mBackPaint.setColor(mResources.getColor(R.color.colorYellow));//背景色

        //绘制平行四边形
        Path path = new Path();
        path.moveTo(mLeftPadding, mOffset);
        path.lineTo(mLeftPadding + mYZWidth, mTopPadding);
        path.lineTo(mLeftPadding + mYZWidth, mYZHeight + mTopPadding);
        path.lineTo(mLeftPadding, mYZHeight + mOffset);
        path.close();

        canvas.drawPath(path, mBackPaint);
        canvas.drawPath(path, mLinePaint);
    }

    /**
     * 绘制XY坐标平面
     *
     * @param canvas
     */
    private void drawXYSurface(Canvas canvas) {
        mBackPaint.setColor(mResources.getColor(R.color.colorLightGray));//背景色

        //坐标
        int left = mLeftPadding + mYZWidth;//左
        int top = mTopPadding;//上
        int right = left + mXYWidth;//右
        int bottom = top + mXYHeight;

        canvas.drawRect(left, top, right, bottom, mBackPaint);//绘制矩形
        canvas.drawRect(left, top, right, bottom, mLinePaint);//绘制边框

    }

    public int getmOffset() {
        return mOffset;
    }

    public void setmOffset(int mOffset) {
        this.mOffset = mOffset;
    }

    public int getmScreenWidth() {
        return mScreenWidth;
    }

    public void setmScreenWidth(int mScreenWidth) {
        this.mScreenWidth = mScreenWidth;
    }

    public int getmScreenHeight() {
        return mScreenHeight;
    }

    public void setmScreenHeight(int mScreenHeight) {
        this.mScreenHeight = mScreenHeight;
    }

    public int getmTopPadding() {
        return mTopPadding;
    }

    public void setmTopPadding(int mTopPadding) {
        this.mTopPadding = mTopPadding;
    }

    public int getmBottomPadding() {
        return mBottomPadding;
    }

    public void setmBottomPadding(int mBottomPadding) {
        this.mBottomPadding = mBottomPadding;
    }

    public int getmLeftPadding() {
        return mLeftPadding;
    }

    public void setmLeftPadding(int mLeftPadding) {
        this.mLeftPadding = mLeftPadding;
    }

    public int getmRightPadding() {
        return mRightPadding;
    }

    public void setmRightPadding(int mRightPadding) {
        this.mRightPadding = mRightPadding;
    }

    public int getmXZHeight() {
        return mXZHeight;
    }

    public void setmXZHeight(int mXZHeight) {
        this.mXZHeight = mXZHeight;
    }

    public int getmXZWidth() {
        return mXZWidth;
    }

    public void setmXZWidth(int mXZWidth) {
        this.mXZWidth = mXZWidth;
    }

    public int getmXYHeight() {
        return mXYHeight;
    }

    public void setmXYHeight(int mXYHeight) {
        this.mXYHeight = mXYHeight;
    }

    public int getmXYWidth() {
        return mXYWidth;
    }

    public void setmXYWidth(int mXYWidth) {
        this.mXYWidth = mXYWidth;
    }

    public int getmYZHeight() {
        return mYZHeight;
    }

    public void setmYZHeight(int mYZHeight) {
        this.mYZHeight = mYZHeight;
    }

    public int getmYZWidth() {
        return mYZWidth;
    }

    public void setmYZWidth(int mYZWidth) {
        this.mYZWidth = mYZWidth;
    }
}
