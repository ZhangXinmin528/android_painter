package com.example.android_painter.ui;

import android.content.Intent;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.android_painter.R;
import com.example.android_painter.ui.base.BaseActivity;

/**
 * Created by ZhangXinmin on 2017/9/17.
 * Copyright (c) 2017 . All rights reserved.
 * The icon_splash activity for the app.
 */

public class SplashActivity extends BaseActivity implements View.OnClickListener {

    private TextView mCountTimeTV;
    private CountDownTimer mTimer;
    private LottieAnimationView mLottieAnimationView;

    @Override
    protected Object setLayout() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initParamsAndViews() {
    }

    @Override
    protected void initViews() {
        mCountTimeTV = (TextView) findViewById(R.id.tv_splash_count);
        mCountTimeTV.setOnClickListener(this);
        mLottieAnimationView = findViewById(R.id.lottie_splash);

        mTimer = new CountDownTimer(4000, 1000) {
            @Override
            public void onTick(long l) {
            }

            @Override
            public void onFinish() {
                jumpToHome();
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        mTimer.start();
        mLottieAnimationView.setAnimation("animated_laptop_.json");
        mLottieAnimationView.playAnimation();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_splash_count://计时
                if (mTimer != null) {
                    mTimer.cancel();
                }
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

    @Override
    protected void onDestroy() {
        if (mTimer != null) {
            mTimer.cancel();
        }
        if (mLottieAnimationView != null) {
            mLottieAnimationView.cancelAnimation();
        }
        super.onDestroy();
    }
}
