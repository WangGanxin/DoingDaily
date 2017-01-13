package com.ganxin.doingdaily.common.data.source;

import android.support.annotation.NonNull;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

/**
 * Description : 新闻频道数据仓库  <br/>
 * author : WangGanxin <br/>
 * date : 2017/1/5 <br/>
 * email : mail@wangganxin.me <br/>
 */
public class NewsRepository implements NewsDataSource{
    private static NewsRepository INSTANCE = null;

    private final NewsDataSource mNewsRemoteDataSource;

    private final NewsDataSource mNewsLocalDataSource;

    private NewsRepository(@NonNull NewsDataSource newsRemoteDataSource,
                            @NonNull NewsDataSource newsLocalDataSource) {
        mNewsRemoteDataSource = checkNotNull(newsRemoteDataSource);
        mNewsLocalDataSource = checkNotNull(newsLocalDataSource);
    }

    public static NewsRepository getInstance(NewsDataSource newsRemoteDataSource,
                                             NewsDataSource newsLocalDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new NewsRepository(newsRemoteDataSource, newsLocalDataSource);
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}
