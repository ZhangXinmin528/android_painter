package com.zxm.android_painter.view.chart;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.Point;
import android.graphics.Shader;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.zxm.android_painter.R;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by ZhangXinmin on 2020/6/30.
 * Copyright (c) 2020 . All rights reserved.
 */
public class StepHistoryView extends View {
    //表类型
    public static final int TYPE_CHART_WEEK = 0x001;//周表类型
    public static final int TYPE_CHART_MONTH = 0x002;//月表类型

    private static final String TAG = "StepHistoryView";

    private Context mContext;

    //图标尺寸
    private int mWidth;
    private int mHeight;
    private float mPictureWidth;
    private float mPictureHeight;

    private float mStartX;
    private float mStopX;
    private float mDefaultPadding;

    //背景绘制
    private Paint mBackPaint;

    //轴线
    private Paint mTextPaint;
    private Paint mPointPaint;
    private float mAxisTextSize;
    private int mAxisTextColor;
    private float mDefaultMarginSize;
    //图表类型
    private int mChartType;
    //天数
    private int mDayCount;
    private int mDayInterval;
    private Calendar mCalendar;
    private float mYLabelMaxWidth;
    private float mLabelTextHeight;
    //坐标单位刻度
    private float mXAxisUnitWidth;
    private PathEffect mPathEffect;
    //曲线
    private Paint mLinePaint;
    private Shader mLineShader;
    private Shader mShadowShader;

    //数据
    private List<Float> mDataList;
    private float mMaxValue;
    private float mZeroAxisY;

    //标签
    private GestureDetectorCompat mGestureDetector;
    private List<Point> mPointList;
    private int mTouchIndex;
    private float mTagTextSize;
    private int mTagTextColor;

    public StepHistoryView(Context context) {
        this(context, null);
    }

