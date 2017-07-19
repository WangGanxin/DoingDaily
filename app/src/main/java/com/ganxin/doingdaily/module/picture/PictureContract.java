package com.ganxin.doingdaily.module.picture;

import com.ganxin.doingdaily.framework.BasePresenter;
import com.ganxin.doingdaily.framework.BaseView;

/**
 * Description : 契约类  <br/>
 * author : WangGanxin <br/>
 * date : 2017/07/14 <br/>
 * email : mail@wangganxin.me <br/>
 */
interface PictureContract {
    interface View extends BaseView {

    }

    abstract class Presenter extends BasePresenter<View> {

    }
}
