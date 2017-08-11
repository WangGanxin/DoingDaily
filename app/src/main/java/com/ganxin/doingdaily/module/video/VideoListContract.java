package com.ganxin.doingdaily.module.video;

import com.ganxin.doingdaily.common.data.model.WechatContentlistBean;
import com.ganxin.doingdaily.framework.BasePresenter;
import com.ganxin.doingdaily.framework.BaseView;

import java.util.List;

/**
 * Description : 契约类  <br/>
 * author : WangGanxin <br/>
 * date : 2017/7/21 <br/>
 * email : mail@wangganxin.me <br/>
 */
public interface VideoListContract {
    interface View extends BaseView {
        void refreshContentList(List<WechatContentlistBean> contentlist);

        void addContentList(List<WechatContentlistBean> contentlist);

        void loadComplete();
    }

    abstract class Presenter extends BasePresenter<View> {
        protected abstract void getVideoList(int pageIndex);
    }
}
