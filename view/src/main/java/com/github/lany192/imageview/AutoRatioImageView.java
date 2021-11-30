package com.github.lany192.imageview;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

import com.google.android.material.imageview.ShapeableImageView;


/**
 * 自适应图片比例，以宽为基准
 * 描述：宽需固定 高度按图片宽高比自适应
 */
public class AutoRatioImageView extends ShapeableImageView {

    public AutoRatioImageView(Context context) {
        super(context);
    }

    public AutoRatioImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public AutoRatioImageView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
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