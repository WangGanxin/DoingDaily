package com.ganxin.doingdaily.module.main.news;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.ganxin.doingdaily.R;
import com.ganxin.doingdaily.common.data.model.News;
import com.ganxin.doingdaily.framework.BaseFragment;
import com.ganxin.doingdaily.framework.ITabFragment;

import java.util.List;

import butterknife.BindView;

/**
 * Description : 新闻界面  <br/>
 * author : WangGanxin <br/>
 * date : 2016/11/3 <br/>
 * email : ganxinvip@163.com <br/>
 */
public class NewsFragment extends BaseFragment<NewsContract.View, NewsContract.Presenter> implements NewsContract.View,ITabFragment {

    @BindView(R.id.mTopTabLayout)
    TabLayout mTopTabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    @Override
    public int setContentLayout() {
        return R.layout.fragment_news;
    }

    @Override
    public void setUpView(View view) {
        int i=10;

        for (int j = 0; j < i; j++) {
            mTopTabLayout.addTab(mTopTabLayout.newTab().setText("焦点"));
        }
    }

    @Override
    protected NewsContract.Presenter setPresenter() {
        return new NewsPresenter();
    }

    @Override
    public BaseFragment getFragment() {
        return this;
    }

    @Override
    public void addTabs(List<News.ShowapiResBodyBean.ChannelListBean> channelList) {

    }
}
