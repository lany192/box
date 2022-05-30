package com.github.lany192.imageview;

import android.animation.ArgbEvaluator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.core.graphics.drawable.DrawableCompat;

import com.google.android.material.imageview.ShapeableImageView;

/**
 * 黑白渐变图片
 */
public class GradientImageView extends ShapeableImageView {

    public GradientImageView(Context context) {
        super(context);
    }

    public GradientImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public GradientImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 改变颜色值，取值范围0.0~1.0
     */
    public void changeColor(float fraction) {
        if (fraction > 1) {
            fraction = 1;
        } else if (fraction < 0) {
            fraction = 0;
        }
        Drawable drawable = DrawableCompat.wrap(getDrawable().mutate());
        DrawableCompat.setTint(drawable, (int) new ArgbEvaluator().evaluate(fraction, Color.WHITE, Color.BLACK));
        setImageDrawable(drawable);
    }
}
