package com.ganxin.doingdaily.common.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.DrawableRes;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.ganxin.doingdaily.R;

import java.util.concurrent.ExecutionException;

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
        display(view, url, R.drawable.placeholder_img_loading, null, false);
    }

    /**
     * glide加载图片
     *
     * @param view
     * @param url
     * @param isCircle
     */
    public static void display(ImageView view, String url, boolean isCircle) {
        display(view, url, R.drawable.placeholder_img_loading, null, isCircle);
    }


    /**
     * glide加载图片
     *
     * @param view
     * @param url
     * @param listener
     */
    public static void display(ImageView view, String url, RequestListener listener) {
        display(view, url, R.drawable.placeholder_img_loading, listener, false);
    }

    /**
     * glide加载图片
     *
     * @param view
     * @param url
     * @param defaultImage
     */
    public static void display(ImageView view, String url, @DrawableRes int defaultImage) {
        display(view, url, defaultImage, null, false);
    }

    /**
     * glide加载图片
     *
     * @param view         view
     * @param url          url
     * @param defaultImage defaultImage
     */
    public static void display(final ImageView view, String url,
                               @DrawableRes int defaultImage, RequestListener listener, boolean isCircle) {

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
                        .listener(listener)
                        .into(view);
            } else {

                if (isCircle) {
                    Glide.with(context)
                            .load(url)
                            .transform(new GlideCircleTransform(context))
                            .placeholder(defaultImage)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .listener(listener)
                            .into(view);
                } else {
                    Glide.with(context)
                            .load(url)
                            .placeholder(defaultImage)
                            .crossFade()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .centerCrop()
                            .listener(listener)
                            .into(view);
                }
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

    public static Bitmap getBitmap(Context context, String url) {
        Bitmap bitmap = null;

        try {
            bitmap = Glide.with(context)
                    .load(url)
                    .asBitmap()
                    .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                    .get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return bitmap;
    }

    private static class GlideCircleTransform extends BitmapTransformation {

        public GlideCircleTransform(Context context) {
            super(context);
        }

        @Override
        protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
            return circleCrop(pool, toTransform);
        }

        private Bitmap circleCrop(BitmapPool pool, Bitmap source) {
            if (source == null) return null;

            int size = Math.min(source.getWidth(), source.getHeight());
            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;

            // TODO this could be acquired from the pool too
            Bitmap squared = Bitmap.createBitmap(source, x, y, size, size);

            Bitmap result = pool.get(size, size, Bitmap.Config.ARGB_8888);
            if (result == null) {
                result = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
            }

            Canvas canvas = new Canvas(result);
            Paint paint = new Paint();
            paint.setShader(new BitmapShader(squared, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
            paint.setAntiAlias(true);
            float r = size / 2f;
            canvas.drawCircle(r, r, r, paint);
            return result;
        }

        @Override
        public String getId() {
            return getClass().getName();
        }
    }
}
