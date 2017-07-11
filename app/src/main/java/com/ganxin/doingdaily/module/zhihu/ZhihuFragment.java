package com.ganxin.doingdaily.module.zhihu;

import android.support.v4.app.Fragment;
import android.view.View;

import com.ganxin.doingdaily.common.data.model.WechatCategory;
import com.ganxin.doingdaily.framework.BaseFragment;
import com.ganxin.doingdaily.framework.ITabFragment;

import java.util.List;

/**
 * Description : 知乎界面  <br/>
 * author : WangGanxin <br/>
 * date : 2017/07/10 <br/>
 * email : mail@wangganxin.me <br/>
 */
public class ZhihuFragment extends BaseFragment<ZhihuContract.View,ZhihuContract.Presenter> implements ZhihuContract.View,ITabFragment {

    @Override
    public Fragment getFragment() {
        return this;
    }

    @Override
    public void addTabs(List<WechatCategory.ShowapiResBodyBean.TypeListBean> typeList) {

    }

    @Override
    public int setContentLayout() {
        return 0;
    }

    @Override
    public void setUpView(View view) {

    }

    @Override
    protected ZhihuContract.Presenter setPresenter() {
        return new ZhihuPresenter();
    }
}
