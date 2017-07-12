package com.ganxin.doingdaily.common.data.source;

import android.support.annotation.NonNull;

import com.ganxin.doingdaily.common.data.source.callback.ZhihuDataSource;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

/**
 * Description : 微信热文数据仓库  <br/>
 * author : WangGanxin <br/>
 * date : 2017/1/5 <br/>
 * email : mail@wangganxin.me <br/>
 */
public class ZhihuRepository implements ZhihuDataSource {

    private static ZhihuRepository INSTANCE = null;

    private final ZhihuDataSource mZhihuRemoteDataSource;

    private final ZhihuDataSource mZhihuLocalDataSource;

    private ZhihuRepository(@NonNull ZhihuDataSource zhihuRemoteDataSource,
                            @NonNull ZhihuDataSource zhihuLocalDataSource) {
        mZhihuRemoteDataSource = checkNotNull(zhihuRemoteDataSource);
        mZhihuLocalDataSource = checkNotNull(zhihuLocalDataSource);
    }

    public static void initialize(ZhihuDataSource zhihuRemoteDataSource,
                                  ZhihuDataSource zhihuLocalDataSource){
        INSTANCE=null;
        INSTANCE = new ZhihuRepository(zhihuRemoteDataSource, zhihuLocalDataSource);
    }

    public static ZhihuRepository getInstance() {
        if (INSTANCE == null) {
            throw new NullPointerException("the repository must be init !");
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    @Override
    public void getBeforeNews(@NonNull String date, @NonNull GetBeforeNewsCallback callback) {
        mZhihuRemoteDataSource.getBeforeNews(date,callback);
    }

    @Override
    public void getLatestNews(@NonNull GetLatestNewsCallback callback) {
        mZhihuRemoteDataSource.getLatestNews(callback);
    }

    @Override
    public void getArticle(@NonNull String articleId, @NonNull GetArticleCallback callback) {
        mZhihuRemoteDataSource.getArticle(articleId,callback);
    }

}
