package com.ganxin.doingdaily.common.network.api;

import com.ganxin.doingdaily.common.data.model.PictureGankBean;

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
     * 获取随机图片列表
     *
     * @param pageIndex 第几页，大于0
     * @return
     */
    @GET("福利/15/{pageIndex}")
    Observable<PictureGankBean> getPictures(@Path("pageIndex") int pageIndex);

    /**
     * 获取随机休息视频列表
     *
     * @param pageIndex 第几页，大于0
     * @return
     */
    @GET("休息视频/15/{pageIndex}")
    Observable<String> getVideos(@Path("pageIndex") int pageIndex);
}
