package com.ganxin.doingdaily.application;

import android.app.Application;
import android.content.Context;

import com.avos.avoscloud.AVAnalytics;
import com.avos.avoscloud.AVOSCloud;
import com.ganxin.doingdaily.BuildConfig;
import com.ganxin.doingdaily.R;
import com.ganxin.doingdaily.common.constants.ConstantValues;
import com.ganxin.doingdaily.common.data.source.CommonRepository;
import com.ganxin.doingdaily.common.data.source.NewsRepository;
import com.ganxin.doingdaily.common.data.source.WechatRepository;
import com.ganxin.doingdaily.common.data.source.ZhihuRepository;
import com.ganxin.doingdaily.common.data.source.local.CommonLocalDataSource;
import com.ganxin.doingdaily.common.data.source.local.NewsLocalDataSource;
import com.ganxin.doingdaily.common.data.source.local.WechatLocalDataSource;
import com.ganxin.doingdaily.common.data.source.local.ZhihuLocalDataSource;
import com.ganxin.doingdaily.common.data.source.remote.CommonRemoteDataSource;
import com.ganxin.doingdaily.common.data.source.remote.NewsRemoteDataSource;
import com.ganxin.doingdaily.common.data.source.remote.WechatRemoteDataSource;
import com.ganxin.doingdaily.common.data.source.remote.ZhihuRemoteDataSource;
import com.ganxin.doingdaily.framework.IAppInitialization;
import com.ganxin.doingdaily.module.MainActivity;
import com.orhanobut.logger.Logger;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

/**
 * Description : 主进程初始化  <br/>
 * author : WangGanxin <br/>
 * date : 2017/07/14 <br/>
 * email : mail@wangganxin.me <br/>
 */
public class DoingDailyMainInit implements IAppInitialization {

    private Context appContext;

    @Override
    public void onAppCreate(Application application) {

        appContext = application.getApplicationContext();

        initLog();
        initRepository();
        initLeanCloud();
        initBugly();
        initUmengShare();
    }

    private void initLog() {
        Logger.init(ConstantValues.DEBUG_TAG);
    }

    private void initRepository() {
        NewsRepository.initialize(NewsRemoteDataSource.getInstance(), NewsLocalDataSource.getInstance(appContext));
        WechatRepository.initialize(WechatRemoteDataSource.getInstance(), WechatLocalDataSource.getInstance(appContext));
        ZhihuRepository.initialize(ZhihuRemoteDataSource.getInstance(), ZhihuLocalDataSource.getInstance(appContext));
        CommonRepository.initialize(CommonRemoteDataSource.getInstance(), CommonLocalDataSource.getInstance(appContext));
    }

    private void initLeanCloud() {
        AVOSCloud.initialize(appContext, ConstantValues.LEANCLOUD_ID, ConstantValues.LEANCLOUD_KEY);
        AVAnalytics.enableCrashReport(appContext, true);
        AVOSCloud.setDebugLogEnabled(BuildConfig.DEBUG);
    }

    private void initBugly() {
        Beta.autoInit = true;
        Beta.autoCheckUpgrade = true;
        Beta.showInterruptedStrategy = true;
        Beta.initDelay = 8 * 1000;
        Beta.canShowUpgradeActs.add(MainActivity.class);
        Beta.smallIconId = R.drawable.ic_notification_logo;
        Bugly.init(appContext, ConstantValues.BUGLY_ID, BuildConfig.DEBUG);
        //CrashReport.testJavaCrash();
    }

    private void initUmengShare() {
        PlatformConfig.setWeixin(ConstantValues.WECHAT_ID, ConstantValues.WECHAT_SECRET);
        PlatformConfig.setSinaWeibo(ConstantValues.SINA_KEY, ConstantValues.SINA_SECRET);
        PlatformConfig.setQQZone(ConstantValues.TENCENT_ID, ConstantValues.TENCENT_SECRET);
        Config.REDIRECT_URL = ConstantValues.SINA_REDIRECT_URL;
        Config.DEBUG = BuildConfig.DEBUG;
        Config.isJumptoAppStore = true;
        UMShareAPI.get(appContext);
    }
}
