package com.ganxin.doingdaily.module.loading;

import android.view.animation.Animation;

import com.ganxin.doingdaily.framework.BasePresenter;
import com.ganxin.doingdaily.framework.BaseView;

/**
 * Description : 契约类  <br/>
 * author : WangGanxin <br/>
 * date : 2016/10/28 <br/>
 * email : ganxinvip@163.com <br/>
 */
interface LoadingContract {
    interface View extends BaseView {
        Animation createBackgroundAnimation();
        Animation createLogoAnimation();
        void jumpToMain();
    }

    abstract class Presenter extends BasePresenter<View> {
        //public abstract void getData(String content);
    }
}
