package com.ganxin.doingdaily.module.main.news;

import android.content.Context;
import android.view.View;

import com.ganxin.doingdaily.R;
import com.ganxin.doingdaily.framework.BaseFragment;
import com.ganxin.doingdaily.framework.ITabFragment;

/**
 * Description : 新闻界面  <br/>
 * author : WangGanxin <br/>
 * date : 2016/11/3 <br/>
 * email : ganxinvip@163.com <br/>
 */
public class NewsFragment extends BaseFragment<NewsContract.View, NewsContract.Presenter> implements NewsContract.View, ITabFragment {

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        enableLazyLoad();
    }

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

}
