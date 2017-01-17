package com.ganxin.doingdaily.module.news.list;

import com.ganxin.doingdaily.common.data.model.NewsContentlistBean;
import com.ganxin.doingdaily.framework.BasePresenter;
import com.ganxin.doingdaily.framework.BaseView;

import java.util.List;

/**
 * Description : 契约类  <br/>
 * author : WangGanxin <br/>
 * date : 2016/11/4 <br/>
 * email : ganxinvip@163.com <br/>
 */
interface NewsListContract {
    interface View extends BaseView {
        void refreshContentList(List<NewsContentlistBean> contentlistBeanList);
        void addContentList(List<NewsContentlistBean> contentlistBeanList);
        void loadComplete();
    }

    abstract class Presenter extends BasePresenter<View> {
        protected abstract void getListContent(String channelId, int pageIndex);
    }
}