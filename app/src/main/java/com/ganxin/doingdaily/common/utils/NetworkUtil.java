package com.ganxin.doingdaily.common.utils;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import java.lang.reflect.Field;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 *
 * Description : 网络工具类  <br/>
 * author : WangGanxin <br/>
 * date : 2016/9/5 <br/>
 * email : ganxinvip@163.com <br/>
 */
public final class NetworkUtil {

    private NetworkUtil() {
    }

    /**
     * 是否已注册了网络状态变化的广播接收器
     */
    private static final AtomicBoolean isRegistNetworkConnectivityChangeReceiver = new AtomicBoolean(false);
    /**
     * 网络状态变化广播接收器
     */
    private static final BroadcastReceiver networkConnectivityChangeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                if (NETWORKCONNECTIVITYCHANGELISTENERLIST != null && !NETWORKCONNECTIVITYCHANGELISTENERLIST.isEmpty()) {
                    boolean isConnectivity = isNetworkConnectivity(context);
                    for (NetworkConnectivityChangeListener listener : NETWORKCONNECTIVITYCHANGELISTENERLIST) {
                        listener.networkConnectivityChange(isConnectivity);
                    }
                }
            } catch (ConcurrentModificationException e) {
                e.printStackTrace();
            }
        }
    };
    /**
     * 注册的网络状态监听器
     */
    private static final List<NetworkConnectivityChangeListener> NETWORKCONNECTIVITYCHANGELISTENERLIST = new ArrayList<NetworkConnectivityChangeListener>();
    /**
     * 关闭 APN 网络时，添加的 APN 信息后缀
     */
    private static final String APN_SUFFIX = "_close";
    /**
     * 获取 APN 列表的 URI
     */
    private static final Uri APN_LIST_URI = Uri.parse("content://telephony/carriers");
    /**
     * 获取当前使用的 APN 的 URI
     */
    private static final Uri CURRENT_APN_URI = Uri.parse("content://telephony/carriers/preferapn");

    /**
     * 注册网络状态变化监听器
     *
     * @param context  上下文
     * @param listener 网络状态变化监听器
     */
    public static void registNetworkConnectivityChangeListener(Context context, NetworkConnectivityChangeListener listener) {
        synchronized (isRegistNetworkConnectivityChangeReceiver) {
            if (listener != null) {
                if (!NETWORKCONNECTIVITYCHANGELISTENERLIST.contains(listener)) {
                    NETWORKCONNECTIVITYCHANGELISTENERLIST.add(listener);
                    if (!isRegistNetworkConnectivityChangeReceiver.get() && context != null) {
                        isRegistNetworkConnectivityChangeReceiver.set(true);
                        context.getApplicationContext().registerReceiver(networkConnectivityChangeReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
                    }
                }
            }
        }
    }

    /**
     * 反注册网络状态变化监听器
     *
     * @param context  上下文
     * @param listener 网络状态变化监听器
     */
    public static void unRegisterNetworkConnectChangeListener(Context context, NetworkConnectivityChangeListener listener) {
        synchronized (isRegistNetworkConnectivityChangeReceiver) {
            if (NETWORKCONNECTIVITYCHANGELISTENERLIST.contains(listener)) {
                NETWORKCONNECTIVITYCHANGELISTENERLIST.remove(listener);
            }
            if (NETWORKCONNECTIVITYCHANGELISTENERLIST.isEmpty() && isRegistNetworkConnectivityChangeReceiver.get() && context != null) {
                context.getApplicationContext().unregisterReceiver(networkConnectivityChangeReceiver);
                isRegistNetworkConnectivityChangeReceiver.set(false);
            }
        }
    }

    /**
     * 判断当前网络是否已连接
     * 需添加权限：
     * <pre>
     *     android.permission.ACCESS_NETWORK_STATE
     * </pre>
     *
     * @param context 上下文
     * @return true 网络已连接
     */
    public static boolean isNetworkConnectivity(Context context) {
        if (null == context) {
            return false;
        }
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    /**
     * 判断当前联网类型是否为 CTWAP
     *
     * @param context 上下文
     * @return true 表示为 CTWAP 联网，false 表示其他网络类型或未联网
     */
    public static boolean isCTWapConnectivity(Context context) {
        boolean result = false;
        if (is3GConnectivity(context)) {
            result = "ctwap".equalsIgnoreCase(getNetworkType(context));
            if (!result) {
                String currentApn = getCurrentAPN(context);
                result = currentApn != null && currentApn.contains("ctwap");
            }
        }

        return result;
    }

    /**
     * 判断当前联网类型是否为 3G
     *
     * @param context 上下文
     * @return true 表示为 3G 网络，false 表示为 WIFI 或未联网
     */
    public static boolean is3GConnectivity(Context context) {
        boolean result = false;
        if (isNetworkConnectivity(context) && !isWifiConnectivity(context)) {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            switch (telephonyManager.getNetworkType()) {
                case TelephonyManager.NETWORK_TYPE_UMTS:
                case TelephonyManager.NETWORK_TYPE_HSDPA:
                case TelephonyManager.NETWORK_TYPE_HSUPA:
                case TelephonyManager.NETWORK_TYPE_HSPA:
                case TelephonyManager.NETWORK_TYPE_EVDO_0:
                case TelephonyManager.NETWORK_TYPE_EVDO_A:
                case TelephonyManager.NETWORK_TYPE_EVDO_B:
                case TelephonyManager.NETWORK_TYPE_EHRPD:
                case TelephonyManager.NETWORK_TYPE_HSPAP:
                    result = true;
                    break;
                default:
                    result = false;
                    break;
            }
        }
        return result;
    }

    /**
     * 判断当前联网类型是否为 4G
     *
     * @param context 上下文
     * @return true 表示为 4G 网络
     */
    public static boolean is4GConnectivity(Context context) {
        boolean result = false;
        if (isNetworkConnectivity(context) && !isWifiConnectivity(context)) {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            switch (telephonyManager.getNetworkType()) {
                case TelephonyManager.NETWORK_TYPE_LTE:
                    result = true;
                    break;
                default:
                    result = false;
                    break;
            }
        }
        return result;
    }


    /**
     * 是否正在使用3g或者4g网络
     *
     * @param context
     * @return
     */
    public static boolean is3GOr4GNetwork(Context context) {
        if (context == null) {
            return false;
        }
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            if (activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting()) {
                if (activeNetworkInfo.getType() != ConnectivityManager.TYPE_WIFI) {
                    switch (activeNetworkInfo.getSubtype()) {
                        case TelephonyManager.NETWORK_TYPE_LTE: //4G
                        case TelephonyManager.NETWORK_TYPE_UMTS:
                        case TelephonyManager.NETWORK_TYPE_HSDPA:
                        case TelephonyManager.NETWORK_TYPE_HSUPA:
                        case TelephonyManager.NETWORK_TYPE_HSPA:
                        case TelephonyManager.NETWORK_TYPE_EVDO_0:
                        case TelephonyManager.NETWORK_TYPE_EVDO_A:
                        case TelephonyManager.NETWORK_TYPE_EVDO_B:
                        case TelephonyManager.NETWORK_TYPE_EHRPD:
                        case TelephonyManager.NETWORK_TYPE_HSPAP:
                            return true;
                    }
                }
            }
        }
        return false;
    }


    /**
     * 判断当前联网类型是否为 WIFI
     *
     * @param context 上下文
     * @return true 表示为 WIFI 网络，false 表示为 3G 或未联网
     */
    public static boolean isWifiConnectivity(Context context) {
        boolean result = false;
        if (isNetworkConnectivity(context)) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            result = activeNetworkInfo != null && activeNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI;
        }
        return result;
    }

    /**
     * 是否使用移动网络
     *
     * @param context
     * @return
     */
    public static boolean isMobileNetwork(Context context) {
        if (context == null) {
            return false;
        }
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivityManager != null) {
                NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                if (activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting()) {
                    if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 判断是否是飞行模式
     *
     * @param context 上下文
     * @return true 当前处于飞行模式
     */
    public static boolean isAirplaneMode(Context context) {
        return Settings.System.getInt(context.getContentResolver(), Settings.System.AIRPLANE_MODE_ON, 0) != 0;
    }

    /**
     * 判断 APN 是否已关闭
     *
     * @param apn APN 信息
     * @return true APN 已关闭，即失效
     */
    private static boolean isAPNClose(APN apn) {
        return (apn.getApn() != null && apn.getApn().endsWith(APN_SUFFIX)) || (apn.getType() != null && apn.getType().endsWith(APN_SUFFIX))
                || (apn.getUser() != null && apn.getUser().endsWith(APN_SUFFIX));
    }

    /**
     * 获取联网类型
     * 需添加权限：
     * <pre>
     *     android.permission.ACCESS_NETWORK_STATE
     * </pre>
     *
     * @param context 上下文
     * @return 可取值：wifi、ctwap、ctnet。尚未联网或处于飞行模式时，返回 null。
     */
    public static String getNetworkType(Context context) {
        String result = null;
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            if (activeNetworkInfo != null && isNetworkConnectivity(context)) {
                if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                    result = "wifi";
                } else {
                    result = getCurrentAPN(context);

                    if (TextUtils.isEmpty(result)) {
                        if (null != activeNetworkInfo.getExtraInfo()) {
                            result = activeNetworkInfo.getExtraInfo().toLowerCase();
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return result;
        }
        return result;
    }


    /**
     * 获取手机的 IP 地址
     * 需添加权限：
     * <pre>
     *     android.permission.INTERNET
     * </pre>
     *
     * @return 手机的 IP 地址。尚未联网或处于飞行模式时，返回 null
     */
    public static String getIpAddress() {
        String result = null;
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                if (result != null) {
                    break;
                }
                NetworkInterface networkInterface = en.nextElement();
                for (Enumeration<InetAddress> enIpAddress = networkInterface.getInetAddresses(); enIpAddress.hasMoreElements(); ) {
                    InetAddress inetAddress = enIpAddress.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        result = inetAddress.getHostAddress();
                        break;
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * 获取当前WIFI连接的ip地址
     *
     * @param context
     * @return
     */
    public static String getLocalIpAddress(Context context) {
        try {
            WifiManager wifiManager = (WifiManager) context
                    .getSystemService(Context.WIFI_SERVICE);
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            int i = wifiInfo.getIpAddress();
            return int2ip(i);
        } catch (Exception ex) {
            return " 获取IP出错鸟!!!!请保证是WIFI,或者请重新打开网络!\n" + ex.getMessage();
        }
    }

    /**
     * @param context
     * @return
     */
    public static String getCurrentAPN(Context context) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            return null;
        }

        String APN = null;
        String user = null;
        try {
            Uri uri = Uri.parse("content://telephony/carriers/preferapn");
            Cursor cr = context.getContentResolver().query(uri, null, null,
                    null, null);
            while (cr != null && cr.moveToNext()) {
                user = cr.getString(cr.getColumnIndex("user"));
                if (user.toLowerCase().contains("ctwap")) {
                    APN = "ctwap";
                } else if (user.toLowerCase().contains("ctnet")) {
                    APN = "ctnet";
                } else {
                    APN = null;
                }
            }

            if (cr != null) {
                cr.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return APN;
    }

    /**
     * 获取当前默认 APN
     *
     * @param context 上下文
     * @return 当前默认 APN
     */
    public static APN getDefaultAPN(Context context) {
        Cursor c = context.getContentResolver().query(CURRENT_APN_URI, null, null, null, null);
        APN apn = null;
        if (c != null && c.moveToFirst()) {
            apn = getAPNInfoByCursor(c);
        }
        if (c != null) {
            c.close();
        }
        return apn;
    }


    /**
     * 获取 APN 列表
     *
     * @param context 上下文
     * @return APN 列表
     */
    public static List<APN> getAPNList(Context context) {
        List<APN> list = new ArrayList<APN>();
        Cursor c = context.getContentResolver().query(APN_LIST_URI, null, null, null, null);
        while (c != null && c.moveToNext()) {
            list.add(getAPNInfoByCursor(c));
        }
        if (c != null) {
            c.close();
        }
        return list;
    }

    /**
     * 获取 APN 信息
     *
     * @param cursor 游标
     * @return APN 对象
     */
    private static APN getAPNInfoByCursor(Cursor cursor) {
        APN apn = new APN();
        for (Field field : apn.getClass().getDeclaredFields()) {
            int columnIndex = cursor.getColumnIndex(field.getName());
            if (columnIndex != -1) {
                field.setAccessible(true);
                try {
                    field.set(apn, cursor.getString(cursor.getColumnIndex(field.getName())));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        return apn;
    }


    /**
     * 设置飞行模式状态
     * 需添加权限：
     * <pre>
     *     android.permission.WRITE_SETTINGS
     * </pre>
     *
     * @param context          上下文
     * @param isAirplaneModeOn 是否开启飞行模式
     */
    public static void setAirplaneMode(Context context, boolean isAirplaneModeOn) {
        Settings.System.putInt(context.getContentResolver(), Settings.System.AIRPLANE_MODE_ON, isAirplaneModeOn ? 1 : 0);
        // 广播飞行模式的改变，让相应的程序可以处理
        // 不发送广播，在非飞行模式下，Android 2.2.1 上测试关闭了 WIFI，不关闭通话网络（GMS/GPRS等）
        // 不发送广播，在飞行模式下，Android 2.2.1 上测试无法关闭飞行模式
        Intent intent = new Intent(Intent.ACTION_AIRPLANE_MODE_CHANGED);
        context.sendBroadcast(intent);
    }

    /**
     * 设置默认 APN
     * 需添加权限：
     * <pre>
     *     android.permission.WRITE_APN_SETTINGS
     * </pre>
     *
     * @param context 上下文
     * @param apn_id  默认 APN 的 ID
     */
    public static void setDefaultAPN(Context context, String apn_id) {
        ContentValues values = new ContentValues();
        values.put("apn_id", apn_id);
        context.getContentResolver().update(CURRENT_APN_URI, values, null, null);
    }


    /**
     * 关闭 APN，即使 APN 信息失效
     * 需添加权限：
     * <pre>
     *     android.permission.WRITE_APN_SETTINGS
     * </pre>
     *
     * @param context 上下文
     */
    public static void closeAPN(Context context) {
        List<APN> apnList = getAPNList(context);
        ContentResolver contentResolver = context.getContentResolver();
        for (APN apn : apnList) {
            if (!isAPNClose(apn)) {
                ContentValues values = new ContentValues();
                values.put("apn", apn.getApn() + APN_SUFFIX);
                values.put("type", apn.getType() + APN_SUFFIX);
                values.put("user", apn.getUser() + APN_SUFFIX);
                contentResolver.update(APN_LIST_URI, values, "_id=?", new String[]{apn.get_id()});
            }
        }
    }


    /**
     * 打开 APN，即恢复失效的 APN 信息
     * 需添加权限：
     * <pre>
     *     android.permission.WRITE_APN_SETTINGS
     * </pre>
     *
     * @param context 上下文
     */
    public static void openAPN(Context context) {
        List<APN> apnList = getAPNList(context);
        ContentResolver contentResolver = context.getContentResolver();
        for (APN apn : apnList) {
            if (isAPNClose(apn)) {
                ContentValues values = new ContentValues();
                values.put("apn", delApnSuffix(apn.getApn()));
                values.put("type", delApnSuffix(apn.getType()));
                values.put("user", delApnSuffix(apn.getUser()));
                contentResolver.update(APN_LIST_URI, values, "_id=?", new String[]{apn.get_id()});
            }
        }
    }

    /**
     * 删除 APN 信息中的后缀
     *
     * @param str 带后缀的 APN 信息
     * @return 去除后缀的 APN 信息
     */
    private static String delApnSuffix(String str) {
        return str.substring(0, str.lastIndexOf(APN_SUFFIX));
    }

    /**
     * APN 信息
     */
    public static class APN {
        private String _id;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        private String apn;

        public String getApn() {
            return apn;
        }

        public void setApn(String apn) {
            this.apn = apn;
        }

        private String authtype;

        public String getAuthtype() {
            return authtype;
        }

        public void setAuthtype(String authtype) {
            this.authtype = authtype;
        }

        private String current;

        public String getCurrent() {
            return current;
        }

        public void setCurrent(String current) {
            this.current = current;
        }

        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        private String type;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        private String user;

        public String getUser() {
            return user;
        }

        public void setUser(String user) {
            this.user = user;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            for (Field field : getClass().getDeclaredFields()) {
                try {
                    sb.append(field.getName()).append(" = ").append(field.get(this)).append(" ; ");
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            return sb.toString();
        }
    }

    /**
     * 网络状态变化监听器
     */
    public static interface NetworkConnectivityChangeListener {
        /**
         * 当网络连接状态变化时调用
         *
         * @param isConnectivity 当前网络是否可用
         */
        public void networkConnectivityChange(boolean isConnectivity);
    }

    /**
     * 将ip的整数形式转换成ip形式
     *
     * @param ipInt
     * @return
     */
    public static String int2ip(int ipInt) {
        StringBuilder sb = new StringBuilder();
        sb.append(ipInt & 0xFF).append(".");
        sb.append((ipInt >> 8) & 0xFF).append(".");
        sb.append((ipInt >> 16) & 0xFF).append(".");
        sb.append((ipInt >> 24) & 0xFF);
        return sb.toString();
    }
}
