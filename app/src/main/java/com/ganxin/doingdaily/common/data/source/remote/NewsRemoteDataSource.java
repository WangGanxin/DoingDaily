package com.ganxin.doingdaily.common.data.source.remote;

import com.ganxin.doingdaily.common.data.source.NewsDataSource;

/**
 * Description : 新闻服务端数据源  <br/>
 * author : WangGanxin <br/>
 * date : 2017/1/5 <br/>
 * email : mail@wangganxin.me <br/>
 */
public class NewsRemoteDataSource implements NewsDataSource {

    private static NewsRemoteDataSource INSTANCE;

    private NewsRemoteDataSource() {

    }

    public static NewsRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new NewsRemoteDataSource();
        }
        return INSTANCE;
    }
}
