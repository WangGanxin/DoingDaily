package com.ganxin.doingdaily.common.network.api;

import com.ganxin.doingdaily.common.data.model.ZhihuArticleBean;
import com.ganxin.doingdaily.common.data.model.ZhihuBeforeNewsBean;
import com.ganxin.doingdaily.common.data.model.ZhihuLatestNewsBean;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Description : 知乎Api定义接口  <br/>
 * author : WangGanxin <br/>
 * date : 2017/07/11 <br/>
 * email : ganxinvip@163.com <br/>
 */
public interface ZhihuApi {

    /**
     * 获取知乎最新文章列表
     *
     * @return
     */
    @GET("news/latest")
    Observable<ZhihuLatestNewsBean> getZhihuLatestNews();

    /**
     * 获取知乎指定日期文章列表
     *
     * @param date 日期
     * @return
     */
    @GET("news/before/{date}")
    Observable<ZhihuBeforeNewsBean> getZhihuBeforeNews(@Path("date") String date);

    /**
     * 获取知乎指定日期文章列表
     *
     * @param articleId 文章Id
     * @return
     */
    @GET("news/{articleId}")
    Observable<ZhihuArticleBean> getZhihuArticle(@Path("articleId") String articleId);
}
