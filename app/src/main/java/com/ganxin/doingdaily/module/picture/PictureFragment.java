package com.ganxin.doingdaily.module.picture;

import android.support.v4.app.Fragment;
import android.view.View;

import com.ganxin.doingdaily.R;
import com.ganxin.doingdaily.framework.BaseFragment;
import com.ganxin.doingdaily.framework.ITabFragment;
import com.ganxin.doingdaily.module.zhihu.list.ZhihuListFragment;

/**
 * Description : 美图界面  <br/>
 * author : WangGanxin <br/>
 * date : 2017/07/10 <br/>
 * email : mail@wangganxin.me <br/>
 */
public class PictureFragment extends BaseFragment<PictureContract.View, PictureContract.Presenter> implements PictureContract.View, ITabFragment {

    @Override
    public Fragment getFragment() {
        return this;
    }

    @Override
    public int setContentLayout() {
        return R.layout.fragment_zhihu;
    }

    @Override
    public void setUpView(View view) {

        ZhihuListFragment fragment=ZhihuListFragment.newInstance();

        if (fragment != null) {
            getChildFragmentManager().beginTransaction()
                    .add(R.id.mContainerLayout,fragment,ZhihuListFragment.class.getSimpleName())
                    .commitAllowingStateLoss();
        }
    }

    @Override
    protected PictureContract.Presenter setPresenter() {
        return new PicturePresenter();
    }
}
