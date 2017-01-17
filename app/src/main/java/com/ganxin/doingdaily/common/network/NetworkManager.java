package com.ganxin.doingdaily.common.network;

import com.ganxin.doingdaily.application.DoingDailyApp;
import com.ganxin.doingdaily.common.constants.ConstantValues;
import com.ganxin.doingdaily.common.network.Interceptor.HttpCacheInterceptor;
import com.ganxin.doingdaily.common.network.Interceptor.HttpEncryptInterceptor;
import com.ganxin.doingdaily.common.utils.LogUtil;

import java.io.File;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
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
                    .cache(createCache()) //设置缓存
                    .addInterceptor(new HttpEncryptInterceptor()) //统一加密请求
                    .addInterceptor(new HttpCacheInterceptor()) //本地拦截缓存
                    .addInterceptor(createLogInterceptor()) //请求日志拦截
                    .build();
        } else {
            okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(new HttpEncryptInterceptor()) //统一加密请求
                    .addInterceptor(createLogInterceptor()) //请求日志拦截
                    .build();
        }
        return okHttpClient;
    }

    private static Cache createCache() {
        File cacheFile = new File(DoingDailyApp.getAppContext().getExternalCacheDir(), ConstantValues.HTTP_CACHE_DIR);
        Cache cache = new Cache(cacheFile, ConstantValues.HTTP_CACHE_MAXSIZE);
        return cache;
    }

    private static HttpLoggingInterceptor createLogInterceptor() {
        HttpLoggingInterceptor.Logger logger = new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                LogUtil.logI("http",message);
            }
        };
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(logger);
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return loggingInterceptor;
    }
}
