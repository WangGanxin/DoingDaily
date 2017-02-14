package com.ganxin.doingdaily.application;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;

import com.ganxin.doingdaily.BuildConfig;
import com.ganxin.doingdaily.R;
import com.ganxin.doingdaily.common.constants.ConstantValues;
import com.ganxin.doingdaily.common.data.source.NewsRepository;
import com.ganxin.doingdaily.common.data.source.WechatRepository;
import com.ganxin.doingdaily.common.data.source.local.NewsLocalDataSource;
import com.ganxin.doingdaily.common.data.source.local.WechatLocalDataSource;
import com.ganxin.doingdaily.common.data.source.remote.NewsRemoteDataSource;
import com.ganxin.doingdaily.common.data.source.remote.WechatRemoteDataSource;
import com.ganxin.doingdaily.common.utils.AppStatusTracker;
import com.ganxin.doingdaily.module.MainActivity;
import com.maxleap.MaxLeap;
import com.orhanobut.logger.Logger;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;

/**
 * Description : DoingDailyApp  <br/>
 * author : WangGanxin <br/>
 * date : 2016/10/9 <br/>
 * email : ganxinvip@163.com <br/>
 */
public class DoingDailyApp extends Application {
    private static Context appContext;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext=getApplicationContext();
        initAppTracker();
        initLog();
        initRepository();
        initMaxLeap();
        initBugly();
    }

    public static Context getAppContext() {
        return appContext;
    }

    private void initAppTracker() {
        AppStatusTracker.init(this);
    }

    private void initLog() {
        Logger.init(ConstantValues.DEBUG_TAG);
    }

    private void initRepository() {
        NewsRepository.initialize(NewsRemoteDataSource.getInstance(),NewsLocalDataSource.getInstance(this));
        WechatRepository.initialize(WechatRemoteDataSource.getInstance(),WechatLocalDataSource.getInstance(this));
    }

    private void initMaxLeap() {

        MaxLeap.Options options = new MaxLeap.Options();
        options.applicationID ="58807fe20d55f900076a4d27";
        options.restAPIKey = "TFJqaGJkNFdQcWxsX2VWM3RQWEVXQQ";
        options.serverRegion = MaxLeap.REGION_CN;
        options.analyticsEnable=true; //开启数据统计
        options.sessionContinueTime = 30 * 1000; //追踪会话时长

        MaxLeap.initialize(this, options);

        //MaxLeap.checkSDKConnection(); 测试当前sdk的appid和restapikey配置是否正确。正式环境可移除。
    }

    private void initBugly() {
        Beta.autoInit=true;
        Beta.autoCheckUpgrade=true;
        Beta.showInterruptedStrategy=true;
        Beta.initDelay = 8 * 1000;
        Beta.canShowUpgradeActs.add(MainActivity.class);
        Beta.smallIconId= R.drawable.ic_notification_logo;
        Bugly.init(appContext, "ca044d3a78", BuildConfig.DEBUG);
        //CrashReport.testJavaCrash();
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
