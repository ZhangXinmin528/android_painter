package com.zxm.android_painter.util;

import android.content.Context;

/**
 * Created by ZhangXinmin on 2018/8/16.
 * Copyright (c) 2018 . All rights reserved.
 * Util class for setting.
 */
public final class SettingUtil {

    public static final String WEATHER_SHARE_TYPE = "weather_share_type";//天气分享形式
    public static final String CLEAR_CACHE = "clean_cache";//清空缓存
    public static final String CONFIG_THEME_COLOR = "theme_color";//主题色

    /**
     * set theme color for app
     *
     * @param themeIndex
     */
    public static void setThemeColor(Context context, int themeIndex) {
        SharedPreferencesUtil.put(context, CONFIG_THEME_COLOR, themeIndex);
    }

    /**
     * get theme color for app
     *
     * @return theme color
     */
    public static int getThemeColor(Context context) {
        return (int) SharedPreferencesUtil.get(context, CONFIG_THEME_COLOR, 0);
    }
}
