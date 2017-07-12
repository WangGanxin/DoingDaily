package com.ganxin.doingdaily.module.zhihu.list;

import com.ganxin.doingdaily.common.data.model.ZhihuBeforeNewsBean;
import com.ganxin.doingdaily.common.data.model.ZhihuLatestNewsBean;
import com.ganxin.doingdaily.framework.BasePresenter;
import com.ganxin.doingdaily.framework.BaseView;

/**
 * Description : 契约类  <br/>
 * author : WangGanxin <br/>
 * date : 2017/07/11 <br/>
 * email : mail@wangganxin.me <br/>
 */
public interface ZhihuListContract {
    interface View extends BaseView {
        void refreshList(ZhihuLatestNewsBean latestNewsBean);

        void addList(ZhihuBeforeNewsBean beforeNewsBean);

        void loadComplete();
    }

    abstract class Presenter extends BasePresenter<View> {
        protected abstract void getLatestNews();
        protected abstract void getBeforeNews(String date);
    }
}
