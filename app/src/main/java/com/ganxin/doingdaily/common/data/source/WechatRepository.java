package com.ganxin.doingdaily.common.data.source;

import android.support.annotation.NonNull;

import com.ganxin.doingdaily.common.data.source.callback.WechatDataSource;

import java.util.Map;

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

    public static void initialize(WechatDataSource wechatRemoteDataSource,
                            WechatDataSource wechatLocalDataSource){
        INSTANCE=null;
        INSTANCE = new WechatRepository(wechatRemoteDataSource, wechatLocalDataSource);
    }

    public static WechatRepository getInstance() {
        if (INSTANCE == null) {
            throw new NullPointerException("the repository must be init !");
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    @Override
    public void getCategory(@NonNull GetCategoryCallback callback) {
        mWechatRemoteDataSource.getCategory(callback);
    }

    @Override
    public void getCategoryContent(@NonNull Map<String, String> params, @NonNull GetCategoryContentCallback callback) {
        mWechatRemoteDataSource.getCategoryContent(params,callback);
    }
}
