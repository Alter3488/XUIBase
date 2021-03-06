package com.lzz.lzzlibrary.http;

import android.text.TextUtils;

import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.convert.StringConvert;
import com.lzy.okgo.request.base.Request;

import okhttp3.Response;

/**
 * Describe:
 * <p>
 * Created by: LuoZhuangZhuang
 * Date on: 2021/1/18 16:54
 */
public abstract class LzzBaseCallBack extends AbsCallback<String> {

    private StringConvert convert;
    private String TAG; //  网络请求TAG（用于日志过滤）-- 后缀地址
    private String baseUrl; // 请求全地址路径
    private String endUrl = "";  // 请求后缀地址

    public LzzBaseCallBack() {
        convert = new StringConvert();
    }

    public LzzBaseCallBack(String requestName) {
        convert = new StringConvert();
        this.TAG = requestName;
    }

    @Override
    public String convertResponse(Response response) throws Throwable {
        String s = convert.convertResponse(response);
        response.close();
        return s;
    }

    @Override
    public void onStart(Request<String, ? extends Request> request) {
        super.onStart(request);
        try {
            baseUrl = request.getBaseUrl();
            String url = baseUrl.replace("//", "");
            int indexOf = url.indexOf("/");
            endUrl = url.substring(indexOf, url.length());

            if (TextUtils.isEmpty(TAG)) {
                TAG = endUrl;
            }
        }catch (Exception e) {
            TAG = "网络请求";
        }

        if (SuperHttpManage.getApiCallbackServiceLoader() != null) {
            for (ApiCallbackService service : SuperHttpManage.getApiCallbackServiceLoader()) {
                service.onStart(baseUrl, endUrl, request);
            }
        }
    }

    @Override
    public void onError(com.lzy.okgo.model.Response<String> response) {
        super.onError(response);
        if (SuperHttpManage.getApiCallbackServiceLoader() != null) {
            for (ApiCallbackService service : SuperHttpManage.getApiCallbackServiceLoader()) {
                service.onError(baseUrl, endUrl, response);
            }
        }
    }

    @Override
    public void onSuccess(com.lzy.okgo.model.Response<String> response) {
        if (SuperHttpManage.getApiCallbackServiceLoader() != null) {
            for (ApiCallbackService service : SuperHttpManage.getApiCallbackServiceLoader()) {
                service.onSuccess(baseUrl, endUrl, response);
            }
        }
    }

    @Override
    public void onFinish() {
        super.onFinish();
        // 设置可请求
        LzzHttpRequest.setIsRequest(baseUrl, true);
        onFinish("请求完成");

        if (SuperHttpManage.getApiCallbackServiceLoader() != null) {
            for (ApiCallbackService service : SuperHttpManage.getApiCallbackServiceLoader()) {
                service.onFinish("请求完成");
            }
        }
    }

    /**
     * 对返回数据进行操作的回调， UI线程
     */
    public abstract void onFinish(String msg);

    /**
     * 获取当前请求TAG
     *
     * @return
     */
    public String getTAG() {
        return TAG;
    }

    /**
     * 获取当前完整请求地址
     *
     * @return
     */
    public String getBaseUrl() {
        return baseUrl;
    }

    /**
     * 获取请求后缀地址
     *
     * @return
     */
    public String getEndUrl() {
        return endUrl;
    }


}