    public StepHistoryView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StepHistoryView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initParams(context, attrs);
    }

    private void initParams(Context context, AttributeSet attrs) {
        this.mContext = context;
        final DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        if (attrs != null) {
            final TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.StepHistoryView);
            mAxisTextSize = typedArray.getDimensionPixelSize(R.styleable.StepHistoryView_textsize_axis_text,
                    10);
            mAxisTextColor = typedArray.getColor(R.styleable.StepHistoryView_textcolor_axis_text,
                    Color.parseColor("#666666"));

            mTagTextSize = typedArray.getDimensionPixelSize(R.styleable.StepHistoryView_textsize_tag_text,
                    10);
            mTagTextColor = typedArray.getColor(R.styleable.StepHistoryView_textcolor_tag_text,
                    Color.parseColor("#666666"));

            typedArray.recycle();
        } else {
            mAxisTextSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10, displayMetrics);
            mAxisTextColor = Color.parseColor("#666666");

            mTagTextSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, displayMetrics);
            mTagTextColor = Color.parseColor("#666666");
        }
        mDefaultMarginSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, displayMetrics);
        mDefaultPadding = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, displayMetrics);

        mCalendar = Calendar.getInstance();

        //默认:月
        mChartType = TYPE_CHART_MONTH;
        mDayCount = 30;

        //paint
        mBackPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBackPaint.setColor(Color.parseColor("#ffffff"));

        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(mAxisTextColor);
        mTextPaint.setTextSize(mAxisTextSize);

        mPointPaint = new Paint();
        mPointPaint.setStrokeCap(Paint.Cap.ROUND);
        mPointPaint.setColor(Color.RED);
        mPointPaint.setStrokeWidth(10);

        mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);


        mPathEffect = new DashPathEffect(new float[]{6, 6}, 0);

        //params
        mYLabelMaxWidth = mTextPaint.measureText("10W");

        final Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        mLabelTextHeight = fontMetrics.bottom - fontMetrics.top;

        mDataList = new ArrayList<>();
        mPointList = new ArrayList<>();

        mGestureDetector = new GestureDetectorCompat(mContext, new GestureDetector.SimpleOnGestureListener() {

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                final float x = e.getX();
                final float y = e.getY();
                mTouchIndex = -1;
                if (calcuTouchIndex(x, y)) {
                    postInvalidate();
                }
                return true;
            }
        });

        mTouchIndex = -1;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        mWidth = MeasureSpec.getSize(widthMeasureSpec);
        mHeight = mWidth / 2;
        mStartX = mDefaultPadding + mYLabelMaxWidth;
        mStopX = mWidth - mDefaultPadding;

        mPictureWidth = mWidth - mDefaultPadding * 2 - mYLabelMaxWidth;
        mPictureHeight = mHeight - mDefaultPadding * 2 - mLabelTextHeight - mDefaultMarginSize;
        mZeroAxisY = mHeight - mDefaultPadding - mLabelTextHeight - mDefaultMarginSize;

        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.parseColor("#ffffff"));

        drawAxisAndLabel(canvas);

        drawGride(canvas);

        drawHistoryLine(canvas);

        drawTag(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mGestureDetector.onTouchEvent(event);
        return true;
    }

    /**
     * 绘制步数标签
     *
     * @param canvas
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void drawTag(Canvas canvas) {

        if (mTouchIndex == -1) {
            return;
        }

        if (!mDataList.isEmpty() && !mPointList.isEmpty()) {
            final Point point = mPointList.get(mTouchIndex);
            final int x = point.x;
            final int y = point.y;
            int dataIndex = mTouchIndex * mDayInterval;

            final String content = mDataList.get(dataIndex == mDayCount ? --dataIndex : dataIndex).intValue() + "";

            //绘制位置点
            mPointPaint.reset();
            mPointPaint.setColor(Color.WHITE);
            mPointPaint.setStrokeCap(Paint.Cap.ROUND);
            mPointPaint.setStrokeWidth(18);

            canvas.drawPoint(x, y, mPointPaint);

            mLinePaint.reset();
            mLinePaint.setStrokeWidth(5);
            mLinePaint.setColor(Color.parseColor("#38b9ff"));
            mLinePaint.setStyle(Paint.Style.STROKE);

            canvas.drawCircle(x, y, 10.0f, mLinePaint);

            mTextPaint.reset();
            mTextPaint.setColor(mTagTextColor);
            mTextPaint.setAntiAlias(true);
            mTextPaint.setStyle(Paint.Style.FILL);
            mTextPaint.setTextSize(mTagTextSize);

            final float contentWidth = mTextPaint.measureText(content);
            final Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
            final float contentHeight = fontMetrics.bottom - fontMetrics.top;

            final float windowHeight = contentHeight + 2 * mDefaultMarginSize;
            final float radius = windowHeight / 2.0f;
            final float windowWidght = contentHeight + 2.0f * radius;

            //起始坐标
            float centerX;
            if (mTouchIndex == 0) {
                centerX = windowWidght / 2.0f + mDefaultPadding * 3.0f + mYLabelMaxWidth;
            } else if (mTouchIndex == 6) {
                centerX = mWidth - 3.0f * mDefaultPadding - windowWidght / 2.0f;
            } else {
                centerX = x;
            }

            float centerY;
            if (y - 3.0f * mDefaultPadding > windowHeight) {
                centerY = y - 1.5f * mDefaultMarginSize - windowHeight / 2.0f;
            } else {
                centerY = y + 1.5f * mDefaultMarginSize + windowHeight / 2.0f;
            }

            mBackPaint.reset();
            mBackPaint.setColor(Color.WHITE);
            mBackPaint.setStyle(Paint.Style.FILL_AND_STROKE);
            mBackPaint.setAntiAlias(true);

            canvas.drawArc(
                    centerX + contentWidth / 2.0f - radius, centerY - radius,
                    centerX + contentWidth / 2.0f + radius, centerY + radius,
                    -90, 180, true, mBackPaint
            );

            canvas.drawRect(centerX - contentWidth / 2.0f, centerY - radius,
                    centerX + contentWidth / 2.0f, centerY + radius, mBackPaint);

            canvas.drawArc(centerX - contentWidth / 2.0f - radius, centerY - radius,
                    centerX - contentWidth / 2.0f + radius, centerY + radius,
                    90, 180, true, mBackPaint);

            //绘制文字
            canvas.drawText(content, centerX - contentWidth / 2.0f, centerY + contentHeight / 3.3f, mTextPaint);
        }
    }

    /**
     * 绘制历史数据
     *
     * @param canvas
     */
    private void drawHistoryLine(Canvas canvas) {

        if (!mPointList.isEmpty()) {
            mPointList.clear();
        }

        if (!mDataList.isEmpty()) {
            final int size = mDataList.size();

            //shadow
            mBackPaint.reset();
            mBackPaint.setStrokeWidth(3);
            mBackPaint.setStyle(Paint.Style.FILL);
            mBackPaint.setStrokeJoin(Paint.Join.ROUND);

            if (mShadowShader == null) {
                mShadowShader = new LinearGradient(0, 0, 0, mHeight,
                        new int[]{Color.parseColor("#5498FF"),
                                Color.argb(30, 76, 161, 255),
                                Color.argb(1, 165, 125, 255),},
                        null, LinearGradient.TileMode.CLAMP);
            }

            mBackPaint.setShader(mShadowShader);

            final Path shadowPath = new Path();
//            final PathEffect pathEffect = new CornerPathEffect(50);
            shadowPath.moveTo(mStartX, mZeroAxisY);

            //line
            mLinePaint.reset();
            mLinePaint.setStrokeWidth(5);
            mLinePaint.setStyle(Paint.Style.STROKE);
            mLinePaint.setStrokeJoin(Paint.Join.ROUND);

            if (mLineShader == null) {

                mLineShader = new LinearGradient(
                        mDefaultPadding, 0, mWidth - mDefaultPadding, 0,
                        Color.parseColor("#6384FF"),
                        Color.parseColor("#34BFFF"),
                        Shader.TileMode.CLAMP);
            }

            mLinePaint.setShader(mLineShader);

            final Path linePath = new Path();
            for (int i = 0; i < size; i++) {
                final float value = mDataList.get(i);
                final float y = (mMaxValue * 10000 - value) * mZeroAxisY / mMaxValue / 10000;
                final float x = mStartX + i * mXAxisUnitWidth;

                if (i == 0) {
                    linePath.moveTo(x, y);
                } else {
                    linePath.lineTo(x, y);
                }

                if (mChartType == TYPE_CHART_WEEK) {
                    mPointList.add(new Point((int) x, (int) y));
                } else {
                    if (i == size - 1 || i % 5 == 0) {
                        mPointList.add(new Point((int) x, (int) y));
                    }
                }

                shadowPath.lineTo(x, y);

//                canvas.drawPoint(x, y, mPointPaint);
            }
            shadowPath.lineTo(mStopX, mZeroAxisY);
            shadowPath.close();
//            mBackPaint.setPathEffect(pathEffect);
            canvas.drawPath(shadowPath, mBackPaint);

//            mLinePaint.setPathEffect(pathEffect);
            canvas.drawPath(linePath, mLinePaint);

        }
    }


    /**
     * 绘制横坐标标签
     *
     * @param canvas
     */
    private void drawAxisAndLabel(@NonNull Canvas canvas) {
        //绘制顶部网格线
        mLinePaint.reset();
        mLinePaint.setColor(Color.parseColor("#ebebeb"));
        mLinePaint.setStrokeWidth(2);
        mLinePaint.setStyle(Paint.Style.FILL);

        canvas.drawLine(mStartX, mDefaultPadding, mStopX, mDefaultPadding, mLinePaint);

        //绘制X轴线
        mLinePaint.reset();
        mLinePaint.setStrokeWidth(2);
        mLinePaint.setStyle(Paint.Style.FILL);
        mLinePaint.setColor(Color.parseColor("#999999"));
        canvas.drawLine(mStartX, mZeroAxisY, mStopX, mZeroAxisY, mLinePaint);

        //绘制x轴刻度标签
        mDayCount = mChartType == TYPE_CHART_MONTH ? 30 : 7;
        mDayInterval = mChartType == TYPE_CHART_MONTH ? 5 : 1;

        mXAxisUnitWidth = mPictureWidth / (mDayCount - 1);
        final float labelY = mHeight - mDefaultPadding;

        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(mCalendar.getTime());
        calendar.add(Calendar.DAY_OF_MONTH, -mDayCount + 1);

        mTextPaint.reset();
        mTextPaint.setColor(mAxisTextColor);
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setTextSize(mAxisTextSize);

//        mPointPaint.reset();
//        mPointPaint.setStrokeCap(Paint.Cap.ROUND);
//        mPointPaint.setColor(Color.RED);
//        mPointPaint.setStrokeWidth(10);

        final int month = calendar.get(Calendar.MONTH) + 1;
        for (int i = 0; i < (mChartType == TYPE_CHART_MONTH ? 30 : 6); ) {
            String xLabel;
            if (i == 0) {
                xLabel = month + "月" + calendar.get(Calendar.DAY_OF_MONTH);
            } else {
                xLabel = calendar.get(Calendar.DAY_OF_MONTH) + "";
            }

//            canvas.drawPoint(mStartX + i * mXAxisUnitWidth, mZeroAxisY, mPointPaint);
            final float labelWidth = mTextPaint.measureText(xLabel);
            canvas.drawText(xLabel, mStartX + i * mXAxisUnitWidth - labelWidth / 2.0f, labelY, mTextPaint);
            calendar.roll(Calendar.DAY_OF_MONTH, mDayInterval);
            i += mDayInterval;
        }
        final String today = mCalendar.get(Calendar.DAY_OF_MONTH) + "";
        final float todayWidth = mTextPaint.measureText(today);

//        canvas.drawPoint(mWidth - mDefaultPadding, mZeroAxisY, mPointPaint);
        canvas.drawText(today, mWidth - todayWidth - mDefaultPadding, labelY, mTextPaint);

    }

    /**
     * 绘制分割线
     *
     * @param canvas
     */
    private void drawGride(Canvas canvas) {

        //绘制分割线
        if (mDataList.isEmpty()) {
            drawLowerDividerLine(canvas);
        } else {
            if (mMaxValue == 1) {
                drawLowerDividerLine(canvas);
            } else {
                drawDashDividerLine(canvas);
            }
        }

    }

    /**
     * 绘制虚线
     *
     * @param canvas
     */
    private void drawDashDividerLine(Canvas canvas) {
        final float unit = (mZeroAxisY - mDefaultPadding) / mMaxValue;
        final PathEffect pathEffect = new DashPathEffect(new float[]{6, 6}, 0);
        for (int i = 1; i < mMaxValue; i++) {
            final float dividerY = mZeroAxisY - i * unit;
            mLinePaint.reset();
            mLinePaint.setColor(Color.parseColor("#cccccc"));
            mLinePaint.setStrokeWidth(2);
            mLinePaint.setStyle(Paint.Style.FILL);
            mLinePaint.setPathEffect(pathEffect);

            canvas.drawLine(mStartX, dividerY, mStopX, dividerY, mLinePaint);

            mTextPaint.reset();
            mTextPaint.setColor(mAxisTextColor);
            mTextPaint.setStyle(Paint.Style.FILL);
            mTextPaint.setTextSize(mAxisTextSize);
            canvas.drawText(i + "W", mDefaultPadding, dividerY, mTextPaint);
        }
    }

    /**
     * 绘制5k分割线
     *
     * @param canvas
     */
    private void drawLowerDividerLine(Canvas canvas) {
        final float middleY = (mZeroAxisY - mDefaultPadding) / 2.0f;
        mLinePaint.reset();
        mLinePaint.setColor(Color.parseColor("#cccccc"));
        mLinePaint.setStrokeWidth(2);
        mLinePaint.setStyle(Paint.Style.FILL);

        mLinePaint.setPathEffect(mPathEffect);

        canvas.drawLine(mStartX, middleY, mStopX, middleY, mLinePaint);

        mTextPaint.reset();
        mTextPaint.setColor(mAxisTextColor);
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setTextSize(mAxisTextSize);
        canvas.drawText("5K", mDefaultPadding, middleY, mTextPaint);
    }

    /**
     * 计算标签位置
     *
     * @param x
     * @param y
     * @return
     */
    private boolean calcuTouchIndex(float x, float y) {
        if (mPointList.isEmpty()) {
            return false;
        }

        final int size = mPointList.size();
        final float touchSlop = mPictureWidth / 6.0f;

        for (int i = 0; i < size; i++) {
            final Point point = mPointList.get(i);

            if (x > point.x - touchSlop / 2.0f && x < point.x + touchSlop / 2.0f) {
                mTouchIndex = i;
                return true;
            }
        }
        return false;
    }

    /**
     * 计算最大值
     */
    private void calcuMaxValue() {
        if (mDataList != null && !mDataList.isEmpty()) {
            for (Float value : mDataList) {
                mMaxValue = mMaxValue > value ? mMaxValue : value;
            }

            final BigDecimal bd1 = new BigDecimal(mMaxValue / 10000.0f).setScale(0,
                    BigDecimal.ROUND_UP);
            mMaxValue = bd1.intValue();
        }
    }

    /**
     * 为图表设置数据
     *
     * @param chartType
     * @param data
     */
    public void setHistoryDada(int chartType, @NonNull List<Float> data) {
        if (data != null && !data.isEmpty()) {
            if (!mDataList.isEmpty()) {
                mDataList.clear();
            }
            mChartType = chartType;
            mTouchIndex = -1;

            mDataList.addAll(data);
            calcuMaxValue();
            postInvalidate();
        }
    }
}
