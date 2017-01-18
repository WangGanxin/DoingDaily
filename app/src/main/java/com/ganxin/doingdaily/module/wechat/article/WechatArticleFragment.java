package com.ganxin.doingdaily.module.wechat.article;

import android.view.View;

import com.ganxin.doingdaily.R;
import com.ganxin.doingdaily.framework.BaseFragment;

/**
 * Description : 微信详情页面  <br/>
 * author : WangGanxin <br/>
 * date : 2017/1/17 <br/>
 * email : mail@wangganxin.me <br/>
 */
public class WechatArticleFragment extends BaseFragment<WechatArticleContract.View,WechatArticleContract.Presenter> implements WechatArticleContract.View{

    @Override
    public int setContentLayout() {
        return R.layout.fragment_wechat_article;
    }

    @Override
    public void setUpView(View view) {

    }

    @Override
    protected WechatArticleContract.Presenter setPresenter() {
        return null;
    }
}
