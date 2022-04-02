package com.github.lany192.imageview;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

import com.google.android.material.imageview.ShapeableImageView;

/**
 * 比例图片，以宽为基准
 */
public class RatioImageView extends ShapeableImageView {
    /**
     * 宽高比例。
     */
    private float ratio;

    public RatioImageView(Context context) {
        super(context);
    }

    public RatioImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RatioImageView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * 设置图片宽高比
     */
    public void setRatio(float ratio) {
        this.ratio = ratio;
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (ratio > 0) {
            int width = MeasureSpec.getSize(widthMeasureSpec);
            int height = (int) (width / ratio);
            setMeasuredDimension(width, height);
        } else {
            Drawable drawable = getDrawable();
            if (drawable != null) {
                int width = MeasureSpec.getSize(widthMeasureSpec);
                int height = (int) (width * drawable.getIntrinsicHeight() * 1f / drawable.getIntrinsicWidth());
                setMeasuredDimension(width, height);
            } else {
                super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            }
        }
    }
}
