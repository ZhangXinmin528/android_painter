package com.zxm.liblocation;

import android.content.Context;

/**
 * Created by ZhangXinmin on 2018/8/11.
 * Copyright (c) 2018 . All rights reserved.
 * AMap location function manager.
 */
public final class LocationManager {
    private static final String TAG = LocationManager.class.getSimpleName();

    private Context mContext;

    private static LocationManager INSTANCE;
    private LocationProvider mLocationProvider;

    private LocationManager(Context context) {
        mContext = context;
    }

    public synchronized static LocationManager getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new LocationManager(context);
        }
        return INSTANCE;
    }

    /**
     * start location
     */
    public void startLocation() {
        if (mLocationProvider == null) {
            mLocationProvider = new LocationProvider(mContext);
        }

        mLocationProvider._startLocation();
    }

    /**
     * stop location
     */
    public void stopLocation() {
        if (mLocationProvider != null) {
            mLocationProvider._stopLocation();
            mLocationProvider = null;
        }
    }

    /**
     * if use it in activity,it will destory AMapLocationClient.
     */
    public void destoryLocation() {
        if (mLocationProvider != null) {
            mLocationProvider._destroyLocation();
            mLocationProvider = null;
        }
    }

}
