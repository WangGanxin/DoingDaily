package com.ganxin.doingdaily.framework;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Description : Rx管理类  <br/>
 * author : WangGanxin <br/>
 * date : 2016/11/2 <br/>
 * email : ganxinvip@163.com <br/>
 */
public class RxManager {
    private CompositeSubscription mCompositeSubscription = new CompositeSubscription();// 管理订阅者者

    public void add(Subscription m) {
        mCompositeSubscription.add(m);
    }

    public void clear() {
        mCompositeSubscription.unsubscribe();// 取消订阅
    }
}
