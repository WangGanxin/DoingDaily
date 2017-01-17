package com.ganxin.doingdaily.common.data.source;

import android.support.annotation.NonNull;

import com.ganxin.doingdaily.common.data.model.NewsChannel;
import com.ganxin.doingdaily.common.data.model.NewsContent;

import java.util.Map;

/**
 * Description : 新闻频道数据源接口  <br/>
 * author : WangGanxin <br/>
 * date : 2017/1/5 <br/>
 * email : mail@wangganxin.me <br/>
 */
public interface NewsDataSource {

    interface GetChannelCallback {

        void onChannelLoaded(NewsChannel channel);

        void onDataNotAvailable();
    }

    interface GetNewsContentCallback {

        void onNewsContentLoaded(NewsContent newsContent);

        void onDataNotAvailable();
    }

    void getChannel(@NonNull GetChannelCallback callback);

    void getChannelContent(@NonNull Map<String,String> params, @NonNull GetNewsContentCallback callback);
}
