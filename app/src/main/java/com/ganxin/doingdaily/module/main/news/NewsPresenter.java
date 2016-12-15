package com.ganxin.doingdaily.module.main.news;

import com.ganxin.doingdaily.common.data.model.News;
import com.ganxin.doingdaily.common.network.NetworkManager;
import com.ganxin.doingdaily.common.utils.LogUtil;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Description : NewsPresenter  <br/>
 * author : WangGanxin <br/>
 * date : 2016/11/4 <br/>
 * email : ganxinvip@163.com <br/>
 */
public class NewsPresenter extends NewsContract.Presenter{

    @Override
    public void onStart() {
        LogUtil.i("----------------111---start");
    }

    @Override
    protected void getTabs() {
        NetworkManager.getAPI().getNews().subscribeOn(Schedulers.newThread())//子线程访问网络
                .observeOn(AndroidSchedulers.mainThread())//回调到主线程
                .subscribe(new Action1<News>() {
                    @Override
                    public void call(News news) {
                        LogUtil.i("222--total----" + news.getShowapi_res_body().getTotalNum());
                        // getView().
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        LogUtil.i("----on error--" + throwable.getMessage());
                    }
                });
    }
}
