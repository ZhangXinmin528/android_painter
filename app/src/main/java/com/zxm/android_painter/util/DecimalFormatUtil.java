package com.zxm.android_painter.util;

import android.text.TextUtils;

import java.text.DecimalFormat;

/**
 * Created by ZhangXinmin on 2017/3/28.
 * Copyright (c) 2017 . All rights reserved.
 */

public class DecimalFormatUtil {

    public static final String STYLE_1 = "0.0";
    public static final String STYLE_2 = "0.00";
    public static final String STYLE_3 = "0.000";

    /**
     * 数字格式转化
     *
     * @param style 数字格式：如“0.0”
     * @param value 需转化数据
     * @return String
     */
    public static String formatNummber(float value, String style) {
        DecimalFormat decimalFormat = new DecimalFormat();
        String newValue = "";
        if (!TextUtils.isEmpty(style)) {
            decimalFormat.applyPattern(style);
            newValue = decimalFormat.format(value);
        } else {
            newValue = value + "";
        }

        return newValue;
    }

    /**
     * 转化成交量数据
     *
     * @param vol
     * @param style
     * @return
     */
    public static String formatVol(float vol, String style) {
        DecimalFormat decimalFormat = new DecimalFormat();
        String newValue = "";
        if (!TextUtils.isEmpty(style)) {
            decimalFormat.applyPattern(style);
            newValue = decimalFormat.format(vol / 1000.0f);
        } else {
            newValue = vol + "";
        }

        return newValue;
    }

}
