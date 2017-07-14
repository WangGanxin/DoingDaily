package com.ganxin.doingdaily.application;

import com.ganxin.doingdaily.framework.IAppInitialization;

/**
 * Description : Application工厂类  <br/>
 * author : WangGanxin <br/>
 * date : 2017/07/14 <br/>
 * email : mail@wangganxin.me <br/>
 */
public class AppInitFactory {
    public static IAppInitialization getAppInitialization(boolean isMain, String processName) {
        IAppInitialization appInitialization = null;

        if (isMain) {
            appInitialization = new DoingDailyMainInit();
        } else if (processName.equals("xxxx:push")) {
            //根据processName初始化不同的application
        }

        return appInitialization;
    }
}
