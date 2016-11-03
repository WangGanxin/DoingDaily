package com.ganxin.doingdaily.module.home;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.ganxin.doingdaily.R;
import com.ganxin.doingdaily.common.constants.ConstantValues;
import com.ganxin.doingdaily.common.data.model.News;
import com.ganxin.doingdaily.common.network.NetworkManager;
import com.ganxin.doingdaily.common.utils.LogUtil;
import com.ganxin.doingdaily.framework.BaseActivity;
import com.ganxin.doingdaily.module.loading.LoadingActivity;

import butterknife.BindView;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Description : MainActivity  <br/>
 * author : WangGanxin <br/>
 * date : 2016/10/9 <br/>
 * email : ganxinvip@163.com <br/>
 */
public class MainActivity extends BaseActivity<MainContract.View, MainContract.Presenter> implements MainContract.View {

    @BindView(R.id.mBtn)
    Button mBtn;

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
    public void showToast() {

    }

    @Override
    public void hideLoading() {

    }

    @OnClick(R.id.mBtn)
    public void onClick() {
        LogUtil.i("-------btn click");
        getNewsForRx();
    }

    private void getNewsForRx() {

        NetworkManager.getAPI().getNews().subscribeOn(Schedulers.newThread())//子线程访问网络
                .observeOn(AndroidSchedulers.mainThread())//回调到主线程
                .subscribe(new Action1<News>() {
                    @Override
                    public void call(News news) {
                        LogUtil.i("222--total----"+news.getShowapi_res_body().getTotalNum());
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        LogUtil.i("----on error--"+throwable.getMessage());
                    }
                });

    }
}
