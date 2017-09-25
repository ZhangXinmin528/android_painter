package com.example.android_painter.http.callback;

import com.example.android_painter.http.response.LazyResponse;
import com.example.android_painter.http.response.SimpleResponse;
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
 * 复合型数据
 */

public abstract class MultiJsonCallback<T> extends AbsCallback<T> {
    @Override
    public T convertResponse(Response response) throws Throwable {
        Type genType = getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        //这里得到第二层泛型的所有类型 LazyResponse<K>
        Type type = params[0];

        if (!(type instanceof ParameterizedType)) throw new IllegalStateException("没有填写泛型参数");
        //这里得到真实数据类型：LazyResponse
        Type rawType = ((ParameterizedType) type).getRawType();
        //这里得到第二层数据的泛型的数据类型：K
        Type typeArgument = ((ParameterizedType) type).getActualTypeArguments()[0];

        ResponseBody body = response.body();
        if (body == null) return null;

        Gson gson = new Gson();
        JsonReader reader = new JsonReader(body.charStream());
        if (rawType != LazyResponse.class) {//简单数据
            T data = gson.fromJson(reader, type);
            response.close();
            return data;
        } else {//复合型数据
            if (typeArgument == Void.class) {//LazyResponse<Void>
                SimpleResponse simpleResponse = gson.fromJson(reader, SimpleResponse.class);
                response.close();
                return (T) simpleResponse.toLazyResponse();
            } else {//LazyResponse<K>
                LazyResponse lazyResponse = gson.fromJson(reader, type);
                response.close();
                int code = lazyResponse.code;

                //服务器返回状态：
                if (code == 0) {
                    return (T) lazyResponse;
                } else if (code == 104) {
                    throw new IllegalStateException("用户授权信息无效");
                } else if (code == 105) {
                    throw new IllegalStateException("用户收取信息已过期");
                } else if (code == 106) {
                    throw new IllegalStateException("用户账户被禁用");
                } else if (code == 300) {
                    throw new IllegalStateException("其他乱七八糟错误");
                } else {
                    throw new IllegalStateException("错误代码：" + code
                            + "..错误信息：" + lazyResponse.msg);
                }
            }
        }
    }
}
