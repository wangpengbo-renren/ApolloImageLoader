package com.apollo.renren.network.http;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.ArrayMap;

import com.apollo.renren.network.response.Callback;

/**
 * 该接口用于定义网络框架的基本协议，如Get、Post请求
 * 不管用什么第三方网络库，封装第三方库的类都要实现这个接口
 * 如使用Okhttp，则创建OkhttpManager，实现IHttpManager
 */
public interface IHttpManager {
    <T> void doGet(@NonNull String url, @Nullable ArrayMap<String,Object> params, @Nullable Callback<T> callback);

    <T> void doPost(@NonNull String url,@Nullable ArrayMap<String,Object> params,@Nullable Callback<T> callback);

    void cancelRequestByTag(Object tag);

    void cacelAllRequests();
}
