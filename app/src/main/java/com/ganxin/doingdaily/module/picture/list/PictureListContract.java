package com.ganxin.doingdaily.module.picture.list;

import com.ganxin.doingdaily.common.data.model.PictureBean;
import com.ganxin.doingdaily.framework.BasePresenter;
import com.ganxin.doingdaily.framework.BaseView;

import java.util.List;

/**
 * Description : 契约类  <br/>
 * author : WangGanxin <br/>
 * date : 2017/07/17 <br/>
 * email : mail@wangganxin.me <br/>
 */
interface PictureListContract {

    interface View extends BaseView {
        void refreshContentList(List<PictureBean> pictureBeanList);
        void addContentList(List<PictureBean> pictureBeanList);
        void loadComplete();
    }

    abstract class Presenter extends BasePresenter<PictureListContract.View> {
        protected abstract void getGankPictures(int pageIndex);
        protected abstract void getShowPictures(int pageIndex);
    }
}
