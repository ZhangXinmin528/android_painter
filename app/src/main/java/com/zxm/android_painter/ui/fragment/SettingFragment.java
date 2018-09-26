package com.zxm.android_painter.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;

import com.zxm.android_painter.R;
import com.zxm.android_painter.util.SettingUtil;

/**
 * Created by ZhangXinmin on 2018/8/15.
 * Copyright (c) 2018 . All rights reserved.
 */
public class SettingFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener {

    private Context mContext;

    private ListPreference weatherShareType;
    private Preference cleanCache;
    private Preference themeColor;

    public static SettingFragment newInstance() {
        return new SettingFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.setting);

        initParamsAndViews();
    }

    private void initParamsAndViews() {

        //天气
        weatherShareType = (ListPreference) findPreference(SettingUtil.WEATHER_SHARE_TYPE);
        weatherShareType.setSummary(weatherShareType.getValue());
        weatherShareType.setOnPreferenceChangeListener(this);

        //清除缓存
        cleanCache = findPreference(SettingUtil.CLEAR_CACHE);
        cleanCache.setOnPreferenceChangeListener(this);

        //主题色
        themeColor = findPreference(SettingUtil.CONFIG_THEME_COLOR);
        String[] themeColors = getActivity().getResources().getStringArray(R.array.arrays_theme_color_title);
        int currentThemeIndex = SettingUtil.getThemeColor(mContext);
        if (currentThemeIndex >= themeColors.length) {
            themeColor.setSummary("自定义色");
        } else {
            themeColor.setSummary(themeColors[currentThemeIndex]);
        }
        themeColor.setOnPreferenceChangeListener(this);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {

        return false;
    }
}
