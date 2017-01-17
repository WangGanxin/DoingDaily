package com.ganxin.doingdaily.module.wechat;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.ganxin.doingdaily.R;
import com.ganxin.doingdaily.common.data.model.WechatCategory;
import com.ganxin.doingdaily.framework.BaseFragment;
import com.ganxin.doingdaily.framework.ITabFragment;
import com.ganxin.doingdaily.module.wechat.list.WechatListFragment;

import java.util.List;

import butterknife.BindView;

/**
 * Description : 微信界面  <br/>
 * author : WangGanxin <br/>
 * date : 2016/11/3 <br/>
 * email : ganxinvip@163.com <br/>
 */
public class WechatFragment  extends BaseFragment<WechatContract.View,WechatContract.Presenter> implements WechatContract.View,ITabFragment {

    @BindView(R.id.mTopTabLayout)
    TabLayout mTopTabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public int setContentLayout() {
        return R.layout.fragment_wechat;
    }

    @Override
    public void setUpView(View view) {

    }

    @Override
    protected WechatContract.Presenter setPresenter() {
        return new WechatPresenter();
    }

    @Override
    public BaseFragment getFragment() {
        return this;
    }

    @Override
    public void addTabs(final List<WechatCategory.ShowapiResBodyBean.TypeListBean> typeList) {
        if(typeList!=null&&typeList.size()>0){
            viewPager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
                @Override
                public Fragment getItem(int position) {
                    return WechatListFragment.newInstance(typeList.get(position).getId());
                }

                @Override
                public int getCount() {
                    return typeList.size();
                }

                @Override
                public CharSequence getPageTitle(int position) {
                    return typeList.get(position).getName();
                }
            });

            mTopTabLayout.setupWithViewPager(viewPager);
        }
    }
}
