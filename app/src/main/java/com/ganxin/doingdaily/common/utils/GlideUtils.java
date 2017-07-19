package com.ganxin.doingdaily.common.utils;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.DrawableRes;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ganxin.doingdaily.R;

/**
 * Description : Glide工具类  <br/>
 * author : WangGanxin <br/>
 * date : 2017/7/17 <br/>
 * email : mail@wangganxin.me <br/>
 */
public class GlideUtils {

    /**
     * glide加载图片
     *
     * @param view view
     * @param url  url
     */
    public static void display(ImageView view, String url) {
        display(view, url, R.drawable.placeholder_img_loading);
    }

    /**
     * glide加载图片
     *
     * @param view         view
     * @param url          url
     * @param defaultImage defaultImage
     */
    public static void display(final ImageView view, String url,
                                   @DrawableRes int defaultImage) {

        if (view == null) {
            return;
        }

        Context context = view.getContext();
        if (context instanceof Activity) {
            if (((Activity) context).isFinishing()) {
                return;
            }
        }

        try {
            if (url.endsWith(".gif")) {
                Glide.with(context)
                        .load(url)
                        .asGif()
                        .placeholder(defaultImage)
                        .dontAnimate()
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(view);
            } else {
                Glide.with(context)
                        .load(url)
                        .placeholder(defaultImage)
                        .crossFade()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .centerCrop()
                        .into(view);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void displayNative(final ImageView view, @DrawableRes int resId) {

        if (view == null) {
            return;
        }

        Context context = view.getContext();

        if (context instanceof Activity) {
            if (((Activity) context).isFinishing()) {
                return;
            }
        }

        try {
            Glide.with(context)
                    .load(resId)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .into(view);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
