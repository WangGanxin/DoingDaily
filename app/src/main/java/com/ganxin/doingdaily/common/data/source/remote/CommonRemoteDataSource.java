package com.ganxin.doingdaily.common.data.source.remote;

import android.support.annotation.NonNull;

import com.ganxin.doingdaily.common.data.model.ZhihuArticleBean;
import com.ganxin.doingdaily.common.data.source.callback.CommonDataSource;
import com.ganxin.doingdaily.common.network.NetworkManager;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Description : 服务端数据源  <br/>
 * author : WangGanxin <br/>
 * date : 2017/7/14 <br/>
 * email : mail@wangganxin.me <br/>
 */
public class CommonRemoteDataSource implements CommonDataSource {
    private static CommonRemoteDataSource INSTANCE;

    private CommonRemoteDataSource() {

    }

    public static CommonRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CommonRemoteDataSource();
        }
        return INSTANCE;
    }

    @Override
    public void getArticle(@NonNull String articleId, @NonNull final GetArticleCallback callback) {
        NetworkManager.getZhihuAPI().getZhihuArticle(articleId).subscribeOn(Schedulers.newThread())//子线程访问网络
                .observeOn(AndroidSchedulers.mainThread())//回调到主线程
                .subscribe(new Action1<ZhihuArticleBean>() {
                    @Override
                    public void call(ZhihuArticleBean articleBean) {
                        callback.onLatestNewsLoaded(articleBean);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        callback.onDataNotAvailable();
                    }
                });
    }
}
