package com.ganxin.doingdaily.common.utils;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.ganxin.doingdaily.common.constants.ConstantValues;

/**
 * Description : App状态跟踪  <br/>
 * author : WangGanxin <br/>
 * date : 2016/10/9 <br/>
 * email : ganxinvip@163.com <br/>
 */
public class AppStatusTracker implements Application.ActivityLifecycleCallbacks {
    private Application application;
    private static AppStatusTracker tracker;

    private int mAppStatus = ConstantValues.STATUS_FORCE_KILLED;
    private int activeCount;
    private boolean isForground;

    private AppStatusTracker(Application application) {
        this.application = application;
        this.application.registerActivityLifecycleCallbacks(this);
    }

    public static void init(Application application) {
        tracker = new AppStatusTracker(application);
    }

    public static AppStatusTracker getInstance() {
        return tracker;
    }

    public void setAppStatus(int status) {
        this.mAppStatus = status;
    }

    public int getAppStatus() {
        return this.mAppStatus;
    }

    public boolean isForground() {
        return isForground;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(Activity activity) {
        activeCount++;
    }

    @Override
    public void onActivityResumed(Activity activity) {
        isForground = true;
    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {
        activeCount--;
        if (activeCount == 0) {
            isForground = false;
        }
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }
}
