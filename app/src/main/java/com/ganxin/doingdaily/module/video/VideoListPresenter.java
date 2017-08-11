package com.ganxin.doingdaily.module.video;

import com.ganxin.doingdaily.common.data.model.ZhihuArticleBean;
import com.ganxin.doingdaily.common.data.source.CommonRepository;
import com.ganxin.doingdaily.common.data.source.callback.CommonDataSource;

import java.util.HashMap;
import java.util.Map;

/**
 * Description : VideoListPresenter  <br/>
 * author : WangGanxin <br/>
 * date : 2017/7/21 <br/>
 * email : mail@wangganxin.me <br/>
 */
public class VideoListPresenter extends VideoListContract.Presenter {
    @Override
    public void onStart() {
        getVideoList(1);
    }

    @Override
    protected void getVideoList(final int pageIndex) {
        Map<String, String> options = new HashMap<>();
        options.put("type", "41"); //视频
        options.put("page", String.valueOf(pageIndex));

        CommonRepository.getInstance().getVideos(options, new CommonDataSource.GetVideoCallback() {
            @Override
            public void onVideosLoaded(ZhihuArticleBean zhihuArticleBean) {
//                if (wechatContent != null) {
//                    List<WechatContentlistBean> list =wechatContent.getShowapi_res_body().getPagebean().getContentlist();
//                    if (list != null && list.size() > 0) {
//                        if (pageIndex == 1) {
//                            getView().refreshContentList(list);
//                        } else {
//                            getView().addContentList(list);
//                        }
//                    }
//                }
                getView().loadComplete();
            }

            @Override
            public void onDataNotAvailable() {
                getView().loadComplete();
            }
        });
    }
}
