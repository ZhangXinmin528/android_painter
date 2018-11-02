package com.zxm.android_painter.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.zxm.android_painter.R;

/**
 * Created by ZhangXinmin on 2017/7/13.
 * Copyright (c) 2017 . All rights reserved.
 * 常规绘制：canvas.draw****();
 */

public class NormalView extends View {
    private static final String TAG = NormalView.class.getSimpleName();

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

    public NormalView(Context context) {
        this(context, null, 0);
    }

    public NormalView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NormalView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initParams();
    }

    private void initParams() {
        mResources = getResources();
        mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLinePaint.setStyle(Paint.Style.STROKE);
        mLinePaint.setStrokeWidth(1);

        mTextSize = mResources.getDimensionPixelSize(R.dimen.textsize_14sp);
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
        if (TextUtils.isEmpty(mDrawType)) return;
        switch (mDrawType) {
            case "drawArc":
                drawArc(canvas);//绘制圆弧
                break;
            case "drawBitmap":
                drawBitmap(canvas);//绘制BitMap
                break;
            case "drawColor":
                drawColor(canvas);//绘制颜色
                break;
            case "drawCircle"://绘制圆
                drawCircle(canvas);
                break;
            case "drawLine"://绘制线条
                drawLine(canvas);
                break;
            case "drawBitmapMesh":
                drawBitmapMesh(canvas);
                break;
            case "drawOval":
                drawOval(canvas);//绘制椭圆
                break;
            case "drawPath":
                drawPath(canvas);//绘制path
                break;
            case "drawPoint":
                drawPoint(canvas);//绘制点
                break;
            case "drawPaint":
                drawPaint(canvas);
                break;
            case "drawPoints":
                drawPoints(canvas);//绘制系列点
                break;
            case "drawRect":
                drawRect(canvas);//绘制矩形
                break;
            case "drawRGB":
                drawRGB(canvas);//绘制RGB
                break;
            case "drawRoundRect":
                drawRoundRect(canvas);//绘制圆角矩形
                break;
            case "drawText":
                drawText(canvas);//绘制文字
                break;
            case "drawTextOnPath":
                drawTextOnPath(canvas);//按照轨迹绘制文字
                break;
            case "drawTextRun":
                drawTextRun(canvas);
                break;
            case "drawVertices":
                drawVertices(canvas);
                break;
        }


    }

    /**
     * 顶点操作：操作画布
     *
     * @param canvas
     */
    private void drawVertices(Canvas canvas) {
        drawTitleText("drawVertices:未完待续...", canvas);
    }

    /**
     * 绘制文字指定文字的显示顺序
     *
     * @param canvas
     */
    private void drawTextRun(Canvas canvas) {
        drawTitleText("drawTextRun", canvas);
        canvas.translate(0, mTextHeight);
        String text = "枯藤老树昏鸦，小桥流水人家，古道西风瘦马。";
        int end = text.length();
        int contextEnd = text.length();
        float x = 0f;
        float y = 0f;
        boolean isRtl = true;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            canvas.drawTextRun(text, 0, end, 0, contextEnd, x, y, isRtl, mTextPaint);
        } else {
            drawTitleText("您的Android版本未达到6.0!", canvas);
        }
    }

    /**
     * 按照指定轨迹绘制文字
     * <p>
     * 1.hOffset:指的是文本相对于path的起始位置；
     * 2.vOffset:可正可负；若为正文字向圆心移动，
     * 若为负文字向圆外移动；
     * </p>
     *
     * @param canvas
     */
    private void drawTextOnPath(Canvas canvas) {
        //按照指定路径绘制文字：vOffset=0
        drawTitleText("按照指定路径绘制文字：vOffset=0", canvas);
        canvas.translate(0, mTextHeight);
        Path path = new Path();
        float left = mViewWidth / 2.0f - mRadius;
        float top = 0;
        float right = left + 2 * mRadius;
        float bottom = top + 2 * mRadius;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            path.addArc(left, top, right, bottom, -180, 180);
        } else {
            RectF rectF = new RectF(left, top, right, bottom);
            path.addArc(rectF, -180, 180);
        }
        String text = "星星之火，可以燎原！";
        float hOffset = (float) ((Math.PI * mRadius - mTextPaint.measureText(text)) / 2.0f);
        float vOffset = mTextHeight / 2.0f;
        mLinePaint.setColor(Color.RED);
        mLinePaint.setStrokeWidth(1);
        canvas.drawPath(path, mLinePaint);//绘制path
        canvas.drawTextOnPath(text, path, hOffset, 0, mTextPaint);//绘制文字

        //按照指定路径绘制文字：vOffset=mTextHeight / 2.0f
        canvas.translate(0, 2 * mRadius);
        drawTitleText("按照指定路径绘制文字：vOffset=mTextHeight / 2.0f", canvas);
        canvas.translate(0, mTextHeight);
        canvas.drawPath(path, mLinePaint);//绘制path
        canvas.drawTextOnPath(text, path, hOffset, vOffset, mTextPaint);//绘制文字

    }

    /**
     * 绘制文本
     *
     * @param canvas
     */
    private void drawText(Canvas canvas) {
        Paint paint = new Paint();
        float x = mViewPadding;
        float y = mViewPadding;
        canvas.drawText("Paint默认字号：" + paint.getTextSize(), x, y, mTextPaint);
        canvas.translate(0, mTextHeight);
        canvas.drawText("Paint默认样式：" + paint.getStyle(), x, y, mTextPaint);
        canvas.translate(0, mTextHeight);
        canvas.drawText("Paint默认颜色：" + paint.getColor(), x, y, mTextPaint);
        canvas.translate(0, mTextHeight);
        canvas.drawText("Paint默认线宽：" + paint.getStrokeWidth(), x, y, mTextPaint);
        canvas.translate(0, mTextHeight);
        canvas.drawText("Paint默认Cap：" + paint.getStrokeCap(), x, y, mTextPaint);
        canvas.translate(0, mTextHeight);
        canvas.drawText("Paint默认Join：" + paint.getStrokeJoin(), x, y, mTextPaint);
        canvas.translate(0, mTextHeight);
        canvas.drawText("Paint默认stroke miter value：" + paint.getStrokeMiter(), x, y, mTextPaint);
        canvas.translate(0, mTextHeight);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            canvas.drawText("Paint默认字符间距：" + paint.getLetterSpacing(), x, y, mTextPaint);
            canvas.translate(0, mTextHeight);
        }
        canvas.drawText("更多内容查阅PaintActivity!", x, y, mTextPaint);
    }

    /**
     * 绘制圆角矩形
     *
     * @param canvas
     */
    private void drawRoundRect(Canvas canvas) {
        //绘制圆角矩形:未填充
        drawTitleText("绘制圆角矩形:未填充", canvas);
        canvas.translate(0, mVerticalSpaceing);
        float left = mViewWidth / 2.0f - mRadius;
        float top = mVerticalSpaceing;
        float right = left + 2 * mRadius;
        float bottom = top + mRadius;
        mLinePaint.setColor(Color.BLUE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            canvas.drawRoundRect(left, top, right, bottom, mRadius / 4.0f, mRadius / 4.0f, mLinePaint);
        } else {
            RectF rectF = new RectF(left, top, right, bottom);
            canvas.drawRoundRect(rectF, mRadius / 4.0f, mRadius / 4.0f, mLinePaint);
        }

        //绘制圆角矩形:填充&边框
        canvas.translate(0, mRadius);
        drawTitleText("绘制圆角矩形:填充&边框", canvas);
        canvas.translate(0, mVerticalSpaceing);
        mFillPaint.setColor(Color.BLUE);
        mLinePaint.setColor(Color.RED);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            canvas.drawRoundRect(left, top, right, bottom, mRadius / 4.0f, mRadius / 4.0f, mFillPaint);
            canvas.drawRoundRect(left, top, right, bottom, mRadius / 4.0f, mRadius / 4.0f, mLinePaint);
        } else {
            RectF rectF = new RectF(left, top, right, bottom);
            canvas.drawRoundRect(rectF, mRadius / 4.0f, mRadius / 4.0f, mFillPaint);
            canvas.drawRoundRect(rectF, mRadius / 4.0f, mRadius / 4.0f, mLinePaint);
        }

    }

    /**
     * 绘制RGB背景
     *
     * @param canvas
     */
    private void drawRGB(Canvas canvas) {
        drawTitleText("draw RGB", canvas);
        canvas.drawRGB(255, 0, 0);
    }

    /**
     * 绘制矩形
     *
     * @param canvas
     */
    private void drawRect(Canvas canvas) {
        //绘制矩形：未填充
        drawTitleText("绘制矩形：未填充", canvas);
        float left = mViewWidth / 2.0f - mRadius;
        float top = mVerticalSpaceing;
        float right = left + 2 * mRadius;
        float bottom = top + mRadius;
        mLinePaint.setColor(Color.BLUE);
        canvas.drawRect(left, top, right, bottom, mLinePaint);

        //绘制矩形：填充&边框
        canvas.translate(0, mRadius);
        drawTitleText("绘制矩形：填充&边框", canvas);
        mFillPaint.setColor(Color.BLUE);
        mLinePaint.setColor(Color.RED);
        canvas.drawRect(left, top, right, bottom, mFillPaint);
        canvas.drawRect(left, top, right, bottom, mLinePaint);
    }

    /**
     * @param canvas
     */
    private void drawPoints(Canvas canvas) {
        drawTitleText("draw points", canvas);
        canvas.translate(0, mVerticalSpaceing);
        mLinePaint.setStrokeWidth(mRadius / 2);
        mLinePaint.setStrokeCap(Paint.Cap.ROUND);
        float[] pts = new float[]{mViewWidth / 2.0f - mRadius, mRadius, mViewWidth / 2.0f + mRadius, mRadius,
                mViewWidth / 2.0f - mRadius, mRadius * 2, mViewWidth / 2.0f + mRadius, mRadius * 2};
        canvas.drawPoints(pts, mLinePaint);
    }

    /**
     * 通过这种方式清屏
     *
     * @param canvas
     */
    private void drawPaint(Canvas canvas) {
        drawTitleText("测试drawPaint", canvas);
        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        canvas.drawPaint(paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
    }

    /**
     * 绘制点
     *
     * @param canvas
     */
    private void drawPoint(Canvas canvas) {
        //绘制点:BUTT
        drawTitleText("绘制点：" + mLinePaint.getStrokeCap(), canvas);
        canvas.translate(0, mVerticalSpaceing);
        mLinePaint.setStrokeWidth(mRadius);
        mLinePaint.setColor(Color.CYAN);
        float x = mViewWidth / 2.0f;
        float y = mRadius / 2.0f;
        canvas.drawPoint(x, y, mLinePaint);

        //绘制点：ROUND
        mLinePaint.setStrokeCap(Paint.Cap.ROUND);
        canvas.translate(0, mRadius * 2);
        drawTitleText("绘制点：" + mLinePaint.getStrokeCap(), canvas);
        canvas.translate(0, mVerticalSpaceing);
        canvas.drawPoint(x, y, mLinePaint);

        //绘制点：ROUND
        mLinePaint.setStrokeCap(Paint.Cap.SQUARE);
        canvas.translate(0, mRadius * 2);
        drawTitleText("绘制点：" + mLinePaint.getStrokeCap(), canvas);
        canvas.translate(0, mVerticalSpaceing);
        canvas.drawPoint(x, y, mLinePaint);

    }

    /**
     * 绘制PAth
     *
     * @param canvas
     */
    private void drawPath(Canvas canvas) {
        //绘制path：未闭合
        Path path = new Path();
        drawTitleText("绘制path:闭合", canvas);
        path.moveTo(mViewPadding, 0);
        path.lineTo(mRadius, mRadius);
        path.lineTo(mRadius * 2, mRadius);
        path.lineTo(mRadius * 3, mRadius * 2);
        path.close();
        canvas.drawPath(path, mLinePaint);

        canvas.translate(0, mRadius * 2);
        drawTitleText("绘制path:未闭合", canvas);
        mLinePaint.setStyle(Paint.Style.STROKE);
        mLinePaint.setColor(Color.BLUE);
        path.reset();
        path.moveTo(mViewPadding, 0);
        path.lineTo(mRadius, mRadius);
        float left = mRadius;
        float top = 0;
        float right = left + mRadius * 2;
        float bottom = top + mRadius * 2;
        RectF rectF = new RectF(left, top, right, bottom);
        path.addArc(rectF, 180, -180);
        canvas.drawPath(path, mLinePaint);

        //绘制组合图形
        canvas.translate(0, mRadius * 2);
        drawTitleText("了解更多参照PathActivity!", canvas);

    }

    /**
     * 绘制椭圆
     *
     * @param canvas
     */
    private void drawOval(Canvas canvas) {
        drawTitleText("绘制椭圆:未填充", canvas);
        float left = mViewWidth / 2.0f - mRadius;
        float top = mVerticalSpaceing;
        float right = mViewWidth / 2.0f + mRadius;
        float bottom = top + mRadius;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            canvas.drawOval(left, top, right, bottom, mLinePaint);
        } else {
            RectF rectF = new RectF(left, top, right, bottom);
            canvas.drawOval(rectF, mLinePaint);
        }

        //填充&边框
        canvas.translate(0, mRadius);
        drawTitleText("绘制椭圆:填充&边框", canvas);
        mFillPaint.setColor(Color.GREEN);
        mLinePaint.setColor(Color.RED);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            canvas.drawOval(left, top, right, bottom, mFillPaint);
            canvas.drawOval(left, top, right, bottom, mLinePaint);
        } else {
            RectF rectF = new RectF(left, top, right, bottom);
            canvas.drawOval(rectF, mFillPaint);
            canvas.drawOval(rectF, mLinePaint);
        }

    }

    /**
     * 顶点操作：操作Bitmap
     * 绘制复杂图片
     *
     * @param canvas
     */
    private void drawBitmapMesh(Canvas canvas) {
        drawTitleText("drawBimapMesh:待续...", canvas);
//        canvas.drawBitmapMesh();
    }

    /**
     * 绘制线条
     *
     * @param canvas
     */
    private void drawLine(Canvas canvas) {

        //绘制线条：普通
        drawTitleText("绘制线条:普通", canvas);
        canvas.translate(0, mVerticalSpaceing);
        mLinePaint.setColor(Color.BLUE);
        float startX = (mViewWidth - mRadius) / 2;
        float startY = 0;
        float stopX = startX + mRadius;
        float stopY = startY + mRadius;
        canvas.drawLine(startX, startY, stopX, stopY, mLinePaint);
        //
        canvas.translate(0, mRadius);
        drawTitleText("绘制线条:组合", canvas);
        float pts[] = new float[]{mViewPadding, 0,
                mRadius, mRadius,
                mRadius, mRadius,
                mRadius * 3, mRadius / 2};
        canvas.drawLines(pts, mLinePaint);
    }

    /**
     * 绘制圆
     *
     * @param canvas
     */
    private void drawCircle(Canvas canvas) {
        //绘制圆:未填充
        drawTitleText("绘制圆：未填充", canvas);
        mLinePaint.setColor(Color.BLUE);
        float cx = mViewWidth / 2.0f;
        float cy = mVerticalSpaceing + mRadius;
        canvas.drawCircle(cx, cy, mRadius, mLinePaint);

        //绘制圆：填充
        canvas.translate(0, mRadius * 2 + mVerticalSpaceing);
        drawTitleText("绘制圆：填充", canvas);
        mLinePaint.setColor(Color.BLACK);
        mFillPaint.setColor(Color.GREEN);
        canvas.drawCircle(cx, cy, mRadius, mFillPaint);
        canvas.drawCircle(cx, cy, mRadius, mLinePaint);
        //绘制圆环
        canvas.translate(0, mRadius * 2 + mVerticalSpaceing);
        drawTitleText("绘制圆环", canvas);
        mFillPaint.setColor(Color.GREEN);
        canvas.drawCircle(cx, cy, mRadius, mFillPaint);
        mFillPaint.setColor(Color.WHITE);
        canvas.drawCircle(cx, cy, mRadius * 3 / 4, mFillPaint);
    }

    /**
     * 绘制颜色
     *
     * @param canvas
     */
    private void drawColor(Canvas canvas) {
        //绘制颜色
        canvas.drawColor(Color.RED);
    }

    /**
     * 绘制Bitmap
     * 关于裁剪图片：
     * 1.Rect src:获取原图片的一部分，要显示图片区域；
     * 2.Rect dst:显示范围;
     * 3.若src>dst,则缩小src区域；
     * 4.若src<dst,则放大src区域；
     *
     * @param canvas
     */
    private void drawBitmap(Canvas canvas) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inSampleSize = 4;
        //绘制原始图片：压缩4倍
        Bitmap originalBitmap = BitmapFactory.decodeResource(mResources, R.drawable.example, opt);
        drawTitleText("绘制Bitmap:加载图片-压缩4倍", canvas);
        float left = (mViewWidth - originalBitmap.getWidth()) / 2.0f;
        float top = mVerticalSpaceing;
        canvas.drawBitmap(originalBitmap, left, top, mBitmapPaint);

        //加载原始图片
        canvas.translate(0, originalBitmap.getHeight());
        drawTitleText("绘制Bitmap:加载原始图片", canvas);
        Bitmap clipBitmap = BitmapFactory.decodeResource(mResources, R.drawable.girl);
        left = (mViewWidth - clipBitmap.getWidth()) / 2.0f;
        canvas.drawBitmap(clipBitmap, left, top, mBitmapPaint);

        //裁剪图片：缩小
        canvas.translate(0, clipBitmap.getHeight());
        drawTitleText("绘制Bitmap:裁剪图片", canvas);
        int l = (int) left;
        int t = (int) mVerticalSpaceing;
        Rect src = new Rect(//裁切区域
                clipBitmap.getWidth() / 4,
                0,
                clipBitmap.getWidth() * 3 / 4,
                clipBitmap.getHeight());
        Rect dst = new Rect(//显示位置
                l,
                t,
                l + clipBitmap.getWidth(),
                t + clipBitmap.getHeight());
        canvas.drawBitmap(clipBitmap, src, dst, mBitmapPaint);
    }


    /**
     * 绘制弧
     * draw the special arc
     * 0.0度在3点钟方向，顺时针为正，依次改变；
     * 1.startAngle：起始角度。若为0，从3点钟方向开始；若为负值或者大于360，按取模角度计算；
     * 2.sweepAngle：遵循顺时针为正原则，同样取模角度；
     * 3.useCenter：若为true,曲线进行封闭；
     *
     * @param canvas
     */
    private void drawArc(Canvas canvas) {
        //未封闭&未填充
        //绘制文字
        drawTitleText("绘制圆弧：未封闭&未填充", canvas);
        //绘制图形
        float left = mViewWidth / 2 - mRadius;
        float top = mVerticalSpaceing;
        float right = left + mRadius * 2;
        float bottom = top + mRadius * 2;
        mLinePaint.setStyle(Paint.Style.STROKE);
        mLinePaint.setColor(Color.BLUE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            canvas.drawArc(left, top, right, bottom, 0, 180, false, mLinePaint);
        } else {
            RectF rectF = new RectF(left, top, right, bottom);
            canvas.drawArc(rectF, 0, 180, false, mLinePaint);
        }

        //封闭&未填充
        canvas.translate(0, mRadius * 2);
        drawTitleText("绘制圆弧：封闭&未填充", canvas);
        //绘制图形
        mLinePaint.setStyle(Paint.Style.STROKE);
        mLinePaint.setColor(Color.BLUE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            canvas.drawArc(left, top, right, bottom, 0, 90, true, mLinePaint);
        } else {
            RectF rectF = new RectF(left, top, right, bottom);
            canvas.drawArc(rectF, 0, 90, true, mLinePaint);
        }
        //填充&绘制边框
        canvas.translate(0, mRadius * 2);
        drawTitleText("绘制圆弧：填充&绘制边框", canvas);
        mFillPaint.setColor(Color.GREEN);
        mLinePaint.setColor(Color.RED);
        //绘制图形
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            canvas.drawArc(left, top, right, bottom, -45, 90, true, mFillPaint);
            canvas.drawArc(left, top, right, bottom, -45, 90, true, mLinePaint);
        } else {
            RectF rectF = new RectF(left, top, right, bottom);
            canvas.drawArc(rectF, -45, 90, true, mFillPaint);
            canvas.drawArc(rectF, -45, 90, true, mLinePaint);
        }
    }

    /**
     * 绘制文本
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

    /**
     * Set the drawtype
     *
     * @param drawType
     */
    public void setmDrawType(String drawType) {
        if (TextUtils.isEmpty(drawType)) return;
        this.mDrawType = drawType;
        invalidate();
    }
}
