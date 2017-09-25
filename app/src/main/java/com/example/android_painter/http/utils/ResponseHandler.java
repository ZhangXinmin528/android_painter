package com.example.android_painter.http.utils;

import android.graphics.Bitmap;

import com.lzy.okgo.model.Response;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;

import okhttp3.Call;
import okhttp3.Headers;

/**
 * Created by ZhangXinmin on 2017/9/18.
 * Copyright (c) 2017 . All rights reserved.
 * 网络请求处理类
 */

public final class ResponseHandler {

    /**
     * 处理请求响应
     *
     * @param data
     * @param <T>
     */
    public static <T> void handleResponse(T data) {
        Response<T> response = new Response<>();
        response.setBody(data);
        handleResponse(response);
    }

    /**
     * 处理请求响应
     *
     * @param response
     * @param <T>
     */
    public static <T> void handleResponse(Response<T> response) {
        StringBuilder sb;
        Call call = response.getRawCall();
        if (call != null) {
            Logger.e("请求成功  请求方式：" + call.request().method() + "\n" + "url：" + call.request().url());

            Headers requestHeadersString = call.request().headers();
            Set<String> requestNames = requestHeadersString.names();
            sb = new StringBuilder();
            for (String name : requestNames) {
                sb.append(name).append(" ： ").append(requestHeadersString.get(name)).append("\n");
            }
            Logger.e(sb.toString());
        }
        T body = response.body();
        if (body != null) {
            if (body instanceof String) {
                Logger.e((String) body);
            } else if (body instanceof List) {
                sb = new StringBuilder();
                List list = (List) body;
                for (Object obj : list) {
                    sb.append(obj.toString()).append("\n");
                }
                Logger.e(sb.toString());
            } else if (body instanceof Set) {
                sb = new StringBuilder();
                Set set = (Set) body;
                for (Object obj : set) {
                    sb.append(obj.toString()).append("\n");
                }
                Logger.e(sb.toString());
            } else if (body instanceof Map) {
                sb = new StringBuilder();
                Map map = (Map) body;
                Set keySet = map.keySet();
                for (Object key : keySet) {
                    sb.append(key.toString()).append(" ： ").append(map.get(key)).append("\n");
                }
                Logger.e(sb.toString());
            } else if (body instanceof File) {
                File file = (File) body;
                Logger.e("数据内容即为文件内容\n下载文件路径：" + file.getAbsolutePath());
            } else if (body instanceof Bitmap) {
                Logger.e("图片的内容即为数据");
            } else {
                Logger.e(Convert.formatJson(body));
            }
        }

        okhttp3.Response rawResponse = response.getRawResponse();
        if (rawResponse != null) {
            Headers responseHeadersString = rawResponse.headers();
            Set<String> names = responseHeadersString.names();
            sb = new StringBuilder();
            sb.append("url ： ").append(rawResponse.request().url()).append("\n\n");
            sb.append("stateCode ： ").append(rawResponse.code()).append("\n");
            for (String name : names) {
                sb.append(name).append(" ： ").append(responseHeadersString.get(name)).append("\n");
            }
            Logger.e(sb.toString());
        } else {
            Logger.e("--");
        }
    }

    protected <T> void handleError() {
        Response<T> response = new Response<>();
        handleResponse(response);
    }

    protected <T> void handleError(Response<T> response) {
        if (response == null) return;
        if (response.getException() != null) response.getException().printStackTrace();
        StringBuilder sb;
        Call call = response.getRawCall();
        if (call != null) {
//            requestState.setText("请求失败  请求方式：" + call.request().method() + "\n" + "url：" + call.request().url());

            Headers requestHeadersString = call.request().headers();
            Set<String> requestNames = requestHeadersString.names();
            sb = new StringBuilder();
            for (String name : requestNames) {
                sb.append(name).append(" ： ").append(requestHeadersString.get(name)).append("\n");
            }
//            requestHeaders.setText(sb.toString());
        } else {
//            requestState.setText("--");
//            requestHeaders.setText("--");
        }

//        responseData.setText("--");
        okhttp3.Response rawResponse = response.getRawResponse();
        if (rawResponse != null) {
            Headers responseHeadersString = rawResponse.headers();
            Set<String> names = responseHeadersString.names();
            sb = new StringBuilder();
            sb.append("stateCode ： ").append(rawResponse.code()).append("\n");
            for (String name : names) {
                sb.append(name).append(" ： ").append(responseHeadersString.get(name)).append("\n");
            }
//            responseHeader.setText(sb.toString());
        } else {
//            responseHeader.setText("--");
        }
    }

}
