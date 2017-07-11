package com.ganxin.doingdaily.module.zhihu;

import com.ganxin.doingdaily.common.data.model.WechatCategory;
import com.ganxin.doingdaily.framework.BasePresenter;
import com.ganxin.doingdaily.framework.BaseView;

import java.util.List;

/**
 * Description : 契约类  <br/>
 * author : WangGanxin <br/>
 * date : 2017/07/10 <br/>
 * email : mail@wangganxin.me <br/>
 */
interface ZhihuContract {
    interface View extends BaseView {
        void addTabs(List<WechatCategory.ShowapiResBodyBean.TypeListBean> typeList);
    }

    abstract class Presenter extends BasePresenter<View> {
        protected abstract void getCategory();
    }
}
