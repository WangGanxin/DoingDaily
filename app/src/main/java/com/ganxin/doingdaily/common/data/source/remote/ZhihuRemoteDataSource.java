package com.ganxin.doingdaily.common.data.source.remote;

import android.support.annotation.NonNull;

import com.ganxin.doingdaily.common.data.model.ZhihuBeforeNewsBean;
import com.ganxin.doingdaily.common.data.model.ZhihuLatestNewsBean;
import com.ganxin.doingdaily.common.data.source.callback.ZhihuDataSource;
import com.ganxin.doingdaily.common.network.NetworkManager;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Description : 知乎服务端数据源  <br/>
 * author : WangGanxin <br/>
 * date : 2017/7/11 <br/>
 * email : mail@wangganxin.me <br/>
 */
public class ZhihuRemoteDataSource implements ZhihuDataSource {
    private static ZhihuRemoteDataSource INSTANCE;

    private ZhihuRemoteDataSource() {

    }

    public static ZhihuRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ZhihuRemoteDataSource();
        }
        return INSTANCE;
    }

    @Override
    public void getBeforeNews(@NonNull String date, @NonNull final GetBeforeNewsCallback callback) {
        NetworkManager.getZhihuAPI().getZhihuBeforeNews(date).subscribeOn(Schedulers.newThread())//子线程访问网络
                .observeOn(AndroidSchedulers.mainThread())//回调到主线程
                .subscribe(new Action1<ZhihuBeforeNewsBean>() {
                    @Override
                    public void call(ZhihuBeforeNewsBean bean) {
                        callback.onBeforeNewsLoaded(bean);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        callback.onDataNotAvailable();
                    }
                });
    }

    @Override
    public void getLatestNews(@NonNull final GetLatestNewsCallback callback) {
        NetworkManager.getZhihuAPI().getZhihuLatestNews().subscribeOn(Schedulers.newThread())//子线程访问网络
                .observeOn(AndroidSchedulers.mainThread())//回调到主线程
                .subscribe(new Action1<ZhihuLatestNewsBean>() {
                    @Override
                    public void call(ZhihuLatestNewsBean zhihuLatestNewsBean) {
                        callback.onLatestNewsLoaded(zhihuLatestNewsBean);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        callback.onDataNotAvailable();
                    }
                });
    }

    @Override
    public void getArticle(@NonNull String articleId, @NonNull final GetArticleCallback callback) {
        NetworkManager.getZhihuAPI().getZhihuArticle(articleId).subscribeOn(Schedulers.newThread())//子线程访问网络
                .observeOn(AndroidSchedulers.mainThread())//回调到主线程
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String zhihuLatestNewsBean) {
                       // callback.onLatestNewsLoaded(zhihuLatestNewsBean);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        callback.onDataNotAvailable();
                    }
                });
    }
}
