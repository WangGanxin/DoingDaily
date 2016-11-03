package com.ganxin.doingdaily.test;

import com.ganxin.doingdaily.common.data.model.News;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import rx.Observable;

/**
 * Description : TODO  <br/>
 * author : WangGanxin <br/>
 * date : 2016/10/31 <br/>
 * email : ganxinvip@163.com <br/>
 */
public interface NewsService {
    @GET("/showapi_open_bus/channel_news/channel_news")
    Call<News> getNews(@Header("apikey") String apikey);

    @GET("/showapi_open_bus/channel_news/channel_news")
    Observable<News> getNews2(@Header("apikey") String apikey);
}
