package com.example.android_painter.view.chart;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.example.android_painter.R;


/**
 * Created by ZhangXinmin on 2017/6/27.
 * Copyright (c) 2017 . All rights reserved.
 * <p>
 * 3D pie chart for fragment of strategy!
 */

public class PieChart extends View {
    private Context mContext;
    private Resources mResources;

    //临界值
    private int mLowIndex;//低临界值
    private int mHighIndex;//高临界值
    private int mHalfIndex;//高临界值
    private int mRiskValue;//风险值

    //画笔
    private Paint mTextPaint;//文字画笔
    private Paint mLinePaint;//线条画笔
    private Paint mBackPaint;//背景画笔

    //画面尺寸
    private float mChartWidth;//宽度
    private float mChartHeight;//高度
    private float mOvalCenterX;//椭圆中心点
    private float mOvalCenterY;//
    private float mPieHeight;//饼图高度
    private float mStartAngle;//角度
    private float mSweepAngle;
    private float mFullAngle;
    //上部椭圆
    private float mLeft;
    private float mTop;
    private float mRight;
    private float mBottom;
    private RectF mOvalRectF;//上部椭圆
    private RectF mBottomOvalRectF;//下部半椭圆

    private float mPadding;
    private float mTextSize;//字号
    private String mText;//绘制文字
    private float mTextHeight;//文字高度
    private float mTextWidth;//文字宽度

    public PieChart(Context context) {
        this(context, null, 0);
    }

