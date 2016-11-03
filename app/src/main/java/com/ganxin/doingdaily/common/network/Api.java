package com.ganxin.doingdaily.common.network;

import com.ganxin.doingdaily.common.data.model.News;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Description : Api定义接口  <br/>
 * author : WangGanxin <br/>
 * date : 2016/10/31 <br/>
 * email : ganxinvip@163.com <br/>
 */
public interface Api {
    @GET("showapi_open_bus/channel_news/channel_news")
    Observable<News> getNews();
}
