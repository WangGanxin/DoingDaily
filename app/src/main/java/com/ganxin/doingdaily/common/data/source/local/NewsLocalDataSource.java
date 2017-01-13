package com.ganxin.doingdaily.common.data.source.local;

import android.content.Context;
import android.support.annotation.NonNull;

import com.ganxin.doingdaily.common.data.source.NewsDataSource;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

/**
 * Description : 新闻频道本地数据源  <br/>
 * author : WangGanxin <br/>
 * date : 2017/1/5 <br/>
 * email : mail@wangganxin.me <br/>
 */
public class NewsLocalDataSource implements NewsDataSource{
    private static NewsLocalDataSource INSTANCE;

    private NewsLocalDataSource(@NonNull Context context) {
        checkNotNull(context);
    }

    public static NewsLocalDataSource getInstance(@NonNull Context context) {
        if (INSTANCE == null) {
            INSTANCE = new NewsLocalDataSource(context);
        }
        return INSTANCE;
    }
}
