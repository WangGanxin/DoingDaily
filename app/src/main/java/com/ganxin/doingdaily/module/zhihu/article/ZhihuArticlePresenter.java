package com.ganxin.doingdaily.module.zhihu.article;

import com.ganxin.doingdaily.common.data.model.ZhihuArticleBean;
import com.ganxin.doingdaily.common.data.source.ZhihuRepository;
import com.ganxin.doingdaily.common.data.source.callback.ZhihuDataSource;

/**
 * Description : ZhihuArticlePresenter  <br/>
 * author : WangGanxin <br/>
 * date : 2017/7/11 <br/>
 * email : mail@wangganxin.me <br/>
 */
public class ZhihuArticlePresenter extends ZhihuArticleContract.Presenter{

    @Override
    public void onStart() {
        getView().loading();
    }

    @Override
    protected void getArticle(String articleId) {
        ZhihuRepository.getInstance().getArticle(articleId, new ZhihuDataSource.GetArticleCallback() {
            @Override
            public void onLatestNewsLoaded(ZhihuArticleBean articleBean) {
                getView().loadComplete();

                if (articleBean != null) {
                    getView().setArticle(articleBean);
                }
            }

            @Override
            public void onDataNotAvailable() {
                getView().loadError();
            }
        });
    }
}
