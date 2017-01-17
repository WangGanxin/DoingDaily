package com.ganxin.doingdaily.module.news;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.ganxin.doingdaily.R;
import com.ganxin.doingdaily.common.data.model.NewsChannel;
import com.ganxin.doingdaily.framework.BaseFragment;
import com.ganxin.doingdaily.framework.ITabFragment;
import com.ganxin.doingdaily.module.news.list.NewsListFragment;

import java.util.List;

import butterknife.BindView;

/**
 * Description : 新闻界面  <br/>
 * author : WangGanxin <br/>
 * date : 2016/11/3 <br/>
 * email : ganxinvip@163.com <br/>
 */
public class NewsFragment extends BaseFragment<NewsContract.View, NewsContract.Presenter> implements NewsContract.View, ITabFragment {

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
    public void addTabs(final List<NewsChannel.ShowapiResBodyBean.ChannelListBean> channelList) {

        if(channelList!=null&&channelList.size()>0){
            viewPager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
                @Override
                public Fragment getItem(int position) {
                    return NewsListFragment.newInstance(channelList.get(position).getChannelId());
                }

                @Override
                public int getCount() {
                    return channelList.size();
                }

                @Override
                public CharSequence getPageTitle(int position) {
                    return channelList.get(position).getName();
                }
            });

            mTopTabLayout.setupWithViewPager(viewPager);
        }
    }
}
