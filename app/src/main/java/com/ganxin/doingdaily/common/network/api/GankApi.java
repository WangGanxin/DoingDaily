package com.ganxin.doingdaily.common.network.api;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Description : 干货集中营Api定义接口  <br/>
 * author : WangGanxin <br/>
 * date : 2017/07/11 <br/>
 * email : ganxinvip@163.com <br/>
 */
public interface GankApi {

    /**
     * 获取知乎指定日期文章列表
     *
     * @param pageIndex 第几页，大于0
     * @return
     */
    @GET("福利/10/{pageIndex}")
    Observable<String> getPictures(@Path("pageIndex") int pageIndex);
}
