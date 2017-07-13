package com.ganxin.doingdaily.framework;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;

import com.ganxin.doingdaily.R;
import com.ganxin.doingdaily.common.utils.ActivityUtils;
import com.ganxin.doingdaily.common.utils.LogUtil;

/**
 * Description : BaseActivity  <br/>
 * author : WangGanxin <br/>
 * date : 2016/10/9 <br/>
 * email : ganxinvip@163.com <br/>
 */
public class BaseContainerActivity extends BaseActivity {

    private static String TAG = "BaseContainerActivity";
    private static BaseFragment baseFragment;

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.activity_container);
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void initView() {
        if (baseFragment == null) {
            LogUtil.logE(TAG, "create baseFragment is null !");
            finish();
            return;
        }
        addFragment(baseFragment);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    public static void setFragment(BaseFragment fragment) {
        baseFragment = fragment;
    }

    public void addFragment(Fragment fragment) {
        ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), fragment, R.id.contentFrame);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (baseFragment != null) {
            baseFragment.onKeyDown(keyCode,event);
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        baseFragment = null;
        super.onDestroy();
    }
}
