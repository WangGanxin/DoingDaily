package com.ganxin.doingdaily.module;

import com.ganxin.doingdaily.R;

/**
 * Description : MainPresenter  <br/>
 * author : WangGanxin <br/>
 * date : 2016/10/28 <br/>
 * email : ganxinvip@163.com <br/>
 */
public class MainPresenter extends MainContract.Presenter {

    private long currentTime;

    @Override
    public void onStart() {

    }

    @Override
    public void exitApp() {
        if (System.currentTimeMillis() - currentTime < 2 * 1000) {
            getView().finishView();
        } else {
            currentTime = System.currentTimeMillis();
            getView().showSnackBar(R.string.main_exit_app);
        }
    }
}
