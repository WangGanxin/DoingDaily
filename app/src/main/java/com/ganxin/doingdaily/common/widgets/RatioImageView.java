package com.ganxin.doingdaily.common.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import com.ganxin.doingdaily.R;

public class RatioImageView extends AppCompatImageView {

    public static final int RATIO_WIDTH_HEIGHT = 1266;
    public static final int RATIO_HEIGHT_WIDTH = 2266;

    private static final float DEFAULT_RATIO = 1.0f;

    // 默认设置宽高比
    public int imageRatioType;
    private float imageRatio;


    public RatioImageView(Context context) {
        super(context);
    }


    public RatioImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.initAttributes(context, attrs);
    }


    public RatioImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.initAttributes(context, attrs);
    }

    private void initAttributes(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RatioImageView);
        this.imageRatioType = typedArray.getInteger(R.styleable.RatioImageView_imageRatioType,
                RATIO_WIDTH_HEIGHT);
        this.imageRatio = typedArray.getFloat(R.styleable.RatioImageView_imageRatio, DEFAULT_RATIO);
        typedArray.recycle();
    }


    public void setImageRatioType(int ratioType) {
        this.imageRatioType = ratioType;
    }


    /**
     * 设置宽高比,高度要跟宽度保持一致
     *
     * @param ratio width / height
     */
    public void setWidthHeightRatio(float ratio) {
        this.imageRatioType = RATIO_WIDTH_HEIGHT;
        this.imageRatio = ratio;
        this.requestLayout();
    }


    /**
     * 设置高宽比,宽度要跟高度保持一致
     *
     * @param ratio height / width
     */
    public void setHeightWidthRatio(float ratio) {
        this.imageRatioType = RATIO_HEIGHT_WIDTH;
        this.imageRatio = ratio;
        this.requestLayout();
    }


    public void setImageRatio(float ratio) {
        this.imageRatio = ratio;
        this.requestLayout();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (this.imageRatio > 0) {
            int width = MeasureSpec.getSize(widthMeasureSpec);
            int height = MeasureSpec.getSize(heightMeasureSpec);
            if (this.imageRatioType == RatioImageView.RATIO_WIDTH_HEIGHT) {
                // 依照宽高比,重算高度
                if (width > 0) {
                    height = (int) ((float) width / this.imageRatio);
                }
            } else {
                // 依照高宽比,重算宽度
                if (height > 0) {
                    width = (int) ((float) height / this.imageRatio);
                }
            }
            this.setMeasuredDimension(width, height);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }
}
