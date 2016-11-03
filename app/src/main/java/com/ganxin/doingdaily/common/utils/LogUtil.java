package com.ganxin.doingdaily.common.utils;


import android.util.Log;

import com.ganxin.doingdaily.BuildConfig;
import com.ganxin.doingdaily.common.constants.ConstantValues;

/**
 *
 * Description : log日志打印工具类  <br/>
 * author : WangGanxin <br/>
 * date : 2016/9/5 <br/>
 * email : ganxinvip@163.com <br/>
 */
public class LogUtil {

    public static boolean DEBUG = BuildConfig.DEBUG;
    public static final String TAG = ConstantValues.DEBUG_TAG;

    private LogUtil() {}

    public static void setDebug(boolean debug) {
        LogUtil.DEBUG = debug;
    }

    public static void d(String msg) {
        if (DEBUG) {
            Log.d(TAG, msg);
        }
    }

    public static void i(String msg) {
        if (DEBUG) {
            Log.i(TAG, msg);
        }
    }

    public static void w(String msg) {
        if (DEBUG) {
            Log.w(TAG, msg);
        }
    }

    public static void e(String msg) {
        if (DEBUG) {
            Log.e(TAG, msg);
        }
    }

    /******************************************************/

    public static void logV(String tag, String msg) {
        if (DEBUG) {
            Log.v(tag, msg);
        }
    }

    public static void logD(String tag, String msg) {
        if (DEBUG) {
            Log.d(tag, msg);
        }
    }

    public static void logI(String tag, String msg) {
        if (DEBUG) {
            Log.i(tag, msg);
        }
    }

    public static void logW(String tag, String msg) {
        if (DEBUG) {
            Log.w(tag, msg);
        }
    }

    public static void logE(String tag, String msg) {
        if (DEBUG) {
            Log.e(tag, msg);
        }
    }

    public static void logWTF(String tag, String msg) {
        if (DEBUG) {
            Log.wtf(tag, msg);
        }
    }
}
