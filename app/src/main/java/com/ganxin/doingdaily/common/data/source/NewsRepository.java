package com.ganxin.doingdaily.common.data.source;

import android.support.annotation.NonNull;

import java.util.Map;

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

    public static void initialize(NewsDataSource newsRemoteDataSource,
                            NewsDataSource newsLocalDataSource){
        INSTANCE=null;
        INSTANCE = new NewsRepository(newsRemoteDataSource, newsLocalDataSource);
    }

    public static NewsRepository getInstance() {
        if (INSTANCE == null) {
            throw new NullPointerException("the repository must be init !");
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    @Override
    public void getChannel(@NonNull final GetChannelCallback callback) {
        mNewsRemoteDataSource.getChannel(callback);
    }

    @Override
    public void getChannelContent(@NonNull Map<String, String> params, @NonNull GetNewsContentCallback callback) {
        mNewsRemoteDataSource.getChannelContent(params,callback);
    }
}
