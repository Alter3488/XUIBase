package com.lzz.lzzlibrary.http;

import android.app.Application;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.cookie.CookieJarImpl;
import com.lzy.okgo.cookie.store.SPCookieStore;
import com.lzy.okgo.model.HttpHeaders;

import java.lang.reflect.Field;
import java.util.ServiceLoader;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Describe:
 * <p>
 * Created by: LuoZhuangZhuang
 * Date on: 2021/1/18 16:55
 */
public class SuperHttpManage {
    private static Application application;
    private static boolean Debug;

    public static void init(Application application) {
        SuperHttpManage.application = application;
        okGoInit();
    }

    public static Application getApplication() {
        return application;
    }

    public static boolean isDebug() {
        return Debug;
    }

    public static void setDebug(boolean debug) {
        Debug = debug;
    }

    private static void okGoInit(){
        try {
            Class c = Class.forName("java.lang.Daemons");
            Field maxField = c.getDeclaredField("MAX_FINALIZE_NANOS");
            maxField.setAccessible(true);
            maxField.set(null, Long.MAX_VALUE);
        }catch (Exception e) {

        }

        OkHttpClient.Builder builderOkHttpClient = new OkHttpClient.Builder();
        builderOkHttpClient.readTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);      // 全局的读取超出时间
        builderOkHttpClient.writeTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);     // 全局的写入超出时间
        builderOkHttpClient.connectTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);   // 全局的连接超出时间
        builderOkHttpClient.cookieJar(new CookieJarImpl(new SPCookieStore(getApplication())));

        OkHttpClient build = builderOkHttpClient.build();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.put("charset", "utf-8");
        OkGo.getInstance().init(getApplication())
                // 可以全局统一设置缓存模式，默认是不使用缓存，可以不传，具体其他模式看GitHub介绍 https://github.com/jeasonlzy/
                .setCacheMode(CacheMode.NO_CACHE)
                // 可以全局统一设置缓存时间，默认永不过期，具体使用方法看GitHub介绍
                .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)
                .setOkHttpClient(build)
                .addCommonHeaders(httpHeaders)
                .setRetryCount(3);
    }

    /**
     * 设置所有接口响应回调
     *
     * @param serviceLoader
     */
    private static ServiceLoader<ApiCallbackService> serviceLoader;

    public static ServiceLoader<ApiCallbackService> getApiCallbackServiceLoader() {
        if (serviceLoader == null) {
            serviceLoader = ServiceLoader.load(ApiCallbackService.class);
        }
        return serviceLoader;
    }

}
