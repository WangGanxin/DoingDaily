package com.ganxin.doingdaily.common.utils;

import android.content.Context;
import android.util.TypedValue;

/**
 * Description : 屏幕密度转换工具类  <br/>
 * author : WangGanxin <br/>
 * date : 2016/9/5 <br/>
 * email : ganxinvip@163.com <br/>
 */
public class DensityUtil {
    private static DensityUtil instance;
    private Context ctx;

    public static final DensityUtil getInstance(Context ctx) {
        if (instance == null) {
            instance = new DensityUtil(ctx);
        }
        return instance;
    }

    private DensityUtil(Context ctx) {
        this.ctx = ctx;
    }

    /**
     * Dp转PX
     *
     * @param size
     * @return
     */
    public int getDip2Px(int size) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, size, ctx.getResources().getDisplayMetrics());
    }

    /**
     * 根据手机的分辨率从 dp 转成为 px
     *
     * @param dpValue
     * @return
     */
    @Deprecated
    public int getDip2Px(float dpValue) {
        final float scale = ctx.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /***
     * SP转PX
     *
     * @param size
     * @return
     */
    public int getSp2Px(int size) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, size, ctx.getResources().getDisplayMetrics());
    }

    /**
     * 根据手机的分辨率从 sp转成为 px
     *
     * @param spValue
     * @return
     */
    @Deprecated
    public int getSp2Px(float spValue) {
        float fontScale = ctx.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px 转成为 dp
     *
     * @param pxValue
     * @return
     */
    public int getPx2Dip(float pxValue) {
        final float scale = ctx.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px 转成为 sp
     *
     * @param pxValue
     * @return
     */
    public int getPx2Sp(float pxValue) {
        float fontScale = ctx.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }


    /***
     * Unit转PX
     *
     * @param unit <li>{@link TypedValue#COMPLEX_UNIT_DIP TypedValue.COMPLEX_UNIT_DIP}等单位
     * @param size
     * @return
     */
    public int getUnit2Px(int unit, int size) {
        return (int) TypedValue.applyDimension(unit, size, ctx.getResources().getDisplayMetrics());
    }
}
