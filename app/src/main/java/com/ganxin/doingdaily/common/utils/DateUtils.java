package com.ganxin.doingdaily.common.utils;

import android.text.TextUtils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Description : 日期时间工具类  <br/>
 * author : WangGanxin <br/>
 * date : 2016/9/5 <br/>
 * email : ganxinvip@163.com <br/>
 */
public class DateUtils {

    private DateUtils() {

    }

    /**
     * 判断是否为闰年
     *
     * @param year
     * @return
     */
    public static boolean isLeapYear(int year) {
        if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0)
            return true;
        else
            return false;
    }

    /****
     * 正计时计算
     *
     * @param oldDate 推算的目标日期，格式必须是 <b>yyyy-MM-dd hh:mm:ss</b>
     * @return 距现在的年数、周数、月数、天数字符串
     */
    public static String getOnTime(String oldDate) {
        return getOnTime(oldDate, "yyyy-MM-dd hh:mm:ss");
    }


    /**
     * 获取某年某月的天数
     *
     * @param year
     * @param month
     * @return
     */
    public static int getYearMonthForDays(int year, int month) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
            Date date = sdf.parse(year + "-" + month);
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(date);
            return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 获取当前时间
     *
     * @param format 转换的格式  <li>e.g : yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String getCurrentTimeFormat(String format) {
        String time = "";
        try {
            SimpleDateFormat df = new SimpleDateFormat(format);//设置日期格式
            time = df.format(new Date());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return time;
    }

    public static String getTimeStampString(){
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        return timestamp.toString();
    }

    /****
     * 正计时计算
     *
     * @param oldDate 推算的目标日期
     * @param format  日期格式
     * @return 距现在的年数、周数、月数、天数字符串
     */
    private static String getOnTime(String oldDate, String format) {
        if (TextUtils.isEmpty(oldDate)) {
            return "";
        }
        Calendar c = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                format, Locale.getDefault());
        int cy = c.get(Calendar.YEAR);
        int cm = c.get(Calendar.MONTH);
        int cw = c.get(Calendar.WEEK_OF_YEAR);
        int cwm = c.get(Calendar.WEEK_OF_MONTH);
        int cd = c.get(Calendar.DAY_OF_YEAR);
        int ch = c.get(Calendar.HOUR_OF_DAY);
        int cmit = c.get(Calendar.MINUTE);
        int cs = c.get(Calendar.SECOND);
        try {
            c.setTime(dateFormat.parse(oldDate));
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
        int oy = c.get(Calendar.YEAR);
        int om = c.get(Calendar.MONTH);
        int ow = c.get(Calendar.WEEK_OF_YEAR);
        int owm = c.get(Calendar.WEEK_OF_MONTH);
        int od = c.get(Calendar.DAY_OF_YEAR);
        int oh = c.get(Calendar.HOUR_OF_DAY);
        int omit = c.get(Calendar.MINUTE);
        int os = c.get(Calendar.SECOND);
        if (cy != oy) {
            int gap = Math.abs(cy - oy);
            if (gap == 1) {
                return "去年";
            } else if (gap == 2) {
                return "前年";
            }
            return gap + "年前";
        } else if (ow != cw) {
            int gap = Math.abs(cw - ow);
            if (gap == 1) {
                return "上周";
            } else if (gap == 2) {
                return "两周前";
            } else {
                if (cm != om) {
                    int mgap = Math.abs(cm - om);
                    return getGapStr("个月", mgap);
                } else {
                    int wgap = cwm - owm;
                    if (wgap == 1) {
                        return "上周";
                    } else if (wgap == 2) {
                        return "两周前";
                    } else {
                        return wgap + "周前";
                    }
                }
            }
        } else if (cd != od) {
            int gap = Math.abs(cd - od);
            if (gap == 1) {
                return "昨天";
            } else if (gap == 2) {
                return "前天";
            } else {
                return gap + "天前";
            }
        } else {
            if (ch != oh) {
                int gap = Math.abs(ch - oh);
                if (gap < 12) {
                    return getGapStr("小时", gap);
                } else {
                    return "今天";
                }
            } else if (cmit != omit) {
                int gap = Math.abs(cmit - omit);
                return getGapStr("分钟", gap);
            } else {
                int gap = Math.abs(cs - os);
                if (gap <= 30) {
                    return "刚刚";
                } else {
                    return gap + "秒前";
                }
            }
        }
    }

    private static String getGapStr(String type, int gap) {
        if (gap == 1) {
            return "一" + type + "前";
        } else if (gap == 2) {
            return "两" + type + "前";
        } else {
            return gap + type + "前";
        }
    }
}
