package com.ganxin.doingdaily.module.wechat.article;

import com.ganxin.doingdaily.framework.BasePresenter;
import com.ganxin.doingdaily.framework.BaseView;

/**
 * Description : 契约类  <br/>
 * author : WangGanxin <br/>
 * date : 2017/1/17 <br/>
 * email : mail@wangganxin.me <br/>
 */
public interface WechatArticleContract {
    interface View extends BaseView {

    }

    abstract class Presenter extends BasePresenter<View> {

    }
}
