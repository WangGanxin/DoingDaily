package com.ganxin.doingdaily.common.utils;

/**
 * Description : 字节操作类  <br/>
 * author : WangGanxin <br/>
 * date : 2016/9/5 <br/>
 * email : ganxinvip@163.com <br/>
 */
public final class ByteUtil {

    private ByteUtil() {
    }

    /**
     * 16 进制字符串
     */
    private static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    /**
     * 将单个字符转换为十六进制字符串
     *
     * @param bt 目标字符
     * @return 转换结果
     */
    public static String byteToHex(byte bt) {
        return HEX_DIGITS[(bt & 0xF0) >> 4] + "" + HEX_DIGITS[bt & 0xF];
    }

    /**
     * 将字节数组转换成16进制字符串
     *
     * @param bytes 目标字节数组
     * @return 转换结果
     */

    public static String bytesToHex(byte bytes[]) {
        return bytesToHex(bytes, 0, bytes.length);
    }

    /**
     * 将字节数组中指定区间的子数组转换成16进制字符串
     *
     * @param bytes 目标字节数组
     * @param start 起始位置（包括该位置）
     * @param end   结束位置（不包括该位置）
     * @return 转换结果
     */
    public static String bytesToHex(byte[] bytes, int start, int end) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0, count = bytes.length <= start + end ? bytes.length : start + end; i < count; i++) {
            sb.append(byteToHex(bytes[i]));
        }
        return sb.toString();
    }

    /**
     * 截取 byte 数组
     *
     * @param src   原 byte 数组
     * @param begin 起始点（包含）
     * @param end   结束点（包含）
     * @return 如起始和结束点设置不正确，将返回 null
     */
    public static byte[] cutBytes(byte[] src, int begin, int end) {
        byte[] result = null;
        if (src != null && begin >= 0 && end < src.length && begin < end) {
            result = new byte[end - begin + 1];
            System.arraycopy(src, begin, result, 0, end + 1 - begin);
        }
        return result;
    }

    /**
     * 查找 tag 在 src 中出现的位置
     *
     * @param tag 待查找的字节数组
     * @param src 原字节数组
     * @return -1 表示未找到
     */
    public static int indexOf(byte[] tag, byte[] src) {
        return indexOf(tag, src, 0);
    }

    /**
     * 查找 tag 在 src 中出现的位置
     *
     * @param tag   待查找的字节数组
     * @param src   原字节数组
     * @param start 查找的起始位置
     * @return -1 表示未找到
     */
    public static int indexOf(byte[] tag, byte[] src, int start) {
        int result = -1;
        if (tag != null && src != null && tag.length <= src.length - start) {
            for (int i = start; i <= src.length - tag.length; i++) {
                if (result != -1) {
                    break;
                }
                if (tag[0] == src[i]) {
                    for (int j = 0; j < tag.length; j++) {
                        if (tag[j] != src[i + j]) {
                            break;
                        }
                        if (j == tag.length - 1) {
                            result = i;
                        }
                    }
                }
            }
        }
        return result;
    }

    /**
     * 查找 tag 在 src 中出现的位置
     *
     * @param tag 待查找的字节数组
     * @param src 原字节数组
     * @return -1 表示未找到
     */
    public static int lastIndexOf(byte[] tag, byte[] src) {
        return lastIndexOf(tag, src, 0);
    }

    /**
     * 查找 tag 在 src 中出现的位置
     *
     * @param tag   待查找的字节数组
     * @param src   原字节数组
     * @param start 查找的起始位置，是相对于结束位置的距离
     * @return -1 表示未找到
     */
    public static int lastIndexOf(byte[] tag, byte[] src, int start) {
        int result = -1;
        if (tag != null && src != null && tag.length <= src.length - start) {
            for (int i = src.length - start - tag.length; i >= 0; i--) {
                if (result != -1) {
                    break;
                }
                if (tag[0] == src[i]) {
                    for (int j = 0; j < tag.length; j++) {
                        if (tag[j] != src[i + j]) {
                            break;
                        }
                        if (j == tag.length - 1) {
                            result = i;
                        }
                    }
                }
            }
        }
        return result;
    }

    /**
     * Convert hex string to byte[]
     *
     * @param hexString the hex string
     * @return byte[]
     */
    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

    /**
     * Convert char to byte
     *
     * @param c char
     * @return byte
     */
    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }
}
