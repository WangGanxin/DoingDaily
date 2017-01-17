package com.ganxin.doingdaily.common.network.Interceptor;

import com.ganxin.doingdaily.application.DoingDailyApp;
import com.ganxin.doingdaily.common.constants.ConstantValues;
import com.ganxin.doingdaily.common.utils.NetworkUtil;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Description : Http缓存拦截器  <br/>
 * author : WangGanxin <br/>
 * date : 2016/12/28 <br/>
 * email : ganxinvip@163.com <br/>
 */
public class HttpCacheInterceptor implements Interceptor{
    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Request request = chain.request();

        //无网络状态下,手动拦截请求，使其使用本地缓存
        if (!NetworkUtil.isNetworkConnectivity(DoingDailyApp.getAppContext())) {
            request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
        }

        //拦截响应
        Response response = chain.proceed(request);
        if (NetworkUtil.isNetworkConnectivity(DoingDailyApp.getAppContext())) {
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
