package com.ganxin.doingdaily.module.zhihu.list;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.ganxin.doingdaily.R;
import com.youth.banner.loader.ImageLoader;

/**
 * Description : GlideImageLoader  <br/>
 * author : WangGanxin <br/>
 * date : 2017/07/11 <br/>
 * email : mail@wangganxin.me <br/>
 */
public class GlideImageLoader extends ImageLoader {

    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        Glide.with(context)
                .load(path)
                .placeholder(R.drawable.placeholder_img_loading)
                .crossFade()
                .centerCrop()
                .into(imageView);
    }

    //提供createImageView 方法，如果不用可以不重写这个方法，主要是方便自定义ImageView的创建
    @Override
    public ImageView createImageView(Context context) {
        return super.createImageView(context);
    }
}
