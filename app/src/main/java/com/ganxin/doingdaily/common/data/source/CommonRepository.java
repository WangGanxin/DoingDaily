package com.ganxin.doingdaily.common.data.source;

import android.support.annotation.NonNull;

import com.ganxin.doingdaily.common.data.source.callback.CommonDataSource;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

/**
 * Description : 公共数据仓库  <br/>
 * author : WangGanxin <br/>
 * date : 2017/7/14 <br/>
 * email : mail@wangganxin.me <br/>
 */
public class CommonRepository implements CommonDataSource {

    private static CommonRepository INSTANCE = null;

    private final CommonDataSource mCommonRemoteDataSource;

    private final CommonDataSource mCommonLocalDataSource;

    private CommonRepository(@NonNull CommonDataSource commonRemoteDataSource,
                             @NonNull CommonDataSource commonLocalDataSource) {
        mCommonRemoteDataSource = checkNotNull(commonRemoteDataSource);
        mCommonLocalDataSource = checkNotNull(commonLocalDataSource);
    }

    public static void initialize(CommonDataSource commonRemoteDataSource,
                                  CommonDataSource commonLocalDataSource) {
        INSTANCE = null;
        INSTANCE = new CommonRepository(commonRemoteDataSource, commonLocalDataSource);
    }

    public static CommonRepository getInstance() {
        if (INSTANCE == null) {
            throw new NullPointerException("the repository must be init !");
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    @Override
    public void getArticle(@NonNull String articleId, @NonNull GetArticleCallback callback) {
        mCommonRemoteDataSource.getArticle(articleId, callback);
    }

}
