package com.zxm.liblocation;

/**
 * Created by ZhangXinmin on 2018/8/11.
 * Copyright (c) 2018 . All rights reserved.
 * AMap location function manager.
 */
public final class LocationManager {
    private static final String TAG = LocationManager.class.getSimpleName();


    private LocationManager() {
    }

    private static class Holder {
        private static LocationManager INSTANCE = new LocationManager();
    }

    public static LocationManager getInstance() {
        return Holder.INSTANCE;
    }


}
