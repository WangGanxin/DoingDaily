package com.ganxin.doingdaily.common.data.source.local;

import android.content.Context;
import android.support.annotation.NonNull;

import com.ganxin.doingdaily.common.data.source.WechatDataSource;

import java.util.Map;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

/**
 * Description : 微信热文本地数据源  <br/>
 * author : WangGanxin <br/>
 * date : 2017/1/5 <br/>
 * email : mail@wangganxin.me <br/>
 */
public class WechatLocalDataSource implements WechatDataSource {
    private static WechatLocalDataSource INSTANCE;

    private WechatLocalDataSource(@NonNull Context context) {
        checkNotNull(context);
    }

    public static WechatLocalDataSource getInstance(@NonNull Context context) {
        if (INSTANCE == null) {
            INSTANCE = new WechatLocalDataSource(context);
        }
        return INSTANCE;
    }

    @Override
    public void getCategory(@NonNull GetCategoryCallback callback) {

    }

    @Override
    public void getCategoryContent(@NonNull Map<String, String> params, @NonNull GetCategoryContentCallback callback) {

    }
}
