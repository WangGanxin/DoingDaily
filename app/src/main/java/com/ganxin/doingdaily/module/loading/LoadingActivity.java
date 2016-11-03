package com.ganxin.doingdaily.module.loading;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.ganxin.doingdaily.R;
import com.ganxin.doingdaily.common.constants.ConstantValues;
import com.ganxin.doingdaily.common.utils.AppStatusTracker;
import com.ganxin.doingdaily.framework.BaseActivity;
import com.ganxin.doingdaily.module.home.MainActivity;

/**
 * Description : 启动界面  <br/>
 * author : WangGanxin <br/>
 * date : 2016/10/9 <br/>
 * email : ganxinvip@163.com <br/>
 */
public class LoadingActivity extends BaseActivity<LoadingContract.View,LoadingContract.Presenter> implements LoadingContract.View{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppStatusTracker.getInstance().setAppStatus(ConstantValues.STATUS_OFFLINE);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.activity_loading);
    }

    @Override
    protected LoadingContract.Presenter createPresenter() {
        return new LoadingPresenter();
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        handler.sendEmptyMessageDelayed(0, 2000);
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            startActivity(new Intent(LoadingActivity.this, MainActivity.class));
            finish();
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeMessages(0);
    }

    @Override
    public void showToast() {

    }

    @Override
    public void hideLoading() {

    }
}
