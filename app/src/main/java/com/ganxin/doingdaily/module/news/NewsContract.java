package com.ganxin.doingdaily.module.news;

import com.ganxin.doingdaily.common.data.model.NewsChannel;
import com.ganxin.doingdaily.framework.BasePresenter;
import com.ganxin.doingdaily.framework.BaseView;

import java.util.List;

/**
 * Description : 契约类  <br/>
 * author : WangGanxin <br/>
 * date : 2016/11/4 <br/>
 * email : ganxinvip@163.com <br/>
 */
interface NewsContract {
    interface View extends BaseView {
        void addTabs(List<NewsChannel.ShowapiResBodyBean.ChannelListBean> channelList);
    }

    abstract class Presenter extends BasePresenter<View> {
        protected abstract void getChannel();
    }
}