package com.ganxin.doingdaily.module;

import com.avos.avoscloud.feedback.FeedbackAgent;
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
        //在用户打开App时，通知用户新的反馈回复
        FeedbackAgent agent = new FeedbackAgent(getView().getContext());
        agent.sync();
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
