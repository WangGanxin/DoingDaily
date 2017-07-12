package com.ganxin.doingdaily.common.data.source.remote;

import android.support.annotation.NonNull;

import com.ganxin.doingdaily.common.data.model.WechatCategory;
import com.ganxin.doingdaily.common.data.model.WechatContent;
import com.ganxin.doingdaily.common.data.source.callback.WechatDataSource;
import com.ganxin.doingdaily.common.network.NetworkManager;

import java.util.Map;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Description : 微信热文服务端数据源  <br/>
 * author : WangGanxin <br/>
 * date : 2017/1/5 <br/>
 * email : mail@wangganxin.me <br/>
 */
public class WechatRemoteDataSource implements WechatDataSource {
    private static WechatRemoteDataSource INSTANCE;

    private WechatRemoteDataSource() {

    }

    public static WechatRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new WechatRemoteDataSource();
        }
        return INSTANCE;
    }

    @Override
    public void getCategory(@NonNull final GetCategoryCallback callback) {
        NetworkManager.getShowAPI().getWechatCategory().subscribeOn(Schedulers.newThread())//子线程访问网络
                .observeOn(AndroidSchedulers.mainThread())//回调到主线程
                .subscribe(new Action1<WechatCategory>() {
                    @Override
                    public void call(WechatCategory wechatCategory) {
                        callback.onCategoryLoaded(wechatCategory);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        callback.onDataNotAvailable();
                    }
                });
    }

    @Override
    public void getCategoryContent(@NonNull Map<String, String> params, @NonNull final GetCategoryContentCallback callback) {
        NetworkManager.getShowAPI().getWechatCategoryContent(params).subscribeOn(Schedulers.newThread())//子线程访问网络
                .observeOn(AndroidSchedulers.mainThread())//回调到主线程
                .subscribe(new Action1<WechatContent>() {
                    @Override
                    public void call(WechatContent wechatContent) {
                        callback.onCategoryContentLoaded(wechatContent);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        callback.onDataNotAvailable();
                    }
                });
    }
}
