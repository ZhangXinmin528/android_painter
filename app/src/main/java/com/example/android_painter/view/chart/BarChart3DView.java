package com.example.android_painter.view.chart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.Toast;

import com.example.android_painter.R;
import com.example.android_painter.model.chart.BarInfo;
import com.example.android_painter.util.DecimalFormatUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZhangXinmin on 2017/2/12.
 * Copyright (c) 2017 . All rights reserved.
 * 三维柱状图
 */

public class BarChart3DView extends BaseChart {

    private static final String TAG = BarChart3DView.class.getSimpleName();

    public static final int ATTRIBUTE_1 = 1;
    public static final int ATTRIBUTE_2 = 2;
    public static final int ATTRIBUTE_3 = 3;
    public static final int ATTRIBUTE_4 = 4;
    public static final int ATTRIBUTE_5 = 5;
    public static final int ATTRIBUTE_6 = 6;

    private List<BarInfo> mList;//数据集合
    //刻度线
    private float mMaxValue;//最大值
    private float mMaxValueLable;//刻度最大值
    private int mAxisOffset = 2;//坐标轴单位刻度
    private float mTopOffset;//顶部留白
    //偏移像素
    private float mXYPer;
    private float mYZPer;
    //画笔
    private Paint mLinePaint;//画线
    private Paint mTextPaint;//绘制文字
    private Paint mPaint;//绘制图形
    private PathEffect mPathEffect;//虚线
    private float fontSize;//字号
    //绘制三维柱体
    private int mBarMaxNum;//绘制柱状图的最大个数
    private int mBarWidth;//绘制3D柱状图的宽度
    private int mDividerWidth;//间距宽度
    //触摸点坐标
    private float pointX;
    private float pointY;
    private float slopX;
    private float slopY;
    private int mTouchSlop;//最小识别滑动距离
    private int mBarFirstVisible;//第一个可见的柱状图；
    private float mSlopDistance;//移动距离；
    //当前页码位置
    private int mCurrPage;//当前页数位置
    private int mTotalPage;//总页数
    private OnPageTurningListener mListener;//监听
    private int mAttribute;//位置属性

    public BarChart3DView(Context context) {
        super(context);
        initBar3DChart();
    }

