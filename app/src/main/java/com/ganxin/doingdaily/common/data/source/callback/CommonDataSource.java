package com.ganxin.doingdaily.common.data.source.callback;

import android.support.annotation.NonNull;

import com.ganxin.doingdaily.common.data.model.ZhihuArticleBean;

/**
 * Description : 公共数据源接口  <br/>
 * author : WangGanxin <br/>
 * date : 2017/7/14 <br/>
 * email : mail@wangganxin.me <br/>
 */
public interface CommonDataSource {

    interface GetArticleCallback {

        void onLatestNewsLoaded(ZhihuArticleBean zhihuArticleBean);

        void onDataNotAvailable();
    }

    void getArticle(@NonNull String articleId, @NonNull GetArticleCallback callback);
}
