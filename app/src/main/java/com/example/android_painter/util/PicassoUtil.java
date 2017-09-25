package com.example.android_painter.util;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by ZhangXinmin on 2017/9/17.
 * Copyright (c) 2017 . All rights reserved.
 * Picasso工具类
 */

public class PicassoUtil {
    private PicassoUtil() {
        throw new UnsupportedOperationException("You must not do this!");
    }

    /**
     * @param context
     * @param resId
     * @param target
     */
    public static void setImageView(@NonNull Context context, @DrawableRes int resId,
                                    @NonNull ImageView target) {
        Picasso.with(context)
                .load(resId)
                .into(target);
    }

}
