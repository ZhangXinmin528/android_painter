package com.example.android_painter.view.chart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.android_painter.R;
import com.example.android_painter.model.chart.HistogramInfo;
import com.example.android_painter.util.DensityUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZXM on 2017/1/8.
 * 大战略-大盘柱状图
 */

public class HistogramChart extends View {
    private static final String TAG = HistogramChart.class.getSimpleName();

    private List<HistogramInfo> mList;

    private Paint mBackPaint;// 背景画笔
    private Paint mPaint;// 绘制图形
    private Paint mTextPaint;// 绘制文本的画笔
    private float mTextSize;//文本字号

    private float mHisWidth;//每个柱子的宽度
    private float mTextWidth;//文字宽度；
    private float mTextHeight;//文字高度
    private float mLabelWight;//百分比标签宽度
    private float mRestWidth;//多余的宽度；
    private int mBottomPadding;// px
    private int mScreenWidth;
    private int mScreenHeight;


    private String[] arr = {"近", "期", "静", "态", "远", "期", "静", "态", "综", "合", "动", "态"};

    public HistogramChart(Context context) {
        super(context, null);
    }

    public HistogramChart(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mBottomPadding = DensityUtil.dip2px(getContext(), 4);
        mList = new ArrayList<>();

        //背景画笔
        mBackPaint = new Paint();
        mBackPaint.setStyle(Paint.Style.FILL);//填充样式

        //图表绘制
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.BLUE);

        //文字绘制
        mTextPaint = new Paint();
        mTextSize = getResources().getDimensionPixelSize(R.dimen.textsize_14sp);
        mTextPaint.setTextSize(mTextSize);
        //获取文字宽度
        mTextWidth = mTextPaint.measureText("近");
        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        mTextHeight = (float) ((fontMetrics.bottom - fontMetrics.top) * 0.8);//粗略高度

        //获取宽度属性
        mRestWidth = 30.0f;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Log.i(TAG, "onDraw");

        mScreenWidth = getMeasuredWidth();
        mScreenHeight = getMeasuredHeight();
        mHisWidth = (mScreenWidth - mRestWidth) / 6.0f;

        drawBack(canvas);//绘制背景

        if (mList.isEmpty())
            return;

        drawHistogram(canvas);
        drawText(canvas);

    }

    /**
     * 绘制文本
     *
     * @param canvas
     */
    private void drawText(Canvas canvas) {
        mTextPaint.reset();
        mTextPaint.setColor(Color.BLACK);
        mTextSize = getResources().getDimensionPixelSize(R.dimen.textsize_14sp);
        mTextPaint.setTextSize(mTextSize);

        for (int n = 0; n < arr.length; n++) {
            float left = (2 * (n / 4) + 1) * mHisWidth - mTextWidth;//左侧坐标
            float top = mScreenHeight - mBottomPadding - (4 - (n % 4) - 1) * mTextHeight;//y的位置很重要
            String currText = arr[n];
            canvas.drawText(currText, left, top, mTextPaint);
        }
    }

    /**
     * 绘制柱状图
     *
     * @param canvas
     */
    private void drawHistogram(Canvas canvas) {
        int[] colorBlue = new int[]{Color.BLUE, Color.GREEN};
        int[] colorRed = new int[]{Color.RED, Color.YELLOW};

        if (mList.isEmpty()) return;
        //重新设置画笔
        mTextPaint.reset();
        mTextPaint.setColor(Color.BLACK);
        mTextSize = getResources().getDimensionPixelSize(R.dimen.textsize_10sp);
        mTextPaint.setTextSize(mTextSize);
        //绘制柱状图
        for (int j = 0; j < mList.size(); j++) {
            HistogramInfo info = mList.get(j);

            float currValue = info.getValue();
            String currPer = (int) Math.floor(currValue) + "%";//当前百分比
            float left = (2 * j + 1) * mHisWidth;
            float right = left + mHisWidth;
            float top = mScreenHeight - ((currValue) * mScreenHeight / 100);
            float bottom = mScreenHeight;
            //颜色梯度
            switch (info.getType()) {
                case 0://blue-green
                    Shader shader0 = new LinearGradient(left, top, right, bottom, colorBlue, null, Shader.TileMode.MIRROR);
                    mPaint.setShader(shader0);
                    break;
                case 1://red-yellow
                    Shader shader1 = new LinearGradient(left, top, right, bottom, colorRed, null, Shader.TileMode.MIRROR);
                    mPaint.setShader(shader1);
                    break;
            }
            canvas.drawRect(left, top, right, bottom, mPaint);
            canvas.drawText(currPer, left + (mHisWidth - mTextPaint.measureText(currPer)) / 2,
                    mScreenHeight - DensityUtil.dip2px(getContext(), 2), mTextPaint);
        }
    }

    /**
     * 绘制背景色带
     */
    private void drawBack(Canvas canvas) {
        //绘制背景色带
        int left = 0;//左侧
        int top = 0;//上
        int right = mScreenWidth;//右侧
        int bottom = 0;//下

        int perHeight = mScreenHeight / 5;//色带高度
        //颜色属性
        int[] arr = {getResources().getColor(R.color.market_his_leve1),
                getResources().getColor(R.color.market_his_leve2),
                getResources().getColor(R.color.market_his_leve3),
                getResources().getColor(R.color.market_his_leve4),
                getResources().getColor(R.color.market_his_leve5)};

        for (int i = 0; i < 5; i++) {
            top = i * perHeight;
            bottom = (i + 1) * perHeight;
            mBackPaint.setColor(arr[i]);
            canvas.drawRect(left, top, right, bottom, mBackPaint);
        }

    }

    /**
     * 为图表添加数据
     * <p>
     * 只允许添加三个数据
     * </P>
     *
     * @param list 第一个数据
     */
    public void bindValuesToChart(List<HistogramInfo> list) {
        if (list == null || list.isEmpty()) {
            Log.e(TAG, "未添加有效数据！");
            return;
        }
        this.mList = list;
    }

}
