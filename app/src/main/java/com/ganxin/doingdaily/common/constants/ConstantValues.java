package com.ganxin.doingdaily.common.constants;

import com.ganxin.doingdaily.BuildConfig;

/**
 * Description : 常量类  <br/>
 * author : WangGanxin <br/>
 * date : 2016/10/9 <br/>
 * email : ganxinvip@163.com <br/>
 */
public class ConstantValues {

    //全局配置
    public static final String DEBUG_TAG = "DoingDaily";

    public static final String APP_ID = "29706"; // 应用ID
    public static final String APP_SECRET = "5d9f976ed7ba4ba78310ed6f4a717ee7"; // 应用密钥
    public static final String BASE_URL_SHOWAPI = "http://route.showapi.com/"; // HOST地址:易源接口
    public static final String BASE_URL_ZHIHU = "http://news-at.zhihu.com/api/4/"; // HOST地址:知乎接口
    public static final String BASE_URL_GANK = "http://gank.io/api/data/"; // HOST地址:干货集中营接口
    public static final String GITHUB_URL = "https://github.com/WangGanxin/DoingDaily"; // GitHub项目地址
    public static final String BUGLY_URL = "https://beta.bugly.qq.com/doingdaily"; // Bugly下载地址

    //Leancloud
    public static final String LEANCLOUD_ID ="lBIrjjfV0Q0xor6nzCOj7rAF-gzGzoHsz";
    public static final String LEANCLOUD_KEY ="0y6q2YwSQQxFex28L0u6pkhh";

    //Bugly
    public static final String BUGLY_ID ="ca044d3a78";

    //wechat
    public static final String WECHAT_ID ="wxc120184ca7a52698";
    public static final String WECHAT_SECRET ="22fbbb573949d6fd1196033f2f65beb2";

    //sina
    public static final String SINA_KEY ="3856921794";
    public static final String SINA_SECRET ="f4969e4a465f23a011457d66d9fe9016";
    public static final String SINA_REDIRECT_URL ="http://sns.whalecloud.com/sina2/callback";

    //QQ Qzone
    public static final String TENCENT_ID ="1105991688";
    public static final String TENCENT_SECRET ="Y5HoDstgwerey0MJ";

    //http请求配置
    public static final boolean HTTP_CACHE_ENABLE =!BuildConfig.DEBUG; // 是否开启OkHTTP缓存
    public static final String HTTP_CACHE_DIR = "httpcache"; // OkHTTP请求缓存目录名称
    public static final int HTTP_CACHE_TIME = 60 * 60 * 72; // OkHTTP请求缓存时间：72小时
    public static final int HTTP_CACHE_MAXSIZE = 1024 * 1024 * 10; // OkHTTP请求缓存大小：10MB

    //app状态
    public static final int STATUS_FORCE_KILLED = -1;
    public static final int STATUS_LOGOUT = 0;
    public static final int STATUS_OFFLINE = 1;
    public static final int STATUS_ONLINE = 2;
    public static final int STATUS_KICK_OUT = 3;

    public static final int ACTION_BACK_TO_HOME = 0;
    public static final int ACTION_RESTART_APP = 1;
    public static final int ACTION_LOGOUT = 2;
    public static final int ACTION_KICK_OUT = 3;

    //参数key
    public static final String KEY_HOME_ACTION = "key_home_action";
    public static final String KEY_CHANNEL_ID = "key_channel_id";
    public static final String KEY_CATEGORY_ID = "key_category_id";
    public static final String KEY_VIEW_TYPE = "key_view_type";
    public static final String KEY_BEAN = "key_bean";
    public static final String KEY_ZHIHU_ARTICLE_TITLE = "key_zhihu_article_title";
    public static final String KEY_ZHIHU_ARTICLE_ID = "key_zhihu_article_id";
    public static final String KEY_ZHIHU_ARTICLE_IMAGE = "key_zhihu_article_image";
    public static final String KEY_PICTURE_TAB = "key_picture_tab";

    public static final int VIEW_TYPE_TXT = 10;
    public static final int VIEW_TYPE_IMAGE = 11;

    public static final int VIEW_ZHIHU_BANNER = 20;
    public static final int VIEW_ZHIHU_SUMMARY = 21;
    public static final int VIEW_ZHIHU_DATE = 22;

    public static final int PICTURE_TAB_SHOW = 30;
    public static final int PICTURE_TAB_GANK = 31;

    public static final String SHARE_IMAGE="share_image";
}
