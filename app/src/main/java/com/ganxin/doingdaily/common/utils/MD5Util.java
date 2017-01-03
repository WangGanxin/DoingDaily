package com.ganxin.doingdaily.common.utils;

import android.text.TextUtils;

import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Description : MD5工具类  <br/>
 * author : WangGanxin <br/>
 * date : 2016/9/5 <br/>
 * email : ganxinvip@163.com <br/>
 */
public final class MD5Util {
    private static MessageDigest messageDigest;

    /**
     * 获取指定文件的 MD5 值
     *
     * @param path 文件路径
     * @return MD5 值
     */
    public static String getFileMD5String(String path) {
        String result = null;
        if (!TextUtils.isEmpty(path)) {
            result = getFileMD5String(new File(path));
        }
        return result;
    }

    /**
     * 获取文件的 MD5 值
     *
     * @param file 目标文件
     * @return MD5 字符串
     */
    public static String getFileMD5String(File file) {
        String result = null;
        if (messageDigest != null && file != null && file.exists() && file.isFile()) {
            FileInputStream in = null;
            FileChannel ch = null;
            try {
                in = new FileInputStream(file);
                ch = in.getChannel();
                ByteBuffer byteBuffer = ch.map(FileChannel.MapMode.READ_ONLY, 0, file.length());
                messageDigest.update(byteBuffer);
                result = ByteUtil.bytesToHex(messageDigest.digest());
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                try {
                    if (ch != null) {
                        ch.close();
                    }
                    if (in != null) {
                        in.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    /**
     * MD5 加密字符串
     *
     * @param str 目标字符串
     * @return MD5 加密后的字符串
     */
    public static String getMD5String(String str) {
        return TextUtils.isEmpty(str) ? null : getMD5String(str.getBytes());
    }

    /**
     * MD5 加密以 byte 数组表示的字符串
     *
     * @param bytes 目标数组
     * @return MD5加密后的字符串
     */
    public static String getMD5String(byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        String result = null;
        if (messageDigest != null && bytes != null && bytes.length > 0) {
            messageDigest.update(bytes);
            result = ByteUtil.bytesToHex(messageDigest.digest());
        }
        return result;
    }

    static {
        try {
            messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private MD5Util() {
    }


    /**
     * 对字符串进行32位MD5加密
     *
     * @param text 需要加密的文本
     * @return 通过32位MD5加密的字符串, 如果加密失败返回来源文本
     */
    public final static String md5_32(String text) {
        String md5String = null;
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(text.getBytes("UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (messageDigest != null) {
            byte[] byteArray = messageDigest.digest();
            int byteArray_length = byteArray.length;
            StringBuilder md5Builder = new StringBuilder();
            for (int i = 0; i < byteArray_length; i++) {
                if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
                    md5Builder.append("0").append(
                            Integer.toHexString(0xFF & byteArray[i]));
                else
                    md5Builder.append(Integer.toHexString(0xFF & byteArray[i]));
            }
            md5String = md5Builder.toString();
        } else {
            return text;
        }
        return md5String;
    }

    /**
     * 对字符串进行16位MD5加密
     *
     * @param text 需要加密的文本
     * @return 通过16位MD5加密的字符串, 如果加密失败返回来源文本
     */
    public final static String md5_16(String text) {
        String md5_32 = md5_32(text);
        String md5_16 = null;
        if (md5_32 != text) {
            md5_16 = md5_32.substring(8, 24);
        } else {
            return text;
        }
        return md5_16;
    }
}
