package com.ganxin.doingdaily.module.picture.browser;

import com.ganxin.doingdaily.common.data.model.ZhihuArticleBean;
import com.ganxin.doingdaily.common.data.source.CommonRepository;
import com.ganxin.doingdaily.common.data.source.callback.CommonDataSource;

import java.util.HashMap;
import java.util.Map;

/**
 * Description : PictureBrowserPresenter  <br/>
 * author : WangGanxin <br/>
 * date : 2017/07/17 <br/>
 * email : mail@wangganxin.me <br/>
 */
public class PictureBrowserPresenter extends PictureBrowserContract.Presenter{

    @Override
    public void onStart() {
        getGankPictures();
    }

    @Override
    protected void getGankPictures() {
        int pageIndex=1;
        CommonRepository.getInstance().getGankPictures(pageIndex, new CommonDataSource.GankPictureCallback() {
            @Override
            public void onLatestNewsLoaded(ZhihuArticleBean zhihuArticleBean) {

            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }

    @Override
    protected void getShowPictures() {
        int pageIndex=1;
        Map<String, String> options = new HashMap<>();
        options.put("num", "10");
        options.put("page", String.valueOf(pageIndex));
        options.put("rand", "1");

        CommonRepository.getInstance().getShowPictures(options, new CommonDataSource.ShowPictureCallback() {
            @Override
            public void onLatestNewsLoaded(ZhihuArticleBean zhihuArticleBean) {

            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }
}
