package com.lzz.lzzlibrary.http;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Describe: 请求公共信息
 * <p>
 * Created by: LuoZhuangZhuang
 * Date on: 2021/1/18 16:55
 */
public class LzzHttpRequest {

    /**
     * 当前请求响应后才能继续下个请求
     * String:当前请求标识
     * Boolean：是否可请求
     */
    private static Map<String, Boolean> requestResponseMap = new HashMap<>();

    /**
     * 设置接口是否可请求
     *
     * @param url       完整地址
     * @param isRequest 当前地址是否可请求
     */
    protected static void setIsRequest(String url, boolean isRequest) {
        requestResponseMap.put(url, isRequest);
    }

    /**
     * 获取指定接口是否可请求
     *
     * @param url 完整地址
     * @return
     */
    private static boolean isRequest(String url) {
        boolean isRequest = true;
        if (requestResponseMap.containsKey(url)) {
            isRequest = requestResponseMap.get(url);
        } else {
            // 默认可请求
            requestResponseMap.put(url, true);
        }
        return isRequest;
    }

    //--------------------------------------------------基本请求 start-------------------------------------------------

    /**
     * 下载
     *
     * @param appUrl 下载地址
     */
    public static void download(String appUrl, FileCallback callback) {
        OkGo.<File>get(appUrl)
                //.tag(this)
                .execute(callback);
    }

    /**
     * json格式的请求方式
     *
     * @param url
     * @param json
     * @param callback
     */
    public static void postJson(String url, String json, LzzBaseCallBack callback) {
        if (isRequest(url)) {   // 防止同一个接口频繁请求，当前请求响应后才能继续请求
            setIsRequest(url, false);
            OkGo.<String>post(url)
                    .upJson(json)
                    .execute(callback);
        } else {
            callback.onFinish(url + "还未响应，相同接口无法再次请求！");
            if (SuperHttpManage.getApiCallbackServiceLoader() != null) {
                for (ApiCallbackService service : SuperHttpManage.getApiCallbackServiceLoader()) {
                    service.onFinish(url + "还未响应，相同接口无法再次请求！");
                }
            }
        }
    }

    /**
     * 上传file文件的请求方式
     *
     * @param url
     * @param callback
     */
    public static void file(String url, File file, LzzBaseCallBack callback) {
        if (isRequest(url)) {   // 防止同一个接口频繁请求，当前请求响应后才能继续请求
            setIsRequest(url, false);
            OkGo.<String>post(url)
                    .params("file", file)
                    .execute(callback);
        } else {
            callback.onFinish(url + "还未响应，相同接口无法再次请求！");
            if (SuperHttpManage.getApiCallbackServiceLoader() != null) {
                for (ApiCallbackService service : SuperHttpManage.getApiCallbackServiceLoader()) {
                    service.onFinish(url + "还未响应，相同接口无法再次请求！");
                }
            }
        }
    }

    /**
     * GET请求方式
     *
     * @param url
     * @param params
     * @param callback
     */
    public static void get(String url, Map<String, Object> params, LzzBaseCallBack callback) {
        if (isRequest(url)) {   // 防止同一个接口频繁请求，当前其你去响应后才能继续请求
            setIsRequest(url, true);
            Map<String, String> param = new LinkedHashMap<>();
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                if (key != null && value != null) {
                    param.put(entry.getKey(), entry.getValue().toString());
                }
            }
            OkGo.<String>get(url)
                    .params(param)
                    .execute(callback);
        } else {
            callback.onFinish(url + "还未响应，相同接口无法再次请求！");
            if (SuperHttpManage.getApiCallbackServiceLoader() != null) {
                for (ApiCallbackService service : SuperHttpManage.getApiCallbackServiceLoader()) {
                    service.onFinish(url + "还未响应，相同接口无法再次请求！");
                }
            }
        }
    }

    /**
     * POST请求方式
     *
     * @param url
     * @param params
     * @param callback
     */
    public static void post(String url, Map<String, Object> params, LzzBaseCallBack callback) {
        if (isRequest(url)) {   // 防止同一个接口频繁请求，当前其你去响应后才能继续请求
            setIsRequest(url, true);
            Map<String, String> param = new LinkedHashMap<>();
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                if (key != null && value != null) {
                    param.put(entry.getKey(), entry.getValue().toString());
                }
            }
            OkGo.<String>post(url)
                    // .tag(requestTag(url))
                    .params(param)
                    .execute(callback);
        } else {
            callback.onFinish(url + "还未响应，相同接口无法再次请求！");
            if (SuperHttpManage.getApiCallbackServiceLoader() != null) {
                for (ApiCallbackService service : SuperHttpManage.getApiCallbackServiceLoader()) {
                    service.onFinish(url + "还未响应，相同接口无法再次请求！");
                }
            }
        }
    }


    /**
     * PUT请求方式
     *
     * @param url
     * @param json
     * @param callback
     */
    public static void put(String url, String json, LzzBaseCallBack callback) {

        if (isRequest(url)) {   // 防止同一个接口频繁请求，当前请求响应后才能继续请求
            setIsRequest(url, false);
            OkGo.<String>put(url)
                    .upJson(json)
                    .execute(callback);
        } else {
            callback.onFinish(url + "还未响应，相同接口无法再次请求！");
            if (SuperHttpManage.getApiCallbackServiceLoader() != null) {
                for (ApiCallbackService service : SuperHttpManage.getApiCallbackServiceLoader()) {
                    service.onFinish(url + "还未响应，相同接口无法再次请求！");
                }
            }
        }
    }

    /**
     * DElETE请求方式
     *
     * @param url
     * @param params
     * @param callback
     */
    public static void delete(String url, Map<String, Object> params, LzzBaseCallBack callback) {
        if (isRequest(url)) {   // 防止同一个接口频繁请求，当前其你去响应后才能继续请求
            setIsRequest(url, true);
            Map<String, String> param = new LinkedHashMap<>();
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                if (key != null && value != null) {
                    param.put(entry.getKey(), entry.getValue().toString());
                }
            }
            OkGo.<String>delete(url)
                    // .tag(requestTag(url))
                    .params(param)
                    .execute(callback);
        } else {
            callback.onFinish(url + "还未响应，相同接口无法再次请求！");
            if (SuperHttpManage.getApiCallbackServiceLoader() != null) {
                for (ApiCallbackService service : SuperHttpManage.getApiCallbackServiceLoader()) {
                    service.onFinish(url + "还未响应，相同接口无法再次请求！");
                }
            }
        }
    }

}
