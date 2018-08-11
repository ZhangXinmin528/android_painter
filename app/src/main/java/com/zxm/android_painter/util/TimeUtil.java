package com.zxm.android_painter.util;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.security.PublicKey;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ZhangXinmin on 2017/9/24.
 * Copyright (c) 2017 . All rights reserved.
 */

public final class TimeUtil {
    public static final SimpleDateFormat DEFAULT_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final SimpleDateFormat DAY_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    private TimeUtil() {
        throw new UnsupportedOperationException("You must not do this!");
    }

    /**
     * 获取指定格式的时间戳
     * @param time
     * @param format
     * @return
     */
    public static String getSpecialTime(@NonNull String time, @NonNull DateFormat format) {
        String result = "";
        if (!TextUtils.isEmpty(time)) {
            result = getTime(time, format);
        }
        return result;
    }

    /**
     * 获取今天日期，格式：yyyy-MM-dd
     *
     * @return
     */
    public static String getCurrDate() {
        return getTime(System.currentTimeMillis(), DAY_FORMAT);
    }

    /**
     * 获取当前时间，格式：yyyy-MM-dd HH:mm:ss
     *
     * @return
     */
    public static String getCurrTime() {
        return getTime(System.currentTimeMillis(), DEFAULT_FORMAT);
    }

    /**
     * 获取指定格式的时间戳
     *
     * @param time
     * @param format
     * @return
     */
    public static String getTime(@NonNull String time, @NonNull DateFormat format) {
        return format.format(time);
    }

    /**
     * 获取指定格式的时间戳
     *
     * @param time
     * @param format
     * @return
     */
    public static String getTime(@NonNull long time, @NonNull DateFormat format) {
        return format.format(new Date(time));
    }
}
