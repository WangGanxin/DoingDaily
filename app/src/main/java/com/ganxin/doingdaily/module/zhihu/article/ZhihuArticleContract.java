package com.ganxin.doingdaily.module.zhihu.article;

import com.ganxin.doingdaily.common.data.model.ZhihuBeforeNewsBean;
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
        void setArticle(ZhihuBeforeNewsBean beforeNewsBean);
        void loadComplete();
        void loadError();
    }

    abstract class Presenter extends BasePresenter<View> {
        protected abstract void getArticle();
    }
}
