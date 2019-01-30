package com.zxm.android_painter.ui;

import android.content.Intent;
import android.os.CountDownTimer;
import android.view.View;

import com.airbnb.lottie.LottieAnimationView;
import com.zxm.android_painter.R;
import com.zxm.android_painter.ui.base.BaseActivity;

import static com.airbnb.lottie.LottieDrawable.INFINITE;

/**
 * Created by ZhangXinmin on 2017/9/17.
 * Copyright (c) 2017 . All rights reserved.
 * The icon_splash activity for the app.
 */

public class SplashActivity extends BaseActivity implements View.OnClickListener {

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
        findViewById(R.id.tv_splash_count).setOnClickListener(this);
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
        mLottieAnimationView.setAnimation("splash.json");
        mLottieAnimationView.setRepeatCount(INFINITE);
        mLottieAnimationView.setImageAssetsFolder("airbnb_slpash/");
        mLottieAnimationView.playAnimation();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_splash_count://计时
                if (mTimer != null) {
                    mTimer.cancel();
                    mTimer = null;
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
