package com.ganxin.doingdaily.module.main.news;

import com.ganxin.doingdaily.common.data.model.ContentlistBean;
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
        void addContentlist(List<ContentlistBean> contentlistBeanList);
    }

    abstract class Presenter extends BasePresenter<View> {
        protected abstract void getContents(String channelId,int pageIndex);
    }
}