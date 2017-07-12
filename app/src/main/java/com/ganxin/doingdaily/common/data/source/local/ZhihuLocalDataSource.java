package com.ganxin.doingdaily.common.data.source.local;

import android.content.Context;
import android.support.annotation.NonNull;

import com.ganxin.doingdaily.common.data.source.callback.ZhihuDataSource;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

/**
 * Description : 知乎本地数据源  <br/>
 * author : WangGanxin <br/>
 * date : 2017/7/11 <br/>
 * email : mail@wangganxin.me <br/>
 */
public class ZhihuLocalDataSource implements ZhihuDataSource {
    private static ZhihuLocalDataSource INSTANCE;

    private ZhihuLocalDataSource(@NonNull Context context) {
        checkNotNull(context);
    }

    public static ZhihuLocalDataSource getInstance(@NonNull Context context) {
        if (INSTANCE == null) {
            INSTANCE = new ZhihuLocalDataSource(context);
        }
        return INSTANCE;
    }

    @Override
    public void getBeforeNews(@NonNull String date, @NonNull GetBeforeNewsCallback callback) {

    }

    @Override
    public void getLatestNews(@NonNull GetLatestNewsCallback callback) {

    }

    @Override
    public void getArticle(@NonNull String articleId, @NonNull GetArticleCallback callback) {

    }

}
