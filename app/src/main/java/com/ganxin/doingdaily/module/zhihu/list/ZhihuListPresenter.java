package com.ganxin.doingdaily.module.zhihu.list;

import com.ganxin.doingdaily.common.data.model.ZhihuBeforeNewsBean;
import com.ganxin.doingdaily.common.data.model.ZhihuLatestNewsBean;
import com.ganxin.doingdaily.common.data.source.ZhihuRepository;
import com.ganxin.doingdaily.common.data.source.callback.ZhihuDataSource;

/**
 * Description : ZhihuListPresenter  <br/>
 * author : WangGanxin <br/>
 * date : 2017/07/11 <br/>
 * email : mail@wangganxin.me <br/>
 */
public class ZhihuListPresenter extends ZhihuListContract.Presenter {
    @Override
    public void onStart() {

    }

    @Override
    protected void getLatestNews() {
         ZhihuRepository.getInstance().getLatestNews(new ZhihuDataSource.GetLatestNewsCallback() {
             @Override
             public void onLatestNewsLoaded(ZhihuLatestNewsBean zhihuLatestNewsBean) {
                 if (zhihuLatestNewsBean != null) {
                     getView().refreshList(zhihuLatestNewsBean);
                 }
                 getView().loadComplete();
             }

             @Override
             public void onDataNotAvailable() {
                 getView().loadComplete();
             }
         });
    }

    @Override
    protected void getBeforeNews(String date) {
        ZhihuRepository.getInstance().getBeforeNews(date, new ZhihuDataSource.GetBeforeNewsCallback() {
            @Override
            public void onBeforeNewsLoaded(ZhihuBeforeNewsBean bean) {
                if (bean != null) {
                    getView().addList(bean);
                }
                getView().loadComplete();
            }

            @Override
            public void onDataNotAvailable() {
                getView().loadComplete();
            }
        });
    }

}
