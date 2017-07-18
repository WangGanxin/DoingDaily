package com.ganxin.doingdaily.module.picture.list;

import com.ganxin.doingdaily.common.data.model.NewsContentlistBean;
import com.ganxin.doingdaily.framework.BasePresenter;
import com.ganxin.doingdaily.framework.BaseView;

import java.util.List;

/**
 * Description : 契约类  <br/>
 * author : WangGanxin <br/>
 * date : 2017/07/17 <br/>
 * email : mail@wangganxin.me <br/>
 */
interface PictureListContract {

    interface View extends BaseView {
        void refreshContentList(List<NewsContentlistBean> contentlistBeanList);
        void addContentList(List<NewsContentlistBean> contentlistBeanList);
        void loadComplete();
    }

    abstract class Presenter extends BasePresenter<PictureListContract.View> {
        protected abstract void getListContent(String channelId, int pageIndex);
        protected abstract void getGankPictures();
        protected abstract void getShowPictures();
    }
}
