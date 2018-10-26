package com.zxm.android_painter.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.zxm.android_painter.R;
import com.zxm.libcommon.Logger;

/**
 * Created by ZhangXinmin on 2018/10/25.
 * Copyright (c) 2018 . All rights reserved.
 */
public class TextDescView extends View {

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

    public TextDescView(Context context) {
        this(context, null, 0);
    }

    public TextDescView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TextDescView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initParamsAndValues(context);
    }

    private void initParamsAndValues(Context context) {
        mContext = context;
        mResources = mContext.getResources();

        mDrawType = "";

        //文字画笔
        mTextSize = mResources.getDimensionPixelSize(R.dimen.textsize_16sp);
        mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setColor(Color.BLACK);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);

        mVerticalSpaceing = mResources.getDimensionPixelSize(R.dimen.draw_space);

        mRadius = mResources.getDimensionPixelSize(R.dimen.draw_radius);

        mViewPadding = mResources.getDimensionPixelSize(R.dimen.draw_padding);

        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        mTextHeight = fontMetrics.bottom - fontMetrics.top;
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
            case "drawText":
                drawText(canvas);
                break;
            //StaticLayout
            case "StaticLayout":
                useStaticLayout(canvas);
                break;
            //设置多种文本样式
            case "setMultiStyle":
                setMultiStyle(canvas);
                break;
        }
    }

    /**
     * 设置多种文本样式
     *
     * @param canvas
     */
    private void setMultiStyle(Canvas canvas) {

        final String content = "Android 自定义View";
        drawTitleText("原文本", canvas);
        //绘制原文本
        mPaint.reset();
        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(2);
        mPaint.setTextSize(mResources.getDimensionPixelSize(R.dimen.textsize_25sp));
        final float width = (mViewWidth - mPaint.measureText(content)) / 2;
        Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
        final float textHeight = fontMetrics.bottom - fontMetrics.top;

        canvas.drawText(
                content,
                width,
                textHeight,
                mPaint);

        canvas.translate(0, textHeight + mVerticalSpaceing);
        drawTitleText("设置删除线", canvas);
        //设置删除线
        mPaint.reset();
        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(2);
        mPaint.setTextSize(mResources.getDimensionPixelSize(R.dimen.textsize_25sp));
        mPaint.setStrikeThruText(true);
        canvas.drawText(
                content,
                width,
                textHeight,
                mPaint);

        canvas.translate(0, textHeight + mVerticalSpaceing);
        drawTitleText("设置下划线", canvas);
        //设置下划线
        mPaint.reset();
        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(2);
        mPaint.setTextSize(mResources.getDimensionPixelSize(R.dimen.textsize_25sp));
        mPaint.setUnderlineText(true);
        canvas.drawText(
                content,
                width,
                textHeight,
                mPaint);

        canvas.translate(0, textHeight + mVerticalSpaceing);
        drawTitleText("设置倾斜", canvas);
        //设置下划线
        mPaint.reset();
        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(2);
        mPaint.setTextSize(mResources.getDimensionPixelSize(R.dimen.textsize_25sp));
        mPaint.setTextSkewX(-0.5f);
        canvas.drawText(
                content,
                width,
                textHeight,
                mPaint);

        canvas.translate(0, textHeight + mVerticalSpaceing);
        drawTitleText("设置文字横向缩放", canvas);
        //设置文字横向缩放
        mPaint.reset();
        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(2);
        mPaint.setTextSize(mResources.getDimensionPixelSize(R.dimen.textsize_25sp));
        mPaint.setTextScaleX(1.2f);
        canvas.drawText(
                content,
                width,
                textHeight,
                mPaint);

        canvas.translate(0, textHeight + mVerticalSpaceing);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            //设置字符间距
            drawTitleText("设置字符间距", canvas);
            mPaint.reset();
            mPaint.setColor(Color.BLUE);
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setStrokeWidth(2);
            mPaint.setLetterSpacing(1.2f);
            mPaint.setTextSize(mResources.getDimensionPixelSize(R.dimen.textsize_25sp));

            canvas.drawText(
                    content,
                    width,
                    textHeight,
                    mPaint);
        }



    }

    /**
     * @param canvas
     */
    private void useStaticLayout(Canvas canvas) {
        drawTitleText("StaticLayout的使用", canvas);
        canvas.translate(0, mTextHeight);
        drawTitleText("canvas.drawText()演示", canvas);
        final String tips0 = "canvas.drawText()不能自动换行，换行符会被识别成空格。";
        float x = (mViewWidth - mTextPaint.measureText(tips0)) / 2;
        canvas.drawText(tips0, 0, mTextHeight, mTextPaint);

        canvas.translate(0, mTextHeight * 4);
        drawTitleText("StaticLayout的演示", canvas);
        //使用StaticLayout绘制文字
        final String content = "StaticLayout能识别换行符的，\n它也能通过宽度换行，有了它再也不愁自定义View换行了。";
        final TextPaint textPaint = new TextPaint();
        textPaint.set(mTextPaint);
        final StaticLayout staticLayout =
                new StaticLayout(
                        content,
                        textPaint,
                        (int) (mViewWidth),
                        Layout.Alignment.ALIGN_NORMAL,
                        1,
                        1,
                        true);
        staticLayout.draw(canvas);
    }

    /**
     * Draw the text, with origin at (x,y), using the specified paint.
     *
     * @param canvas
     */
    private void drawText(Canvas canvas) {
        drawTitleText("drawText", canvas);
        canvas.translate(0, mTextHeight);

        //绘制文本
        final String content = "文字绘制:AaJjPp";

        mTextPaint.reset();
        mTextPaint.setStrokeWidth(2);
        mTextPaint.setColor(Color.parseColor("#444444"));
        mTextPaint.setTextSize(mResources.getDimensionPixelSize(R.dimen.textsize_30sp));
        mTextPaint.setStyle(Paint.Style.FILL);

        //获取属性值
        // 获取绘制文本高度：（基线位置）
        final Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        final float top = fontMetrics.top;
        final float bottom = fontMetrics.bottom;
        final float textHeight = bottom - top;
        final float ascent = fontMetrics.ascent;
        final float descent = fontMetrics.descent;

        Logger.e("zxm", "top:" + top + "..bottom:" + bottom
                + "..ascent:" + ascent + "..descent:" + descent + "..textHeight:" + textHeight);

        final float x = (mViewWidth - mTextPaint.measureText(content)) / 2;

        canvas.drawText(content, x, textHeight, mTextPaint);


        //注意：位置都是相对于基线的距离
        //-------绘制基线（baseline）--------//
        final String baseline = "baseline";
        mPaint.reset();
        mPaint.setColor(Color.parseColor("#444444"));
        mPaint.setStrokeWidth(1);

        final float baseStartX = x - mViewPadding / 2;
        final float baseStopX = baseStartX + mTextPaint.measureText(content) + mViewPadding;
        canvas.drawLine(baseStartX, textHeight, baseStopX, textHeight, mPaint);
        //绘制基线Tag
        mTextPaint.reset();
        mTextPaint.setStrokeWidth(1);
        mTextPaint.setColor(Color.parseColor("#444444"));
        mTextPaint.setTextSize(mResources.getDimensionPixelSize(R.dimen.textsize_12sp));
        mTextPaint.setStyle(Paint.Style.FILL);

        final float tagHeight = mTextPaint.getFontMetrics().bottom - mTextPaint.getFontMetrics().top;
        canvas.drawText(baseline, baseStartX - mTextPaint.measureText(baseline) - 4, textHeight + tagHeight / 4, mTextPaint);

        //-------绘制top--------//
        final String topTag = "top";
        mPaint.reset();
        mPaint.setColor(Color.parseColor("#4a90e5"));
        mPaint.setStrokeWidth(1);
        canvas.drawLine(baseStartX, top + textHeight, baseStopX, top + textHeight, mPaint);

        //绘制基线Tag
        mTextPaint.reset();
        mTextPaint.setStrokeWidth(1);
        mTextPaint.setColor(Color.parseColor("#4a90e5"));
        mTextPaint.setTextSize(mResources.getDimensionPixelSize(R.dimen.textsize_12sp));
        mTextPaint.setStyle(Paint.Style.FILL);

        canvas.drawText(topTag, baseStopX + 4, top + textHeight, mTextPaint);

        //-------绘制ascent--------//
        final String ascentTag = "ascent";
        mPaint.reset();
        mPaint.setColor(Color.parseColor("#82d11e"));
        mPaint.setStrokeWidth(1);
        canvas.drawLine(baseStartX, ascent + textHeight, baseStopX, ascent + textHeight, mPaint);

        //绘制基线Tag
        mTextPaint.reset();
        mTextPaint.setStrokeWidth(1);
        mTextPaint.setColor(Color.parseColor("#82d11e"));
        mTextPaint.setTextSize(mResources.getDimensionPixelSize(R.dimen.textsize_12sp));
        mTextPaint.setStyle(Paint.Style.FILL);

        canvas.drawText(ascentTag, baseStopX + 4, ascent + textHeight + tagHeight / 4, mTextPaint);

        //-------绘制bottom--------//
        final String bottomTag = "bottom";
        mPaint.reset();
        mPaint.setColor(Color.parseColor("#e02729"));
        mPaint.setStrokeWidth(1);
        canvas.drawLine(baseStartX, bottom + textHeight, baseStopX, bottom + textHeight, mPaint);

        //绘制基线Tag
        mTextPaint.reset();
        mTextPaint.setStrokeWidth(1);
        mTextPaint.setColor(Color.parseColor("#e02729"));
        mTextPaint.setTextSize(mResources.getDimensionPixelSize(R.dimen.textsize_12sp));
        mTextPaint.setStyle(Paint.Style.FILL);

        canvas.drawText(bottomTag, baseStopX + 4, bottom + textHeight + tagHeight / 2, mTextPaint);

        //-------绘制descent--------//
        final String descentTag = "descent";
        mPaint.reset();
        mPaint.setColor(Color.parseColor("#f7a51e"));
        mPaint.setStrokeWidth(1);
        canvas.drawLine(baseStartX, descent + textHeight, baseStopX, descent + textHeight, mPaint);

        //绘制基线Tag
        mTextPaint.reset();
        mTextPaint.setStrokeWidth(1);
        mTextPaint.setColor(Color.parseColor("#f7a51e"));
        mTextPaint.setTextSize(mResources.getDimensionPixelSize(R.dimen.textsize_12sp));
        mTextPaint.setStyle(Paint.Style.FILL);

        canvas.drawText(descentTag, baseStopX + 4, descent + textHeight, mTextPaint);

        //绘制基准点
        mPaint.reset();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.RED);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(10);

        mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setColor(Color.RED);
        canvas.drawPoint(x, textHeight, mPaint);
        final String baseDot = "红色圆点为基准点";
        float textWidth = mTextPaint.measureText(baseDot);
        canvas.translate(0, bottom + textHeight);
        canvas.drawText(baseDot, (mViewWidth - textWidth) / 2, mTextHeight, mTextPaint);

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
