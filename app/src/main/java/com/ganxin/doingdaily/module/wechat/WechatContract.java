package com.ganxin.doingdaily.module.wechat;

import com.ganxin.doingdaily.common.data.model.WechatCategory;
import com.ganxin.doingdaily.framework.BasePresenter;
import com.ganxin.doingdaily.framework.BaseView;

import java.util.List;

/**
 * Description : 契约类  <br/>
 * author : WangGanxin <br/>
 * date : 2016/11/4 <br/>
 * email : ganxinvip@163.com <br/>
 */
interface WechatContract {
    interface View extends BaseView {
        void addTabs(List<WechatCategory.ShowapiResBodyBean.TypeListBean> typeList);
    }

    abstract class Presenter extends BasePresenter<View> {
        protected abstract void getCategory();
    }
}