    public BarChart3DView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initBar3DChart();
    }

    public BarChart3DView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initBar3DChart();
    }

    /**
     * 添加翻页监听
     *
     * @param listener
     */
    public void setPageTurningListener(OnPageTurningListener listener) {
        this.mListener = listener;
    }

    //初始化参数
    private void initBar3DChart() {
        mAxisOffset = 2;//默认值
        mBarMaxNum = 10;//默认最大10个
        mCurrPage = 1;//默认第一页
        mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();//获取最小识别距离
        mList = new ArrayList<>();

        //初始化画笔
        mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLinePaint.setStyle(Paint.Style.STROKE);
        mLinePaint.setStrokeWidth(1);
        mLinePaint.setAntiAlias(true);
        mLinePaint.setColor(Color.BLACK);
        //绘制字体
        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(Color.BLACK);
        fontSize = getResources().getDimensionPixelSize(R.dimen.textsize_14sp);//字号
        mTextPaint.setTextSize(fontSize);
        mTextPaint.setStrokeCap(Paint.Cap.ROUND);//设置画笔的笔触风格,离开画板时最后留下的图形
        mTextPaint.setStrokeWidth(1);
        //图形绘制
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);

        mPathEffect = new DashPathEffect(new float[]{4, 2, 4, 2}, 4);//虚线

        //获取顶部留白像素
        mTopOffset = (mTextPaint.descent() - mTextPaint.ascent()) * 2;//
    }

    /**
     * 为图表添加数据
     *
     * @param attribute 位置属性
     * @param data      数据集合
     */
    public void setDataForView(int attribute, List<BarInfo> data) {
        if (data == null)
            return;
        mList = data;
        mAttribute = attribute;
        for (BarInfo info : mList) {
            float value = info.getmValue();
            mMaxValue = mMaxValue > value ? mMaxValue : value;
        }
        mMaxValueLable = (int) Math.ceil(mMaxValue);

        if (mMaxValueLable <= 10) {
            mAxisOffset = 2;
        } else if (mMaxValueLable <= 50) {
            mAxisOffset = 10;
        } else if (mMaxValueLable <= 100) {
            mAxisOffset = 20;
        } else if (mMaxValueLable <= 250) {
            mAxisOffset = 50;
        } else if (mMaxValueLable <= 500) {
            mAxisOffset = 100;
        } else if (mMaxValueLable <= 1000) {
            mAxisOffset = 200;
        } else {
            mAxisOffset = 500;
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int per = (int) (getmXYWidth() * 0.98 / mBarMaxNum);
        mBarWidth = (int) (per * 0.6);
        mDividerWidth = (int) (per * 0.4);
        drawDashLines(canvas);//绘制坐标平面虚分割线
        drawBarIn3D(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN://在第一个点被按下时触发
                pointX = event.getX();
                pointY = event.getY();
                slopX = event.getRawX();
                slopY = event.getRawY();
                barPositionClicked(pointX, pointY);//三维柱状图点击事件
                break;
            case MotionEvent.ACTION_UP://当屏幕上唯一的点被放开时触发

                break;
            case MotionEvent.ACTION_MOVE://当有点在屏幕上移动时触发:
                float scrollX = event.getRawX() - slopX;//X向滑动距离

                if (Math.abs(scrollX) > mTouchSlop && scrollX != mSlopDistance) {//X向滑动距离大于最小识别距离视为向滑动
                    if (scrollX > 0) {
                        mBarFirstVisible = mBarFirstVisible - 1;
                        if (mBarFirstVisible < 0)
                            mBarFirstVisible = 0;
                    }
                    if (scrollX < 0 && mBarFirstVisible >= 0 && mBarFirstVisible <= mList.size() - mBarMaxNum) {
                        mBarFirstVisible = mBarFirstVisible + 1;
                    }
                    postInvalidate();
                    mSlopDistance = scrollX;
                    setmCurrPage(mBarFirstVisible / 10 + 1);//改变当前页数
                    mListener.onPageTurning(mAttribute, mCurrPage);//添加监听
                }

                break;
        }
        return true;
    }

    /**
     * 计算屏幕滑动距离对应的柱图的个数
     *
     * @param scrollX 滑动距离
     * @return 个数
     */
    private int calculateSlopCount(float scrollX) {
        int count = (int) (Math.abs(scrollX) / (mBarWidth + mDividerWidth));
        return count;
    }

    /**
     * 三维柱状图的点击事件:确定点击位置
     *
     * @param pointX 触摸点X坐标
     * @param pointY 触摸点Y坐标
     */
    private void barPositionClicked(float pointX, float pointY) {
        if (mList.isEmpty()) return;
        double per = mMaxValue / getmXYHeight();//比率

        int count = mList.size();//数据个数
        for (int i = 0; mBarMaxNum <= count && i < mBarMaxNum; i++) {
            BarInfo info = mList.get(i);
            float currValue = info.getmValue();
            ///绘制前部柱形图，计算坐标
            float left = getmLeftPadding() + mDividerWidth * (i + 1) + mBarWidth * i;
            float top = (float) (getmOffset() + getmYZHeight() - per * currValue);
            float right = getmLeftPadding() + (mDividerWidth + mBarWidth) * (i + 1);
            float bottom = getmOffset() + getmYZHeight();

            //判断触摸点是否在柱体前柱面内
            if (pointX > left && pointX < right && pointY < bottom && pointY > top) {
                Toast.makeText(getContext(), "点击了第：" + (i + mBarFirstVisible) + "个标签", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 绘制3D柱状图
     *
     * @param canvas
     */
    private void drawBarIn3D(Canvas canvas) {
        if (mList.isEmpty()) return;
        double per = (getmXYHeight() - mTopOffset) / mMaxValue;//比率

        for (int i = 0; i < mBarMaxNum && mBarFirstVisible + i < mList.size(); i++) {
            BarInfo info = mList.get(i + mBarFirstVisible);
            float currValue = info.getmValue();
            String strValue = DecimalFormatUtil.formatNummber(info.getmValue(), DecimalFormatUtil.STYLE_3);
            String currLabel = info.getmLable();//标签
            boolean currstate = info.isMarked();//状态标记
            //绘制前部柱形图，计算坐标
            float left = getmLeftPadding() + mDividerWidth * (i + 1) + mBarWidth * i;
            float top = (float) (getmOffset() + getmYZHeight() - per * currValue);
            float right = getmLeftPadding() + (mDividerWidth + mBarWidth) * (i + 1);
            float bottom = getmOffset() + getmYZHeight();

            if (currstate) {
                mPaint.setColor(mResources.getColor(R.color.colorCyan));
            } else {
                mPaint.setColor(mResources.getColor(R.color.colorRed));
            }

            canvas.drawRect(left, top, right, bottom, mPaint);
            //绘制边框
            mLinePaint.reset();
            mLinePaint.setStyle(Paint.Style.STROKE);
            mLinePaint.setStrokeWidth(1);
            mLinePaint.setAntiAlias(true);
            mLinePaint.setColor(Color.BLACK);
            canvas.drawRect(left, top, right, bottom, mLinePaint);//绘制边框强化3D效果

            //绘制上表面
            Path upPath = new Path();

            float fx = right; //前部右上
            float fy = top;
            float bxl = left + getmYZWidth();//后部左上
            float byl = (float) (getmTopPadding() + getmXYHeight() - per * currValue);
            float bxr = right + getmYZWidth();//后部右上
            float byr = (float) (getmTopPadding() + getmXYHeight() - per * currValue);
            upPath.moveTo(left, top);//前左上
            upPath.lineTo(bxl, byl);//后左上
            upPath.lineTo(bxr, byr);//后右上
            upPath.lineTo(fx, fy);//前右上
            upPath.close();
            canvas.drawPath(upPath, mPaint);
            canvas.drawPath(upPath, mLinePaint);//绘制边框强化3D效果

            //绘制右侧表面
            Path rightPath = new Path();
            //后下
            float bx = right + getmYZWidth();
            float by = getmTopPadding() + getmXYHeight();
            rightPath.moveTo(fx, fy);//前上
            rightPath.lineTo(bxr, byr);//后上
            rightPath.lineTo(bx, by);//后下
            rightPath.lineTo(right, bottom);
            rightPath.close();
            canvas.drawPath(rightPath, mPaint);
            canvas.drawPath(rightPath, mLinePaint);//绘制边框强化3D效果

            //绘制柱状图上的文字标签
            float width = (float) ((right - left) * 1.3);//框的宽度
            float height = width / 2;//框的高度
            //竖线高度
            float even = (float) (getmXYHeight() * 0.05);//偶数
            //竖线位置:自下向上绘制
            float vertialX = (bxr - left) / 2 + left;//x位置
            float startY = (top - byr) / 2 + byr;//底部
            mPaint.reset();
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setColor(mResources.getColor(R.color.colorGray));
            //绘制白色竖线
            mLinePaint.reset();
            mLinePaint.setStyle(Paint.Style.STROKE);
            mLinePaint.setStrokeWidth(1);
            mLinePaint.setAntiAlias(true);
            mLinePaint.setColor(Color.WHITE);
            canvas.drawLine(vertialX, startY, vertialX, startY - even, mLinePaint);

            //绘制提示框
            mLinePaint.reset();
            mLinePaint.setStyle(Paint.Style.STROKE);
            mLinePaint.setStrokeWidth(1);
            mLinePaint.setAntiAlias(true);
            mLinePaint.setColor(Color.BLACK);

            float leftTips = vertialX - width / 2;
            float topTips = startY - even - height;
            float rightTips = vertialX + width / 2;
            float bottomTips = startY - even;
            canvas.drawRect(leftTips, topTips, rightTips, bottomTips, mPaint);//绘制背景
            canvas.drawRect(leftTips, topTips, rightTips, bottomTips, mLinePaint);//绘制边框
            fontSize = getResources().getDimensionPixelSize(R.dimen.textsize_14sp);//字号
            mTextPaint.setTextSize(fontSize);
            //绘制提示内容
            canvas.drawText(currLabel, leftTips + (width - mTextPaint.measureText(currLabel)) / 2,
                    topTips + fontSize, mTextPaint);
            fontSize = getResources().getDimensionPixelSize(R.dimen.textsize_12sp);//字号
            mTextPaint.setTextSize(fontSize);
            canvas.drawText(strValue, leftTips + (width - mTextPaint.measureText(strValue)) / 2,
                    bottomTips - 5, mTextPaint);

        }
    }

    /**
     * 绘制坐标平面虚分割线,并绘制Y轴标签
     * <p>
     * 绘制XY，YZ坐标面的网格线（水平）；绘制Y轴标签文字
     * </p>
     *
     * @param canvas
     */
    private void drawDashLines(Canvas canvas) {

        //绘制XY平面分割线
        if (mMaxValueLable == 0) return;
        int n = (int) (mMaxValueLable / mAxisOffset);
        int remain = (int) (mMaxValueLable % mAxisOffset);

        //画线画笔
        mLinePaint.reset();
        mLinePaint.setStyle(Paint.Style.STROKE);
        mLinePaint.setStrokeWidth(1);
        mLinePaint.setAntiAlias(true);
        mLinePaint.setColor(getResources().getColor(R.color.colorLightGray));
        mLinePaint.setPathEffect(mPathEffect);

        if (remain != 0) {
            n = n + 1;
        }
        mXYPer = (getmXYHeight() - mTopOffset) / n;
        //绘制XY坐标面的网格线（水平）
        for (int i = 1; i <= n; i++) {
            Path path = new Path();
            path.moveTo(getmLeftPadding() + getmYZWidth(), getmTopPadding() + getmXYHeight() - mXYPer * i);
            path.lineTo(getmLeftPadding() + getmYZWidth() + getmXYWidth(), getmTopPadding() + getmXYHeight() - mXYPer * i);
            path.close();
            canvas.drawPath(path, mLinePaint);
        }

        mYZPer = (getmYZHeight() - mTopOffset) / n;
        //绘制YZ坐标面的网格线（水平）
        for (int i = 1; i <= n; i++) {
            Path path = new Path();
            path.moveTo(getmLeftPadding(), getmOffset() + getmYZHeight() - i * mYZPer);
            path.lineTo(getmLeftPadding() + getmYZWidth(), getmTopPadding() + getmYZHeight() - i * mYZPer);
            path.close();
            canvas.drawPath(path, mLinePaint);
        }

        //Y轴标签
        fontSize = getResources().getDimensionPixelSize(R.dimen.textsize_12sp);//字号
        mTextPaint.setTextSize(fontSize);
        //绘制Y轴标签
        float textWidth = mTextPaint.measureText(mAxisOffset + "");//文字长度
        for (int i = 0; i <= n; i++) {
            float x = getmLeftPadding() - textWidth - 20;
            float y = getmOffset() + getmYZHeight() - i * mYZPer;
            //绘制标记
            canvas.drawLine(getmLeftPadding() - 6, y,
                    getmLeftPadding() - 1, y, mLinePaint);

            //绘制Y轴标签
            canvas.drawText(0 + i * mAxisOffset + "", x, y + 5, mTextPaint);
        }
    }

    /**
     * 获取当前翻页位置
     *
     * @return
     */
    public int getmCurrPage() {
        return mCurrPage;
    }

    /**
     * 设置翻页位置
     *
     * @param mCurrPage
     */
    public void setmCurrPage(int mCurrPage) {
        this.mCurrPage = mCurrPage;
    }

    /**
     * 获取位置属性
     *
     * @return
     */
    public int getmAttribute() {
        return mAttribute;
    }

    /**
     * 设置位置属性
     *
     * @param mAttribute
     */
    public void setmAttribute(int mAttribute) {
        this.mAttribute = mAttribute;
    }

    /**
     * 设置第一个可见的柱子
     *
     * @param mBarFirstVisible
     */
    public void setmBarFirstVisible(int mBarFirstVisible) {
        if (mBarFirstVisible < mList.size() && mBarFirstVisible >= 0) {
            this.mBarFirstVisible = mBarFirstVisible;
            setmCurrPage(mBarFirstVisible / 10 + 1);//改变当前页数
            mListener.onPageTurning(mAttribute, mCurrPage);//添加监听
            invalidate();
        }
    }

    /**
     * 获取每页最大显示数
     *
     * @return
     */
    public int getmBarMaxNum() {
        return mBarMaxNum;
    }

    /**
     * 设置每页最大显示数
     *
     * @param mBarMaxNum
     */
    public void setmBarMaxNum(int mBarMaxNum) {
        this.mBarMaxNum = mBarMaxNum;
    }

    /**
     * 总页数
     *
     * @return
     */
    public String getmTotalPage() {

        if (mList.isEmpty()) {
            return null;
        } else {
            mTotalPage = mList.size() / mBarMaxNum;
            if ((mList.size() % mBarMaxNum) != 0)
                mTotalPage += 1;
            return mTotalPage + "";
        }
    }

    //翻页监听器
    public interface OnPageTurningListener {
        /**
         * 设置翻页
         *
         * @param attribute 位置属性
         * @param currPage  页数
         */
        void onPageTurning(int attribute, int currPage);
    }
}
