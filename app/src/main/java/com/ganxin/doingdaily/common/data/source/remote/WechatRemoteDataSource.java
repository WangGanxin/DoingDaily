package com.ganxin.doingdaily.common.data.source.remote;

import com.ganxin.doingdaily.common.data.source.NewsDataSource;

/**
 * Description : 微信热文服务端数据源  <br/>
 * author : WangGanxin <br/>
 * date : 2017/1/5 <br/>
 * email : mail@wangganxin.me <br/>
 */
public class WechatRemoteDataSource implements NewsDataSource {
    private static WechatRemoteDataSource INSTANCE;

    private WechatRemoteDataSource() {

    }

    public static WechatRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new WechatRemoteDataSource();
        }
        return INSTANCE;
    }
}
