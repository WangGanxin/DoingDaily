package com.ganxin.doingdaily.module.picture;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.ganxin.doingdaily.R;
import com.ganxin.doingdaily.common.constants.ConstantValues;
import com.ganxin.doingdaily.framework.BaseFragment;
import com.ganxin.doingdaily.framework.ITabFragment;
import com.ganxin.doingdaily.module.picture.list.PictureListFragment;

import butterknife.BindView;

/**
 * Description : 美图界面  <br/>
 * author : WangGanxin <br/>
 * date : 2017/07/10 <br/>
 * email : mail@wangganxin.me <br/>
 */
public class PictureFragment extends BaseFragment<PictureContract.View, PictureContract.Presenter> implements PictureContract.View, ITabFragment {

    @BindView(R.id.mTopTabLayout)
    TabLayout mTopTabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    @Override
    public Fragment getFragment() {
        return this;
    }

    @Override
    public int setContentLayout() {
        return R.layout.fragment_picture;
    }

    @Override
    public void setUpView(View view) {

        viewPager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0:
                        return PictureListFragment.newInstance(ConstantValues.PICTURE_TAB_GANK);
                    case 1:
                        return PictureListFragment.newInstance(ConstantValues.PICTURE_TAB_SHOW);
                    default:
                        return null;
                }
            }

            @Override
            public int getCount() {
                return getResources().getStringArray(R.array.tab_pictures).length;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return getResources().getStringArray(R.array.tab_pictures)[position];
            }
        });

        mTopTabLayout.setTabMode(TabLayout.MODE_FIXED);
        mTopTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        mTopTabLayout.setupWithViewPager(viewPager);

    }

    @Override
    protected PictureContract.Presenter setPresenter() {
        return new PicturePresenter();
    }
}
