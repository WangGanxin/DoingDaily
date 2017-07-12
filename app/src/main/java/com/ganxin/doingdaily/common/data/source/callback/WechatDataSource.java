package com.ganxin.doingdaily.common.data.source.callback;

import android.support.annotation.NonNull;

import com.ganxin.doingdaily.common.data.model.WechatCategory;
import com.ganxin.doingdaily.common.data.model.WechatContent;

import java.util.Map;

/**
 * Description : 微信热文数据源接口  <br/>
 * author : WangGanxin <br/>
 * date : 2017/1/5 <br/>
 * email : mail@wangganxin.me <br/>
 */
public interface WechatDataSource {

    interface GetCategoryCallback {

        void onCategoryLoaded(WechatCategory wechatCategory);

        void onDataNotAvailable();
    }

    interface GetCategoryContentCallback {

        void onCategoryContentLoaded(WechatContent wechatContent);

        void onDataNotAvailable();
    }

    void getCategory(@NonNull GetCategoryCallback callback);

    void getCategoryContent(@NonNull Map<String, String> params, @NonNull GetCategoryContentCallback callback);
}
