package com.example.android_painter.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android_painter.R;
import com.example.android_painter.api.HttpApi;
import com.example.android_painter.http.callback.MultiJsonCallback;
import com.example.android_painter.http.response.LazyResponse;
import com.example.android_painter.model.ImageInfo;
import com.example.android_painter.ui.base.BaseActivity;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZhangXinmin on 2017/9/17.
 * Copyright (c) 2017 . All rights reserved.
 * The splash activity for the app.
 */

public class SplashActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mBackIv;
    private TextView mCountTimeTV;
    private TextView mTipsTv;
    private TextView mTimeTv;
    private List<ImageInfo> mSplashData;
    private int mSelectedIndex;
    private CountDownTimer mTimer;

    @Override
    protected Object setLayout() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initParamsAndViews() {
        mSplashData = new ArrayList<>();

    }

    @Override
    protected void initViews() {
        mBackIv = (ImageView) findViewById(R.id.iv_splash_bg);
        mCountTimeTV = (TextView) findViewById(R.id.tv_splash_count);
        mCountTimeTV.setOnClickListener(this);
        mTipsTv = (TextView) findViewById(R.id.tv_slpash_tips);
        mTimeTv = (TextView) findViewById(R.id.tv_splash_time);

        findViewById(R.id.tv_change).setOnClickListener(this);

        mTimer = new CountDownTimer(4000, 1000) {
            @Override
            public void onTick(long l) {
                mCountTimeTV.setText(getString(R.string.count_tips, l / 1000 + ""));
            }

            @Override
            public void onFinish() {
                jumpToHome();
            }
        };
    }

    @Override
    protected void requestDataFromNet() {
        requestSplashData();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mTimer.start();
    }

    /**
     * 请求启动页数据
     */
    private void requestSplashData() {
        OkGo.<LazyResponse<List<ImageInfo>>>get(HttpApi.SPLASH_API)
                .tag(mContext)
                .execute(new MultiJsonCallback<LazyResponse<List<ImageInfo>>>() {

                    @Override
                    public void onSuccess(Response<LazyResponse<List<ImageInfo>>> response) {
                        if (response != null && response.body().data != null) {
                            List<ImageInfo> list = response.body().data;

                            if (!list.isEmpty()) {
                                mSplashData.addAll(list);
//                                setDataForSplash();
                            }
                        }
                    }
                });
    }

    /**
     * 设置信息
     */
    private void setDataForSplash() {
        int size = mSplashData.size();
        ImageInfo info = mSplashData.get(mSelectedIndex % size);
        //背景图片
        Picasso.with(mContext)
                .load(info.getUrl())
                .config(Bitmap.Config.RGB_565)
                .placeholder(R.drawable.icon_splash)
                .error(R.drawable.icon_splash)
                .fit()
                .into(mBackIv);

        //tips
        if (!TextUtils.isEmpty(info.getCopyright()))
            mTipsTv.setText(info.getCopyright());

        if (!TextUtils.isEmpty(info.getDate()))
            mTimeTv.setText(info.getDate());

        mSelectedIndex++;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_change://改变背景
                setDataForSplash();
                break;
            case R.id.tv_splash_count://计时
                mTimer.cancel();
                jumpToHome();
                break;
        }
    }

    /**
     * 页面跳转
     */
    private void jumpToHome() {
        Intent intent = new Intent(mContext, HomeActivity.class);
        startActivity(intent);
        finish();
    }
}
