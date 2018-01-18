package com.example.android_painter.util;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Administrator on 2018/1/17.
 * Copyright (c) 2018 . All rights reserved.
 * 新闻列表
 */
public final class LogUtil {

    private static final String TAG = "painter";
    private static final SimpleDateFormat DEFAULT_FORMAT =
            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.getDefault());

    /**
     * use default tag to print log；i
     *
     * @param message
     */
    public static void logIWithTime(String message) {
        if (!TextUtils.isEmpty(message)) {
            logI(TAG, getCurrentTime() + "==>" + message);
        }
    }

    /**
     * use default tag to print log:i
     *
     * @param message
     */
    public static void logI(String message) {
        if (!TextUtils.isEmpty(message)) {
            logI(TAG, message);
        }
    }

    /**
     * log:i
     *
     * @param tag     tag
     * @param message message
     */
    public static void logI(String tag, String message) {
        Log.i(tag, message);
    }

    /**
     * use default tag to print log:e
     *
     * @param message
     */
    public static void logEWithTime(String message) {
        if (!TextUtils.isEmpty(message)) {
            logE(TAG, getCurrentTime() + "==>" + message);
        }
    }

    /**
     * use default tag to print log:e
     *
     * @param message
     */
    public static void logE(String message) {
        if (!TextUtils.isEmpty(message)) {
            logE(TAG, message);
        }
    }

    /**
     * log:e
     *
     * @param tag     tag
     * @param message message
     */
    public static void logE(String tag, String message) {
        Log.e(tag, message);
    }

    /**
     * 获取当前时间字符串
     * <p>
     * 格式：yyyy-MM-dd HH:mm:ss
     * </P>
     *
     * @return
     */
    public static String getCurrentTime() {
        return getSpecialStringTime(System.currentTimeMillis(), DEFAULT_FORMAT);
    }

    /**
     * 获取指定格式的时间字符串
     *
     * @param millTime
     * @param format
     * @return
     */
    private static String getSpecialStringTime(long millTime, @NonNull SimpleDateFormat format) {
        return format.format(new Date(millTime));
    }
}
