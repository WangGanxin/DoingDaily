package com.ganxin.doingdaily.module.video;

import com.ganxin.doingdaily.common.data.model.VideoBean;
import com.ganxin.doingdaily.common.data.model.VideoPageBean;
import com.ganxin.doingdaily.common.data.source.CommonRepository;
import com.ganxin.doingdaily.common.data.source.callback.CommonDataSource;

import java.util.HashMap;
import java.util.List;
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

    }

    @Override
    protected void getVideoList(final int pageIndex) {
        Map<String, String> options = new HashMap<>();
        options.put("type", "41"); //视频
        options.put("page", String.valueOf(pageIndex));

        CommonRepository.getInstance().getVideos(options, new CommonDataSource.GetVideoCallback() {
            @Override
            public void onVideosLoaded(VideoPageBean videoPageBean) {
                if (videoPageBean != null) {
                    List<VideoBean> list =videoPageBean.getContentlist();
                    if (list != null && list.size() > 0) {
                        try {
                            if (pageIndex == 1) {
                                getView().refreshContentList(list);
                            } else {
                                getView().addContentList(list);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
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
