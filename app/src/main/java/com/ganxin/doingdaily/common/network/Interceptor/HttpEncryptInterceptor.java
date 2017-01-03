package com.ganxin.doingdaily.common.network.Interceptor;

import android.text.TextUtils;

import com.ganxin.doingdaily.common.constants.ConstantValues;
import com.ganxin.doingdaily.common.utils.DateUtils;
import com.ganxin.doingdaily.common.utils.MD5Util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Description : Http请求加密拦截器  <br/>
 * author : WangGanxin <br/>
 * date : 2016/12/28 <br/>
 * email : ganxinvip@163.com <br/>
 */
public class HttpEncryptInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        if (request.method().equals("GET")) {
            request = addGetParams(request);
        } else if (request.method().equals("POST")) {
            request = addPostParams(request);
        }
        return chain.proceed(request);
    }

    //get请求 添加公共参数 签名
    private static Request addGetParams(Request request) {
        //添加公共参数
        HttpUrl httpUrl = request.url()
                .newBuilder()
                .addQueryParameter("showapi_appid", ConstantValues.APP_ID)
                .addQueryParameter("showapi_timestamp", DateUtils.getCurrentTimeFormat("yyyyMMddHHmmss"))
                .addQueryParameter("showapi_res_gzip","1")
                .build();

        //添加签名
        Set<String> nameSet = httpUrl.queryParameterNames();
        List<String> nameList = new ArrayList<>();
        nameList.addAll(nameSet);
        Collections.sort(nameList);

        StringBuilder requestBuilder = new StringBuilder();
        StringBuilder signBuilder = new StringBuilder();

        for (int i = 0; i < nameList.size(); i++) {
            requestBuilder.append("&")
                    .append(nameList.get(i))
                    .append("=")
                    .append(httpUrl.queryParameterValues(nameList.get(i)) != null &&
                            httpUrl.queryParameterValues(nameList.get(i)).size() > 0 ? httpUrl.queryParameterValues(nameList.get(i)).get(0) : "");

            signBuilder.append(nameList.get(i));
            if (nameList.get(i) != null && httpUrl.queryParameterValues(nameList.get(i)).size() > 0) {
                signBuilder.append(httpUrl.queryParameterValues(nameList.get(i)).get(0));
            }
        }
        httpUrl = httpUrl.newBuilder()
                .addQueryParameter("showapi_sign", MD5Util.md5_32(signBuilder.toString() + ConstantValues.APP_SECRET))
                .build();
        request = request.newBuilder().url(httpUrl).build();
        return request;
    }

    //post 添加签名和公共参数
    private Request addPostParams(Request request) throws UnsupportedEncodingException {
        if (request.body() instanceof FormBody) {
            FormBody.Builder bodyBuilder = new FormBody.Builder();
            FormBody formBody = (FormBody) request.body();

            //把原来的参数添加到新的构造器
            for (int i = 0; i < formBody.size(); i++) {
                bodyBuilder.addEncoded(formBody.encodedName(i), formBody.encodedValue(i));
            }

            //添加公共参数
            formBody = bodyBuilder
                    .addEncoded("showapi_appid", ConstantValues.APP_ID)
                    .addEncoded("showapi_timestamp", DateUtils.getCurrentTimeFormat("yyyyMMddHHmmss"))
                    .addEncoded("showapi_res_gzip", "1")
                    .build();

            Map<String, String> bodyMap = new HashMap<>();
            List<String> nameList = new ArrayList<>();

            for (int i = 0; i < formBody.size(); i++) {
                nameList.add(formBody.encodedName(i));
                bodyMap.put(formBody.encodedName(i), URLDecoder.decode(formBody.encodedValue(i), "UTF-8"));
            }

            Collections.sort(nameList);

            StringBuilder requestBuilder = new StringBuilder();
            StringBuilder signBuilder = new StringBuilder();


            for (int i = 0; i < nameList.size(); i++) {

                String value = URLDecoder.decode(bodyMap.get(nameList.get(i)), "UTF-8");

                requestBuilder.append("&")
                        .append(nameList.get(i))
                        .append("=")
                        .append(value);

                if (nameList.get(i) != null && !TextUtils.isEmpty(value)) {
                    signBuilder.append(nameList.get(i))
                            .append(value);
                }
            }

            formBody = bodyBuilder.
                    addEncoded("showapi_sign", MD5Util.md5_32(signBuilder.toString() + ConstantValues.APP_SECRET))
                    .build();
            request = request.newBuilder().post(formBody).build();
        }
        return request;
    }

}
