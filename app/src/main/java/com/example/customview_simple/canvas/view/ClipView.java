package com.example.customview_simple.canvas.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.customview_simple.R;

/**
 * Created by ZhangXinmin on 2017/7/18.
 * Copyright (c) 2017 . All rights reserved.
 * 裁剪操作：canvas.clip**();
 */

public class ClipView extends View {
    private static final String TAG = ClipView.class.getSimpleName();

    private Context mContext;
    private Resources mResources;

    private Paint mLinePaint;
    private Paint mTextPaint;
    private Paint mFillPaint;
    private Paint mBitmapPaint;

    private float mTextSize;
    private float mTextWidth;//文字宽度
    private float mTextHeight;//文字高度
    private float mViewPadding;
    private float mRadius;
    private float mVerticalSpaceing;//绘制间隔

    private String mDrawType;//绘制类型
    private float mViewWidth;

    public ClipView(Context context) {
        this(context, null, 0);
    }

    public ClipView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ClipView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initParams();
    }

    private void initParams() {
        mResources = getResources();
        mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLinePaint.setStyle(Paint.Style.STROKE);
        mLinePaint.setStrokeWidth(1);

        mTextSize = mResources.getDimensionPixelSize(R.dimen.textSize_14sp);
        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(Color.BLACK);
        mTextPaint.setTextSize(mTextSize);
        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        mTextHeight = fontMetrics.bottom - fontMetrics.top;

        mFillPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mFillPaint.setStyle(Paint.Style.FILL);

        mBitmapPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBitmapPaint.setFilterBitmap(true);//set flag
        mBitmapPaint.setDither(true);//set flag

        mViewPadding = mResources.getDimensionPixelSize(R.dimen.draw_padding);
        mRadius = mResources.getDimensionPixelSize(R.dimen.draw_radius);
        mVerticalSpaceing = mResources.getDimensionPixelSize(R.dimen.draw_space);
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
        switch (mDrawType) {
            case "clipPath":
                clipPath(canvas);
                break;
            case "clipRect":

                break;

        }
    }

    private void clipPath(Canvas canvas) {
    }
}
