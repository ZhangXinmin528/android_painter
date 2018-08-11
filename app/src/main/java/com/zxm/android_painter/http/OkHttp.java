package com.zxm.android_painter.http;

import android.app.Application;
import android.text.TextUtils;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.interceptor.HttpLoggingInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by ZhangXinmin on 2017/9/17.
 * Copyright (c) 2017 . All rights reserved.
 * okgo 网络请求框架封装
 */

public class OkHttp {
    private static final String TAG = "painter";

    private OkHttp() {
        throw new UnsupportedOperationException("You must't do this!");
    }

    /**
     * 创建OkHttpClient.Builder
     *
     * @param tag 拦截器标签
     * @return
     */
    private static OkHttpClient.Builder getOkHttpClientBuilder(String tag) {
        if (TextUtils.isEmpty(tag))
            tag = TAG;
        return getOkHttpClientBuilder(tag, 0, 0, 0);
    }

    /**
     * 创建OkHttpClient.Builder
     *
     * @param tag            拦截器标签
     * @param connectTimeOut 连接超时
     * @return
     */
    private static OkHttpClient.Builder getOkHttpClientBuilder(String tag, long connectTimeOut) {

        return getOkHttpClientBuilder(tag, connectTimeOut, 0, 0);
    }

    /**
     * 创建OkHttpClient.Builder
     *
     * @param tag            拦截器标签
     * @param connectTimeOut 连接超时
     * @param readTimeOut    读入超时
     * @param writeTimeOut   写入超时
     * @return
     */
    private static OkHttpClient.Builder getOkHttpClientBuilder(String tag, long connectTimeOut,
                                                               long readTimeOut, long writeTimeOut) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (TextUtils.isEmpty(tag)) {//设置拦截器
            builder.addInterceptor(new HttpLoggingInterceptor(tag));
        }
        if (connectTimeOut != 0)//连接超时
            builder.connectTimeout(connectTimeOut, TimeUnit.MILLISECONDS);

        if (readTimeOut != 0)//全局读取超时
            builder.readTimeout(readTimeOut, TimeUnit.MILLISECONDS);

        if (writeTimeOut != 0)//全局写入超时
            builder.readTimeout(writeTimeOut, TimeUnit.MILLISECONDS);

        return builder;
    }

    /**
     * 初始化OkGo
     * <p>
     * 使用默认OkHttpClient,无缓存策略，缓存永久有效，不进行重连.
     * </p>
     *
     * @param app Application
     */
    public static void init(Application app, String tag) {

        init(app,
                getOkHttpClientBuilder(tag).build(),
                CacheMode.NO_CACHE,
                CacheEntity.CACHE_NEVER_EXPIRE,
                -1);
    }

    /**
     * 初始化OkGo,设置重连次数
     * <p>
     * 使用默认OkHttpClient,无缓存策略，缓存永久有效.
     * </p>
     *
     * @param app        Application
     * @param retryCount 重连次数：默认3次，不需要设置为零
     */
    public static void init(Application app, String tag, int retryCount) {

        init(app,
                getOkHttpClientBuilder(tag).build(),
                CacheMode.NO_CACHE,
                CacheEntity.CACHE_NEVER_EXPIRE,
                retryCount);
    }

    /**
     * 初始化OkGo,设置重连次数
     * <p>
     * 无缓存策略，缓存永久有效.
     * </p>
     *
     * @param app          Application
     * @param okHttpClient OkHttpClient
     * @param retryCount   重连次数：默认3次，不需要设置为零
     */
    public static void init(Application app, OkHttpClient okHttpClient, int retryCount) {

        init(app,
                okHttpClient,
                CacheMode.NO_CACHE,
                CacheEntity.CACHE_NEVER_EXPIRE,
                retryCount);
    }

    /**
     * 初始化OkGo
     *
     * @param app          Application
     * @param okHttpClient OkHttpClient
     * @param cacheMode    缓存策略：默认不使用缓存
     * @param cacheTime    缓存时间：默认永不过期
     * @param retryCount   重连次数：默认3次，不需要设置为零
     */
    public static void init(Application app, OkHttpClient okHttpClient, CacheMode cacheMode,
                            long cacheTime, int retryCount) {

        if (retryCount == -1) retryCount = 3;

        OkGo.getInstance()
                .init(app)
                .setOkHttpClient(okHttpClient)
                .setCacheMode(cacheMode)//缓存策略
                .setCacheTime(cacheTime)//缓存时间
                .setRetryCount(retryCount);//重复请求次数
    }
}
