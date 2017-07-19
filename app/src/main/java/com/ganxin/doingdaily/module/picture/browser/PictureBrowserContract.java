package com.ganxin.doingdaily.module.picture.browser;

import com.ganxin.doingdaily.framework.BasePresenter;
import com.ganxin.doingdaily.framework.BaseView;

/**
 * Description : 契约类  <br/>
 * author : WangGanxin <br/>
 * date : 2017/07/17 <br/>
 * email : mail@wangganxin.me <br/>
 */
interface PictureBrowserContract {
    interface View extends BaseView {

    }

    abstract class Presenter extends BasePresenter<View> {

    }
}
