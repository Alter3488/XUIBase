package com.lzz.lzzlibrary.http;

import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

/**
 * Describe:
 * <p>
 * Created by: LuoZhuangZhuang
 * Date on: 2021/1/18 16:47
 */
public interface ApiCallbackService<T> {

    /**
     * 网络请求开始前，UI线程
     */
    void onStart(String baserUrl, String endUrl, Request<T, ? extends Request> request);

    /**
     * 网络请求结束后，UI线程
     */
    void onFinish(String msg);

    /**
     * 网络请求失败，响应错误，数据解析错误等，UI线程
     */
    void onError(String baseUrl, String endUrl, Response<T> response);

    /**
     * 网络请求成功，对返回数据进行操作，UI线程
     */
    void onSuccess(String baseUrl, String endUrl, Response<T> response);

}
