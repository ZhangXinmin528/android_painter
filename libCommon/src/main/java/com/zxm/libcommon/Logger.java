package com.zxm.libcommon;

import android.support.annotation.NonNull;
import android.util.Log;

/**
 * Created by Administrator on 2018/1/17.
 * Copyright (c) 2018 . All rights reserved.
 */
public final class Logger {

    private Logger() {
    }

    /**
     * log.d
     *
     * @param tag
     * @param msg
     */
    public static void d(@NonNull String tag, @NonNull String msg) {
        if (BuildConfig.LOG_ENABLE) {
            Log.d(tag, msg);
        }
    }

    /**
     * log.i
     *
     * @param tag
     * @param msg
     */
    public static void i(@NonNull String tag, @NonNull String msg) {
        if (BuildConfig.LOG_ENABLE) {
            Log.i(tag, msg);
        }
    }

    /**
     * log.e
     *
     * @param tag
     * @param msg
     */
    public static void e(@NonNull String tag, @NonNull String msg) {
        if (BuildConfig.LOG_ENABLE) {
            Log.e(tag, msg);
        }
    }
}
