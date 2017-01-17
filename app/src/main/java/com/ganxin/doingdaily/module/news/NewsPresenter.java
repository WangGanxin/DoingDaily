package com.ganxin.doingdaily.module.news;

import com.ganxin.doingdaily.common.data.model.NewsChannel;
import com.ganxin.doingdaily.common.data.source.NewsDataSource;
import com.ganxin.doingdaily.common.data.source.NewsRepository;

import java.util.List;

/**
 * Description : NewsPresenter  <br/>
 * author : WangGanxin <br/>
 * date : 2016/11/4 <br/>
 * email : ganxinvip@163.com <br/>
 */
public class NewsPresenter extends NewsContract.Presenter{


    public NewsPresenter(){

    }

    @Override
    public void onStart() {
        getChannel();
    }

    @Override
    protected void getChannel() {
        NewsRepository.getInstance().getChannel(new NewsDataSource.GetChannelCallback() {
            @Override
            public void onChannelLoaded(NewsChannel channel) {
                if(channel !=null){
                    List<NewsChannel.ShowapiResBodyBean.ChannelListBean> channelList= channel.getShowapi_res_body().getChannelList();
                    getView().addTabs(channelList);
                }
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }
}
