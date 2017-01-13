package com.ganxin.doingdaily.module.news;

import com.ganxin.doingdaily.common.data.model.NewsChannel;
import com.ganxin.doingdaily.common.network.NetworkManager;

import java.util.List;

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
        getTabs();
    }

    @Override
    protected void getTabs() {
        NetworkManager.getAPI().getNewsChannel().subscribeOn(Schedulers.newThread())//子线程访问网络
                .observeOn(AndroidSchedulers.mainThread())//回调到主线程
                .subscribe(new Action1<NewsChannel>() {
                    @Override
                    public void call(NewsChannel newsChannel) {
                        if(newsChannel !=null){
                            List<NewsChannel.ShowapiResBodyBean.ChannelListBean> channelList= newsChannel.getShowapi_res_body().getChannelList();
                            if(channelList!=null&&channelList.size()>0){
                                getView().addTabs(channelList);
                            }
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });
    }
}
