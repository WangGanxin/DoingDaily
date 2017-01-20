package com.ganxin.doingdaily.common.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;


/**
 * Description : 设备工具类  <br/>
 * author : WangGanxin <br/>
 * date : 2016/9/5 <br/>
 * email : ganxinvip@163.com <br/>
 */
public class DeviceUtil {

    private static final Object SYNC_LOCK = new Object();

    private static String mTrackID;
    private static String mMachineID;

    public static final String TAG = "DeviceUtil";

    private static final String KEY_MIUI_VERSION_NAME = "ro.miui.ui.version.name";

    private DeviceUtil() {
    }

    /**
     * 获取可变标识符
     *
     * @param context 当前环境
     * @return 可变标识符
     */
    public static String getTrackID(Context context) {
        if (mTrackID == null) {
            synchronized (SYNC_LOCK) {
                if (mTrackID == null) {
                    StringBuilder trackIDBuilder = new StringBuilder();
                    String deviceID = getDeviceID(context);
                    String simSerial = getSimSerial(context);
                    String androidID = getAndroidID();
                    String androidSerial = DeviceUtil.getAndroidSerial();
                    String timeStamp = DateUtils.getTimeStampString();
                    trackIDBuilder.append(deviceID);
                    trackIDBuilder.append(simSerial);
                    trackIDBuilder.append(androidID);
                    trackIDBuilder.append(androidSerial);
                    trackIDBuilder.append(timeStamp);
                    mTrackID = MD5Util.md5_16(trackIDBuilder.toString());
                }
            }
        }
        return mTrackID;
    }

    /**
     * 获取物理标识符
     *
     * @param context 当前环境
     * @return 物理标识符
     */
    public static String getMachineID(Context context) {
        if (mMachineID == null) {
            synchronized (SYNC_LOCK) {
                if (mMachineID == null) {
                    StringBuilder machineIDBuilder = new StringBuilder();
                    String deviceID = getDeviceID(context);
                    String simSerial = getSimSerial(context);
                    String androidID = getAndroidID();
                    String androidSerial = DeviceUtil.getAndroidSerial();
                    machineIDBuilder.append(deviceID);
                    machineIDBuilder.append(simSerial);
                    machineIDBuilder.append(androidID);
                    machineIDBuilder.append(androidSerial);
                    mMachineID = MD5Util.md5_16(machineIDBuilder.toString());
                }
            }
        }
        return mMachineID;
    }

    /**
     * 获取Android序列号
     *
     * @return Android序列号
     */
    public static String getAndroidSerial() {
        String Serial = Build.SERIAL;
        if (Serial == null) {
            return "null";
        } else {
            return Serial;
        }
    }

    /**
     * 获取Sim卡序列号
     *
     * @param context 当前上下文
     * @return Sim卡序列号
     */
    public static String getSimSerial(Context context) {
        String Serial = ((TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE))
                .getSimSerialNumber();
        if (Serial == null) {
            return "null";
        } else {
            return Serial;
        }
    }

