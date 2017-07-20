package com.ganxin.doingdaily.common.utils;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Html;
import android.text.TextUtils;

import com.ganxin.doingdaily.R;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Description : 系统跳转帮助类  <br/>
 * author : WangGanxin <br/>
 * date : 2016/9/5 <br/>
 * email : ganxinvip@163.com <br/>
 */
public class SystemHelper {

    private SystemHelper() {
    }

    /**
     * 调用系统拔号
     *
     * @param context
     * @param phone
     */
    public static void SystemPhone(Context context, String phone) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + phone + ""));
        context.startActivity(intent);
    }

    /**
     * 调用系统短信
     *
     * @param context
     * @param address
     * @param sms_body
     */
    public static void SystemSms(Context context, String address, String sms_body) {
        Intent mIntent = new Intent(Intent.ACTION_VIEW);
        mIntent.putExtra("address", address);   //发送号码
        mIntent.putExtra("sms_body", sms_body);   //短信内容
        mIntent.setType("vnd.android-dir/mms-sms");
        context.startActivity(mIntent);
    }

    /**
     * 调用系统浏览器
     *
     * @param context
     * @param url
     */
    public static void SystemBrowser(Context context, String url) {

        if (context == null) {
            return;
        }

        if (TextUtils.isEmpty(url)) {
            return;
        }

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        Uri content_url = Uri.parse(url);
        intent.setData(content_url);

        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(Intent.createChooser(intent, context.getString(R.string.tips_select_browser)));
        }
    }

    /**
     * 调用系统相机
     *
     * @param activity
     */
    public static void SystemCamera(Activity activity) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
                "yyyyMMddhhmmss");
        Date date = new Date();
        String file_name = simpleDateFormat.format(date) + ".jpg";

        Uri imageUri = Uri.fromFile(new File(Environment
                .getExternalStorageDirectory(), file_name));
        // 指定照片保存路径（SD卡）
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        activity.startActivityForResult(intent, 1);
    }

    /**
     * 调用系统相册
     *
     * @param activity
     */
    public static void SystemMediaPhoto(Activity activity) {
        Intent i = new Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        activity.startActivityForResult(i, 1);
    }

    /**
     * 调用系统音乐播放器
     *
     * @param context
     * @param path
     */
    public static void SystemPlayAudio(Context context, String path) {
        Intent it = new Intent(Intent.ACTION_VIEW);
        it.setDataAndType(Uri.parse(path), "audio/mp3");
        context.startActivity(it);
    }

    /**
     * 调用系统视频播放器
     *
     * @param context
     * @param path
     */
    public static void SystemPlayMp4(Context context, String path) {
        Intent it = new Intent(Intent.ACTION_VIEW);
        it.setDataAndType(Uri.parse(path), "video/mp4");
        context.startActivity(it);
    }

    /**
     * 调用系统自带下载
     *
     * @param context
     * @param url
     */
    public static void SystemDownload(Context context, String url) {
        if (url != null) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            Uri data = Uri.parse(Html.fromHtml(url).toString());
            intent.setData(data);
            intent.setPackage("com.google.android.browser");
            intent.addCategory("android.intent.category.BROWSABLE");
            intent.setComponent(new ComponentName("com.android.browser",
                    "com.android.browser.BrowserActivity"));
            context.startActivity(intent);
        }
    }

    /**
     * 调用系统剪贴板复制
     *
     * @param context
     * @param text
     */
    public static void SystemCopy(Context context, String text) {

        if (TextUtils.isEmpty(text)) {
            return;
        }

        ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText("text", text);
        clipboardManager.setPrimaryClip(clipData);
    }

    /**
     * 更新系统图库
     *
     * @param context
     * @param uri
     */
    public static void UpdateMedia(Context context, Uri uri) {

        if (uri == null || context == null) {
            return;
        }

        Intent scannerIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri);
        context.sendBroadcast(scannerIntent);
    }
}
