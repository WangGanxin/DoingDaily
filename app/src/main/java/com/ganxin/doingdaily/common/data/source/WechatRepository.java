package com.ganxin.doingdaily.common.data.source;

import android.support.annotation.NonNull;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

/**
 * Description : 微信热文数据仓库  <br/>
 * author : WangGanxin <br/>
 * date : 2017/1/5 <br/>
 * email : mail@wangganxin.me <br/>
 */
public class WechatRepository implements WechatDataSource {

    private static WechatRepository INSTANCE = null;

    private final WechatDataSource mWechatRemoteDataSource;

    private final WechatDataSource mWechatLocalDataSource;

    private WechatRepository(@NonNull WechatDataSource wechatRemoteDataSource,
                             @NonNull WechatDataSource wechatLocalDataSource) {
        mWechatRemoteDataSource = checkNotNull(wechatRemoteDataSource);
        mWechatLocalDataSource = checkNotNull(wechatLocalDataSource);
    }

    public static WechatRepository getInstance(WechatDataSource wechatRemoteDataSource,
                                               WechatDataSource wechatLocalDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new WechatRepository(wechatRemoteDataSource, wechatLocalDataSource);
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}
