package com.ganxin.doingdaily.common.network;

import com.ganxin.doingdaily.application.DoingDailyApp;
import com.ganxin.doingdaily.common.constants.ConstantValues;
import com.ganxin.doingdaily.common.utils.LogUtil;
import com.ganxin.doingdaily.common.utils.NetworkUtil;

import java.io.File;
import java.io.IOException;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Description : 网络访问控制类  <br/>
 * author : WangGanxin <br/>
 * date : 2016/11/1 <br/>
 * email : ganxinvip@163.com <br/>
 */
public class NetworkManager {
    private static Api API;

    private NetworkManager() {

    }

    public static Api getAPI() {
        if (API == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(createOkHttp(ConstantValues.HTTP_CACHE_ENABLE))
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .baseUrl(ConstantValues.BASE_URL)
                    .build();
            API = retrofit.create(Api.class);
        }
        return API;
    }

    private static OkHttpClient createOkHttp(boolean cacheable) {
        OkHttpClient okHttpClient;
        if (cacheable) {
            okHttpClient = new OkHttpClient.Builder()
                    .cache(getCache()) //设置缓存
                    .addInterceptor(new HttpCacheInterceptor()) //本地拦截缓存
                    .addInterceptor(getLogInterceptor()) //请求日志拦截
                    .addNetworkInterceptor(new HttpRequestInterceptor()) //统一请求header
                    .build();
        } else {
            okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(getLogInterceptor()) //请求日志拦截
                    .addNetworkInterceptor(new HttpRequestInterceptor()) //统一请求header
                    .build();
        }
        return okHttpClient;
    }

    private static HttpLoggingInterceptor getLogInterceptor() {
        HttpLoggingInterceptor.Logger logger = new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                LogUtil.i(message);
            }
        };
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(logger);
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return loggingInterceptor;
    }

    private static class HttpRequestInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request().newBuilder()
                    .addHeader(ConstantValues.REQUEST_HEADER, ConstantValues.API_KEY) //设置全局的Header
                    .build();
            return chain.proceed(request);
        }
    }

    private static Cache getCache() {
        File cacheFile = new File(DoingDailyApp.getInstance().getExternalCacheDir(), ConstantValues.HTTP_CACHE_DIR);
        Cache cache = new Cache(cacheFile, ConstantValues.HTTP_CACHE_MAXSIZE);
        return cache;
    }

    private static class HttpCacheInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();

            //无网络状态下,手动拦截请求，使其使用本地缓存
            if (!NetworkUtil.isNetworkConnectivity(DoingDailyApp.getInstance())) {
                request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
            }

            //拦截响应
            Response response = chain.proceed(request);
            if (NetworkUtil.isNetworkConnectivity(DoingDailyApp.getInstance())) {
                // 有网络时 默认缓存超时为0
                int maxAge = 0;
                response.newBuilder().header("Cache-Control", "public, max-age=" + maxAge).build();
            } else {
                // 无网络时，设置超时时间
                int maxStale = ConstantValues.HTTP_CACHE_TIME;
                response.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale).build();
            }

            return response;
        }
    }
}
