package com.ganxin.doingdaily.common.constants;

/**
 * Description : 常量类  <br/>
 * author : WangGanxin <br/>
 * date : 2016/10/9 <br/>
 * email : ganxinvip@163.com <br/>
 */
public class ConstantValues {
    public static final String DEBUG_TAG = "DoingDaily";

    public static final String BASE_URL = "http://route.showapi.com/"; // HOST地址:易源接口
    public static final String APP_ID = "29706"; // 应用ID
    public static final String APP_SECRET = "5d9f976ed7ba4ba78310ed6f4a717ee7"; // 应用密钥
    public static final boolean HTTP_CACHE_ENABLE = false; // 是否开启OkHTTP缓存
    public static final String HTTP_CACHE_DIR = "httpcache"; // OkHTTP请求缓存目录名称
    public static final int HTTP_CACHE_TIME = 60 * 60 * 72; // OkHTTP请求缓存时间：72小时
    public static final int HTTP_CACHE_MAXSIZE = 1024 * 1024 * 10; // OkHTTP请求缓存大小：10MB

    public static final int STATUS_FORCE_KILLED = -1;
    public static final int STATUS_LOGOUT = 0;
    public static final int STATUS_OFFLINE = 1;
    public static final int STATUS_ONLINE = 2;
    public static final int STATUS_KICK_OUT = 3;

    public static final int ACTION_BACK_TO_HOME = 0;
    public static final int ACTION_RESTART_APP = 1;
    public static final int ACTION_LOGOUT = 2;
    public static final int ACTION_KICK_OUT = 3;

    public static final String KEY_HOME_ACTION = "key_home_action";
}
