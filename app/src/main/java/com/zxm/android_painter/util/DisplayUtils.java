package com.zxm.android_painter.util;

import android.support.annotation.NonNull;
import android.text.TextUtils;

/**
 * Created by Administrator on 2018/1/17.
 * Copyright (c) 2018 . All rights reserved.
 */

public final class DisplayUtils {
    private DisplayUtils() {
    }

    /**
     * 非空
     *
     * @param value
     * @return
     */
    public static String isEmpty(String value) {
        if (TextUtils.isEmpty(value)) {
            return "--";
        } else {
            return value;
        }
    }

    /**
     * 截取新闻中的时间戳
     *
     * @param time
     * @return
     */
    public static String getNewsString(@NonNull String time) {
        String result = "";
        if (!TextUtils.isEmpty(time)) {
            result = time.split("T")[0];
        }
        return result;
    }
}
