package com.ganxin.doingdaily.module.main.wechat;

import android.content.Context;
import android.view.View;

import com.ganxin.doingdaily.R;
import com.ganxin.doingdaily.framework.BaseFragment;
import com.ganxin.doingdaily.framework.ITabFragment;

/**
 * Description : 微信界面  <br/>
 * author : WangGanxin <br/>
 * date : 2016/11/3 <br/>
 * email : ganxinvip@163.com <br/>
 */
public class WechatFragment  extends BaseFragment<WechatContract.View,WechatContract.Presenter> implements WechatContract.View,ITabFragment {

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        enableLazyLoad();
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
}
