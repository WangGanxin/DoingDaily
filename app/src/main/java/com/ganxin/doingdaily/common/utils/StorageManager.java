package com.ganxin.doingdaily.common.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Environment;

import java.io.File;

/**
 * Description : 存储管理类  <br/>
 * author : WangGanxin <br/>
 * date : 2017/7/17 <br/>
 * email : mail@wangganxin.me <br/>
 */
public class StorageManager {

    private static StorageManager INSTANCE;

    private Context mContext;
    private BroadcastReceiver mExternalStorageReceiver;
    private boolean mExternalStorageAvailable = false;
    private boolean mExternalStorageWriteable = false;

    private File mRoot;
    private File mImageDir;

    private StorageManager() {

    }

    public synchronized static StorageManager getInstance() {
        if (INSTANCE == null)
            INSTANCE = new StorageManager();
        return INSTANCE;
    }

    public void setAppContext(Context context) {
        mContext = context;
        updateExternalStorageState();
        startWatching();
    }

    public void setRootDir(String rootDir) {
        File f = getCacheDir(mContext, rootDir);
        if (f != null || !f.exists()) {
            f.mkdirs();
        }
        this.mRoot = f;
    }

    public void setImageDir(String rootDir) {
        File f = getCacheDir(mContext, rootDir);
        if (f != null || !f.exists()) {
            f.mkdirs();
        }
        this.mImageDir = f;
    }

    public File getRootDir() {
        return this.mRoot;
    }

    public File getImageDir() {
        return this.mImageDir;
    }

    private File getCacheDir(Context context, String uniqueName) {

        String cachePath = isExternalStorageAvailable() || !isExternalStorageRemovable() ?
                getExternalStorageDir().getPath() : context.getCacheDir().getPath();
        return new File(cachePath + File.separator + uniqueName);
    }

    private File getExternalStorageDir() {
        return new File(Environment.getExternalStorageDirectory().getPath());
    }

    private void updateExternalStorageState() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            mExternalStorageAvailable = mExternalStorageWriteable = true;
        } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            mExternalStorageAvailable = true;
            mExternalStorageWriteable = false;
        } else {
            mExternalStorageAvailable = mExternalStorageWriteable = false;
        }
    }

    public void startWatching() {
        if (mContext == null) {
            return;
        }

        mExternalStorageReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                updateExternalStorageState();
            }
        };
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_MEDIA_MOUNTED);
        filter.addAction(Intent.ACTION_MEDIA_REMOVED);
        mContext.registerReceiver(mExternalStorageReceiver, filter);
        updateExternalStorageState();
    }

    public void stopWatching() {
        if (mContext == null) {
            return;
        }
        mContext.unregisterReceiver(mExternalStorageReceiver);
    }

    public boolean isExternalStorageAvailable() {
        return mExternalStorageAvailable;
    }

    public boolean isExternalStorageWriteable() {
        return mExternalStorageWriteable;
    }

    /**
     * 主外部存储设备是否是可拆卸的
     *
     * @return
     */
    private boolean isExternalStorageRemovable() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            return Environment.isExternalStorageRemovable();
        }
        return true;
    }
}
