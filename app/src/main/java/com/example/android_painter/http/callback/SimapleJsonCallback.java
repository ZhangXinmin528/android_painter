package com.example.android_painter.http.callback;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.lzy.okgo.callback.AbsCallback;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by ZhangXinmin on 2017/9/20.
 * Copyright (c) 2017 . All rights reserved.
 * 单一型数据
 */

public abstract class SimapleJsonCallback<T> extends AbsCallback<T> {

    private Type type;

    public SimapleJsonCallback() {
    }


    public SimapleJsonCallback(@NonNull Type type) {
        this.type = type;
    }

    @Override
    public T convertResponse(Response response) throws Throwable {
        ResponseBody body = response.body();
        if (body == null) return null;

        T data = null;
        Gson gson = new Gson();
        JsonReader jsonReader = new JsonReader(body.charStream());

        if (type == null) {
            Type genType = getClass().getGenericSuperclass();
            type = ((ParameterizedType) genType).getActualTypeArguments()[0];
        }
        if (type != null) {
            data = gson.fromJson(jsonReader, type);
        }
        return data;
    }
}