    public PieChart(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PieChart(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;

        initParamsAndValues();
    }

    //初始化参数
    private void initParamsAndValues() {
        mResources = mContext.getResources();
        mRiskValue = 90;//测试值

        mLowIndex = 30;
        mHalfIndex = 50;
        mHighIndex = 80;
        mStartAngle = 0;
        mFullAngle = 360;
        //资源
        mPadding = mResources.getDimensionPixelSize(R.dimen.padding_risk);
        mTextSize = mResources.getDimensionPixelSize(R.dimen.textsize_14sp);
        mText = mResources.getString(R.string.market_riskindex);//文字内容
        mPieHeight = mResources.getDimensionPixelSize(R.dimen.height_pie);//高度

        //初始化画笔
        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(Color.BLACK);
        mTextPaint.setStrokeWidth(1);
        mTextPaint.setTextSize(mTextSize);
        mTextWidth = mTextPaint.measureText(mText);//文字宽度
        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        mTextHeight = (float) ((fontMetrics.bottom - fontMetrics.top) * 0.8);//文字高度

        mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLinePaint.setColor(Color.GRAY);
        mLinePaint.setStrokeWidth(2);
        mLinePaint.setStyle(Paint.Style.STROKE);

        mBackPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBackPaint.setColor(Color.BLUE);
        mBackPaint.setStyle(Paint.Style.FILL);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        mChartWidth = getMeasuredWidth() - mPadding * 2;
        mChartHeight = getMeasuredHeight() - mPadding * 3 - mTextHeight;

        mOvalCenterX = mChartWidth / 2;
        mOvalCenterY = (mChartHeight - mPieHeight) / 2 + mPadding * 2 + mTextHeight;

        mLeft = mPadding;
        mTop = mPadding * 2 + mTextHeight;
        mRight = getMeasuredWidth() - mPadding;
        mBottom = getMeasuredHeight() - mPadding - mPieHeight;
        mOvalRectF = new RectF(mLeft, mTop, mRight, mBottom);//上部椭圆
        mBottomOvalRectF = new RectF(mLeft, mTop + mPieHeight, mRight, mBottom + mPieHeight);//下部椭圆弧
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawFrame(canvas);//绘制边框
        drawTitle(canvas);//绘制标题

        drawPieChart(canvas);//绘制饼图
    }

    /**
     * draw 3D pie chart
     *
     * @param canvas
     */
    private void drawPieChart(Canvas canvas) {
        mSweepAngle = (mFullAngle * mRiskValue) / 100;
        mBackPaint.setColor(Color.GRAY);
        mLinePaint.setColor(Color.BLACK);
        if (mRiskValue == 0) {
            canvas.drawArc(mBottomOvalRectF, mStartAngle, mFullAngle, true, mBackPaint); //绘制下部圆弧：填充
            canvas.drawArc(mOvalRectF, mStartAngle, mFullAngle, true, mBackPaint); //绘制上部圆弧：填充

            drawPieChartFrame(canvas);//绘制框架
            canvas.drawLine(mOvalCenterX, mOvalCenterY, mRight, mOvalCenterY, mLinePaint);//绘制上椭圆连线
        } else if (mRiskValue <= mHalfIndex) {//在上部
            canvas.drawArc(mOvalRectF, mStartAngle, mFullAngle, true, mBackPaint); //绘制上部圆弧：填充
            canvas.drawArc(mBottomOvalRectF, mStartAngle, mFullAngle, true, mBackPaint); //绘制下部圆弧：填充
            mBackPaint.setColor(getRiskColor());

            canvas.drawArc(mOvalRectF, 0, -mSweepAngle, true, mBackPaint);//风险指数填充
            canvas.drawArc(mOvalRectF, 0, -mSweepAngle, true, mLinePaint);//风险指数边框
            drawPieChartFrame(canvas);//绘制框架
        } else {
            mBackPaint.setColor(getRiskColor());
            canvas.drawArc(mBottomOvalRectF, mStartAngle, mFullAngle, true, mBackPaint); //绘制下部圆弧：填充
            mBackPaint.setColor(Color.GRAY);
            canvas.drawArc(mOvalRectF, mStartAngle, mFullAngle, true, mBackPaint); //绘制上部圆弧：填充
            mBackPaint.setColor(getRiskColor());
            canvas.drawArc(mOvalRectF, 0, mSweepAngle, true, mBackPaint);//风险指数填充
            canvas.drawArc(mOvalRectF, 0, mSweepAngle, true, mLinePaint);//风险指数边框
            drawPieChartFrame(canvas);//绘制框架
        }
    }

    /**
     * draw the frame for pie chart with line
     *
     * @param canvas
     */
    private void drawPieChartFrame(Canvas canvas) {

        canvas.drawArc(mBottomOvalRectF, mStartAngle, mFullAngle / 2, false, mLinePaint); //绘制下部圆弧：线条
        canvas.drawArc(mOvalRectF, mStartAngle, mFullAngle, true, mLinePaint); //绘制上部圆弧：线条
        //左侧封闭线
        Path leftPath = new Path();
        leftPath.moveTo(mPadding, mOvalCenterY);
        leftPath.lineTo(mPadding, mOvalCenterY + mPieHeight);
        canvas.drawPath(leftPath, mLinePaint);
        //右侧封闭线
        Path rightPath = new Path();
        rightPath.moveTo(getMeasuredWidth() - mPadding, mOvalCenterY);
        rightPath.lineTo(getMeasuredWidth() - mPadding, mOvalCenterY + mPieHeight);
        canvas.drawPath(rightPath, mLinePaint);
    }

    /**
     * draw frame for the chart
     *
     * @param canvas
     */
    private void drawFrame(Canvas canvas) {
        float left = 0;
        float top = 0;
        float right = getMeasuredWidth();
        float bottom = getMeasuredHeight();
        canvas.drawRect(left, top, right, bottom, mLinePaint);
    }

    /**
     * draw title for the chart
     *
     * @param canvas
     * @hide
     */
    private void drawTitle(Canvas canvas) {
        //绘制标题
        if (TextUtils.isEmpty(mText)) return;
        float mTitleX = mPadding;
        float mTitleY = mPadding + mTextHeight;
        canvas.drawText(mText, mTitleX, mTitleY, mTextPaint);

        //绘制图例
        float left = mPadding + mTextWidth + mPadding;
        float top = mPadding;
        float right = left + mTextHeight;
        float bottom = top + mTextHeight;
        mBackPaint.setColor(getRiskColor());
        canvas.drawRect(left, top, right, bottom, mBackPaint);

        //绘制风险提示值
        canvas.drawText(mRiskValue + "%", right + mPadding / 5, mTitleY, mTextPaint);
    }

    /**
     * get the risk color
     *
     * @return int
     */
    private int getRiskColor() {
        if (mRiskValue >= mHighIndex) {//风险很大
            return Color.GREEN;
        } else if (mRiskValue <= mLowIndex) {//风险很小
            return Color.RED;
        } else {
            return Color.BLUE;
        }
    }

    /**
     * Set the risk value for the pie chart,then the chart will invalidate.
     *
     * @param mRiskValue
     */
    public void setmRiskValue(int mRiskValue) {
        this.mRiskValue = mRiskValue;
        invalidate();
    }
}