    /**
     * 获取Mac地址
     *
     * @param context 当前上下文
     * @return Mac地址
     */
    public static String getMacSerial(Context context) {
        String Serial = null;
        String str = "";
        try {
            Process pp = Runtime.getRuntime().exec(
                    "cat /sys/class/net/wlan0/address ");
            InputStreamReader ir = new InputStreamReader(pp.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);
            for (; null != str; ) {
                str = input.readLine();
                if (str != null) {
                    Serial = str.trim();// 去空格
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "null";
        }
        return Serial;
    }

    /**
     * 获取AndroidID
     *
     * @return AndroidID
     */
    public static String getAndroidID() {
        String id = android.provider.Settings.Secure.ANDROID_ID;
        if (id == null) {
            return "null";
        } else {
            return id;
        }
    }

    /**
     * 获取DeviveID
     *
     * @param context 当前上下文
     * @return DeviveID
     */
    public static String getDeviceID(Context context) {

        String id = ((TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
        if (id == null) {
            return "null";
        } else {
            return id;
        }
    }

    /**
     * @param context
     * @return
     */
    public static String getIMEI(Context context) {
        return getDeviceID(context);
    }

    /**
     * @param context
     * @return
     */
    public static String getIMSI(Context context) {
        String imsi = "00000000000000";
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String model = Build.MANUFACTURER + Build.MODEL;

        if (tm == null || (model.toLowerCase().contains("htc") && (tm.getSimState() == TelephonyManager.SIM_STATE_ABSENT || tm.getSimState() == TelephonyManager.SIM_STATE_UNKNOWN))) {
            return imsi;
        }
        String imsiTmp = tm.getSubscriberId();
        if (!TextUtils.isEmpty(imsiTmp)) {
            imsi = imsiTmp;
        }

        return imsi;
    }

    /**
     * @param context
     * @return
     */
    public static String getWifiMAC(Context context) {
        String mac = null;

        WifiManager wifiManager = (WifiManager) context.getSystemService(context.WIFI_SERVICE);
        if (wifiManager == null) {
        }
        try {
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            mac = wifiInfo.getMacAddress();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mac;
    }

    /**
     * 手机型号
     *
     * @return
     */
    public static String getModel() {
        String model = Build.MODEL;
        return model;
    }

    /**
     * 获取手机号码
     *
     * @param context
     * @return
     */
    public static String getPhoneNumber(Context context) {
        TelephonyManager phoneMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String number = phoneMgr.getLine1Number();
        return number;
    }

    private static String UniqueID; // 唯一标识

    /**
     * 手机用户识别串号
     *
     * @param context
     * @return
     */
    public static String getUniqueID(Context context) {
        if (UniqueID == null) {
            String deviceID = getDeviceID(context);//得到用户设备ID
            String androidID = getAndroidID();//得到
            String simSerial = getSimSerial(context);//得到用户SIM卡串号
            // String macSerial = PhysicalUtils.getMacSerial(context);
            String androidSerial = getAndroidSerial();//获取Android序列号
            UniqueID =MD5Util.getMD5String(deviceID + androidID + simSerial
                    + androidSerial);
        }
        return UniqueID;
    }

    /**
     * 获取设备屏幕大小
     *
     * @param context
     * @return 0 width,1 height
     */
    public static int[] getScreenSize(final Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        dm = context.getApplicationContext().getResources().getDisplayMetrics();
        int screenWidth = dm.widthPixels;
        int screenHeight = dm.heightPixels;

        return new int[]{screenWidth, screenHeight};
    }

    /**
     * 获取版本名称
     *
     * @return
     */
    public static String getVersionName(final Context context) {
        final String packageName = context.getPackageName();
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    packageName, 0);
            return info.versionName;
        } catch (NameNotFoundException e) {
            return "";
        }
    }

    /**
     * 获取版本
     *
     * @return
     */
    public static int getVersionCode(final Context context) {
        final String packageName = context.getPackageName();
        int version = 1;
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    packageName, 0);
            version = info.versionCode;
        } catch (NameNotFoundException e) {
        }
        return version;
    }

    /**
     * 获取SDK版本
     *
     * @return 当前SDK版本
     */
    public static final int getSDKVersion() {
        return Build.VERSION.SDK_INT;
    }

    /**
     * 获取手机厂商
     *
     * @return 当前手机生产厂商
     */
    public static final String getBoard() {
        if (Build.BOARD != null) {
            return Build.BOARD;
        }
        return "unKnown";
    }

    public static String getCPU() {
        String cpuInfo = null;

        FileReader fstream = null;
        BufferedReader in = null;
        try {
            fstream = new FileReader("/proc/cpuinfo");
            if (fstream != null) {
                in = new BufferedReader(fstream, 1024);
                cpuInfo = in.readLine();
                in.close();
                fstream.close();
            }
            if (cpuInfo != null) {
                int start = cpuInfo.indexOf(':') + 1;
                cpuInfo = cpuInfo.substring(start);
            }

            cpuInfo = cpuInfo.trim();
        } catch (FileNotFoundException e) {
            Log.e(TAG, "Could not open /proc/cpuinfo", e);
        } catch (IOException e) {
            Log.e(TAG, "Could not read /proc/cpuinfo", e);
        } catch (NullPointerException e) {
            Log.e(TAG, "Could not read /proc/cpuinfo", e);
        } finally {
            try {
                if (null != fstream) {
                    fstream.close();
                    fstream = null;
                }
                if (null != in) {
                    in.close();
                    in = null;
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return cpuInfo;
    }


    public static HashMap<String, Long> getMemoryInfo(Context context) {

        HashMap<String, Long> hmMeminfo = new HashMap<String, Long>();

        String meminfoPath = "/proc/meminfo";

        FileReader fr = null;
        BufferedReader br = null;
        try {
            fr = new FileReader(meminfoPath);
            br = new BufferedReader(fr, 4096);
            String lineStr = null;
            while ((lineStr = br.readLine()) != null) {


                String[] lineItems = lineStr.split("\\s+");
                if (lineItems != null && lineItems.length == 3) {

                    String itemName = lineItems[0].substring(0, lineItems[0].length() - 1);

                    long itemMemory = Long.valueOf(lineItems[1]);


                    hmMeminfo.put(itemName, itemMemory);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (fr != null) {
                try {
                    fr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return hmMeminfo;
    }


    public static String getSystemProperty(String propName) {
        String line;
        BufferedReader input = null;
        try {
            Process p = Runtime.getRuntime().exec("getprop " + propName);
            input = new BufferedReader(new InputStreamReader(p.getInputStream()), 1024);
            line = input.readLine();
            input.close();
        } catch (IOException ex) {
            return null;
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                }
            }
        }
        return line;
    }


    /**
     * 判断是否是小米rom
     */
    public static boolean isMIUIRom() {
        String property = getSystemProperty(KEY_MIUI_VERSION_NAME);
        return !TextUtils.isEmpty(property);
    }

    public static boolean isMiUIV6() {
        try {
            final BuildProperties prop = BuildProperties.newInstance();
            String name = prop.getProperty(KEY_MIUI_VERSION_NAME, "");
            if ("V6".equals(name)) {
                return true;
            } else {
                return false;
            }
        } catch (final IOException e) {
            return false;
        }
    }

    /**
     * 是否是oppo机器
     */
    public static boolean isOPPO() {
        String deviceModel = Build.MANUFACTURER + Build.MODEL;
        if (!TextUtils.isEmpty(deviceModel) && deviceModel.toLowerCase().contains("oppo")) {
            return true;
        }
        return false;
    }


    public static class BuildProperties {

        private final Properties properties;

        private BuildProperties() throws IOException {
            properties = new Properties();
            properties.load(new FileInputStream(new File(Environment.getRootDirectory(), "build.prop")));
        }

        public boolean containsKey(final Object key) {
            return properties.containsKey(key);
        }

        public boolean containsValue(final Object value) {
            return properties.containsValue(value);
        }

        public Set<Map.Entry<Object, Object>> entrySet() {
            return properties.entrySet();
        }

        public String getProperty(final String name) {
            return properties.getProperty(name);
        }

        public String getProperty(final String name, final String defaultValue) {
            return properties.getProperty(name, defaultValue);
        }

        public boolean isEmpty() {
            return properties.isEmpty();
        }

        public Enumeration<Object> keys() {
            return properties.keys();
        }

        public Set<Object> keySet() {
            return properties.keySet();
        }

        public int size() {
            return properties.size();
        }

        public Collection<Object> values() {
            return properties.values();
        }

        public static BuildProperties newInstance() throws IOException {
            return new BuildProperties();
        }

    }
}
