package com.ganxin.doingdaily.module.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;

import com.ganxin.doingdaily.R;
import com.ganxin.doingdaily.common.constants.ConstantValues;
import com.ganxin.doingdaily.common.data.model.News;
import com.ganxin.doingdaily.common.network.NetworkManager;
import com.ganxin.doingdaily.common.utils.LogUtil;
import com.ganxin.doingdaily.common.widgets.tab.TabLayout;
import com.ganxin.doingdaily.framework.BaseActivity;
import com.ganxin.doingdaily.framework.ITabFragment;
import com.ganxin.doingdaily.module.loading.LoadingActivity;
import com.ganxin.doingdaily.module.main.about.AboutFragment;
import com.ganxin.doingdaily.module.main.news.NewsFragment;
import com.ganxin.doingdaily.module.main.wechat.WechatFragment;

import java.util.ArrayList;

import butterknife.BindView;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Description : MainActivity  <br/>
 * author : WangGanxin <br/>
 * date : 2016/10/9 <br/>
 * email : ganxinvip@163.com <br/>
 */
public class MainActivity extends BaseActivity<MainContract.View, MainContract.Presenter> implements MainContract.View, TabLayout.OnTabClickListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.appBarLayout)
    AppBarLayout appBarLayout;
    @BindView(R.id.mFragmentContainerLayout)
    FrameLayout mFragmentContainerLayout;
    @BindView(R.id.mTabLayout)
    TabLayout mTabLayout;
    private ITabFragment currentFragment;

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.activity_main);
    }

    @Override
    protected MainContract.Presenter createPresenter() {
        return new MainPresenter();
    }

    @Override
    protected void initView() {
        //toolbar设置
        setSupportActionBar(toolbar);

        //添加tab（因为tab里的Fragment的添加方式不是预先加入Layout容器内，无需开启Fragment的懒加载）
        ArrayList<TabLayout.Tab> tabs = new ArrayList<>();
        tabs.add(new TabLayout.Tab(R.drawable.ic_bottomtabbar_news, R.string.tab_news, NewsFragment.class));
        tabs.add(new TabLayout.Tab(R.drawable.ic_bottomtabbar_wechat, R.string.tab_wechat, WechatFragment.class));
        tabs.add(new TabLayout.Tab(R.drawable.ic_bottomtabbar_about, R.string.tab_about, AboutFragment.class));
        mTabLayout.setUpData(tabs, this);
        mTabLayout.setCurrentTab(0);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        int action = intent.getIntExtra(ConstantValues.KEY_HOME_ACTION, ConstantValues.ACTION_BACK_TO_HOME);
        switch (action) {
            case ConstantValues.ACTION_KICK_OUT:
                break;
            case ConstantValues.ACTION_LOGOUT:
                break;
            case ConstantValues.ACTION_RESTART_APP:
                protectApp();
                break;
            case ConstantValues.ACTION_BACK_TO_HOME:
                break;
        }
    }

    @Override
    protected void protectApp() {
        startActivity(new Intent(this, LoadingActivity.class));
        finish();
    }

    @Override
    public void onTabClick(TabLayout.Tab tab) {
        try {
            setTitle(tab.labelResId);

            ITabFragment tmpFragment = (ITabFragment) getSupportFragmentManager().findFragmentByTag(tab.targetFragmentClz.getSimpleName());
            if (tmpFragment == null) {
                tmpFragment = tab.targetFragmentClz.newInstance();
                if (currentFragment == null) {
                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.mFragmentContainerLayout, tmpFragment.getFragment(), tab.targetFragmentClz.getSimpleName())
                            .commit();
                } else {
                    getSupportFragmentManager().beginTransaction()
                            .hide(currentFragment.getFragment())
                            .add(R.id.mFragmentContainerLayout, tmpFragment.getFragment(), tab.targetFragmentClz.getSimpleName())
                            .commit();
                }
            } else {
                if(currentFragment ==null){
                    getSupportFragmentManager().beginTransaction()
                            .show(tmpFragment.getFragment())
                            .commit();
                }
                else{
                    getSupportFragmentManager().beginTransaction()
                            .hide(currentFragment.getFragment())
                            .show(tmpFragment.getFragment())
                            .commit();
                }
            }
            currentFragment = tmpFragment;

        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    protected void setUpTitle(int titleResId) {
        if (titleResId > 0 && toolbar != null) {
            toolbar.setTitle(titleResId);
        }
    }

    private void getNewsForRx() {

        NetworkManager.getAPI().getNews().subscribeOn(Schedulers.newThread())//子线程访问网络
                .observeOn(AndroidSchedulers.mainThread())//回调到主线程
                .subscribe(new Action1<News>() {
                    @Override
                    public void call(News news) {
                        LogUtil.i("222--total----" + news.getShowapi_res_body().getTotalNum());
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        LogUtil.i("----on error--" + throwable.getMessage());
                    }
                });

    }
}
