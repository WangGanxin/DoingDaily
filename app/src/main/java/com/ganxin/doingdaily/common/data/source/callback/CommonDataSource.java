package com.ganxin.doingdaily.common.data.source.callback;

import android.support.annotation.NonNull;

import com.ganxin.doingdaily.common.data.model.PictureGankBean;
import com.ganxin.doingdaily.common.data.model.PictureShowBean;
import com.ganxin.doingdaily.common.data.model.ZhihuArticleBean;

import java.util.Map;

/**
 * Description : 公共数据源接口  <br/>
 * author : WangGanxin <br/>
 * date : 2017/7/14 <br/>
 * email : mail@wangganxin.me <br/>
 */
public interface CommonDataSource {

    interface GankPictureCallback {

        void onPicturesLoaded(PictureGankBean pictureGankBean);

        void onDataNotAvailable();
    }

    interface ShowPictureCallback {

        void onPicturesLoaded(PictureShowBean pictureShowBean);

        void onDataNotAvailable();
    }

    interface GetVideoCallback {

        void onVideosLoaded(ZhihuArticleBean zhihuArticleBean);

        void onDataNotAvailable();
    }

    /**
     * gank福利图片
     *
     * @param pageIndex
     * @param callback
     */
    void getGankPictures(int pageIndex, @NonNull GankPictureCallback callback);

    /**
     * 易源图片数据
     *
     * @param callback
     */
    void getShowPictures(Map<String, String> options, @NonNull ShowPictureCallback callback);

    /**
     * 获取视频数据
     *
     * @param callback
     */
    void getVideos(@NonNull GetVideoCallback callback);
}
