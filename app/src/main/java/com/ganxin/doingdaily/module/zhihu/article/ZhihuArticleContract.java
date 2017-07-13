package com.ganxin.doingdaily.module.zhihu.article;

import com.ganxin.doingdaily.common.data.model.ZhihuArticleBean;
import com.ganxin.doingdaily.framework.BasePresenter;
import com.ganxin.doingdaily.framework.BaseView;

/**
 * Description : 契约类  <br/>
 * author : WangGanxin <br/>
 * date : 2017/7/11 <br/>
 * email : mail@wangganxin.me <br/>
 */
public interface ZhihuArticleContract {

    interface View extends BaseView {
        String getArticleId();
        void setArticle(ZhihuArticleBean articleBean);
        void loading();
        void loadComplete();
        void loadError();
        void backAction();
    }

    abstract class Presenter extends BasePresenter<View> {
        protected abstract void getArticle(String articleId);
    }
}
