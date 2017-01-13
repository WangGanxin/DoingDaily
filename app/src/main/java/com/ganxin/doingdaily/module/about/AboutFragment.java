package com.ganxin.doingdaily.module.about;

import android.view.View;

import com.ganxin.doingdaily.R;
import com.ganxin.doingdaily.framework.BaseFragment;
import com.ganxin.doingdaily.framework.ITabFragment;

/**
 * Description : 关于界面  <br/>
 * author : WangGanxin <br/>
 * date : 2016/11/3 <br/>
 * email : ganxinvip@163.com <br/>
 */
public class AboutFragment  extends BaseFragment<AboutContract.View,AboutContract.Presenter> implements AboutContract.View,ITabFragment {

    @Override
    public int setContentLayout() {
        return R.layout.fragment_about;
    }

    @Override
    public void setUpView(View view) {

    }

    @Override
    protected AboutContract.Presenter setPresenter() {
        return new AboutPresenter();
    }

    @Override
    public BaseFragment getFragment() {
        return this;
    }
}
