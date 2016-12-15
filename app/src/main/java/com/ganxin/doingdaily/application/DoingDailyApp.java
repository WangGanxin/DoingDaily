package com.ganxin.doingdaily.application;

import android.app.Application;
import android.content.res.Configuration;

import com.ganxin.doingdaily.common.constants.ConstantValues;
import com.ganxin.doingdaily.common.utils.AppStatusTracker;
import com.orhanobut.logger.Logger;

/**
 * Description : DoingDailyApp  <br/>
 * author : WangGanxin <br/>
 * date : 2016/10/9 <br/>
 * email : ganxinvip@163.com <br/>
 */
public class DoingDailyApp extends Application {
    private static DoingDailyApp INSTANCE;

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE=this;
        initAppTracker();
        initLog();

    }

    public static DoingDailyApp getInstance() {
        return INSTANCE;
    }

    private void initAppTracker() {
        AppStatusTracker.init(this);
    }

    private void initLog() {
        Logger.init(ConstantValues.DEBUG_TAG);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }
}
