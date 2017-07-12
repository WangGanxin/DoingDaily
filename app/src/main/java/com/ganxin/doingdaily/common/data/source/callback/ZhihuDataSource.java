package com.ganxin.doingdaily.common.data.source.callback;

import android.support.annotation.NonNull;

import com.ganxin.doingdaily.common.data.model.ZhihuBeforeNewsBean;
import com.ganxin.doingdaily.common.data.model.ZhihuLatestNewsBean;

/**
 * Description : 知乎数据源接口  <br/>
 * author : WangGanxin <br/>
 * date : 2017/7/11 <br/>
 * email : mail@wangganxin.me <br/>
 */
public interface ZhihuDataSource {

    interface GetBeforeNewsCallback {

        void onBeforeNewsLoaded(ZhihuBeforeNewsBean bean);

        void onDataNotAvailable();
    }

    interface GetLatestNewsCallback {

        void onLatestNewsLoaded(ZhihuLatestNewsBean zhihuLatestNewsBean);

        void onDataNotAvailable();
    }

    interface GetArticleCallback {

        void onLatestNewsLoaded(ZhihuLatestNewsBean zhihuLatestNewsBean);

        void onDataNotAvailable();
    }

    void getBeforeNews(@NonNull String date, @NonNull GetBeforeNewsCallback callback);

    void getLatestNews(@NonNull GetLatestNewsCallback callback);

    void getArticle(@NonNull String articleId, @NonNull GetArticleCallback callback);
}
