package com.ganxin.doingdaily.common.data.source.remote;

import android.support.annotation.NonNull;

import com.ganxin.doingdaily.common.data.model.NewsChannel;
import com.ganxin.doingdaily.common.data.model.NewsContent;
import com.ganxin.doingdaily.common.data.source.NewsDataSource;
import com.ganxin.doingdaily.common.network.NetworkManager;

import java.util.Map;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

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

    @Override
    public void getChannel(@NonNull final GetChannelCallback callback) {
        NetworkManager.getAPI().getNewsChannel().subscribeOn(Schedulers.newThread())//子线程访问网络
                .observeOn(AndroidSchedulers.mainThread())//回调到主线程
                .subscribe(new Action1<NewsChannel>() {
                    @Override
                    public void call(NewsChannel newsChannel) {
                        callback.onChannelLoaded(newsChannel);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        callback.onDataNotAvailable();
                    }
                });
    }

    @Override
    public void getChannelContent(@NonNull Map<String, String> params, @NonNull final GetNewsContentCallback callback) {
        NetworkManager.getAPI().getNewsContent(params).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<NewsContent>() {
                    @Override
                    public void call(NewsContent newsContent) {
                        callback.onNewsContentLoaded(newsContent);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        callback.onDataNotAvailable();
                    }
                });
    }

}
