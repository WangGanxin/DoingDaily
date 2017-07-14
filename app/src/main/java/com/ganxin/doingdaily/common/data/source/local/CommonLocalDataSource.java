package com.ganxin.doingdaily.common.data.source.local;

import android.content.Context;
import android.support.annotation.NonNull;

import com.ganxin.doingdaily.common.data.source.callback.CommonDataSource;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

/**
 * Description : 本地数据源  <br/>
 * author : WangGanxin <br/>
 * date : 2017/7/14 <br/>
 * email : mail@wangganxin.me <br/>
 */
public class CommonLocalDataSource implements CommonDataSource {
    private static CommonLocalDataSource INSTANCE;

    private CommonLocalDataSource(@NonNull Context context) {
        checkNotNull(context);
    }

    public static CommonLocalDataSource getInstance(@NonNull Context context) {
        if (INSTANCE == null) {
            INSTANCE = new CommonLocalDataSource(context);
        }
        return INSTANCE;
    }

    @Override
    public void getArticle(@NonNull String articleId, @NonNull GetArticleCallback callback) {

    }

}
