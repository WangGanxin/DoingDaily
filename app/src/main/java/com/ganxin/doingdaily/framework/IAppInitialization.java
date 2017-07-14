package com.ganxin.doingdaily.framework;

import android.app.Application;

/**
 * Description : Application初始化接口  <br/>
 * author : WangGanxin <br/>
 * date : 2017/07/14 <br/>
 * email : mail@wangganxin.me <br/>
 */
public interface IAppInitialization {

    void onAppCreate(Application application);
}
