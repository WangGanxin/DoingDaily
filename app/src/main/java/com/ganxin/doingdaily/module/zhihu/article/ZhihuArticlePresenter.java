package com.ganxin.doingdaily.module.zhihu.article;

import com.ganxin.doingdaily.common.data.model.ZhihuLatestNewsBean;
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
        getArticle();
    }

    @Override
    protected void getArticle() {
        ZhihuRepository.getInstance().getArticle("9517717", new ZhihuDataSource.GetArticleCallback() {
            @Override
            public void onLatestNewsLoaded(ZhihuLatestNewsBean zhihuLatestNewsBean) {

            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }
}
