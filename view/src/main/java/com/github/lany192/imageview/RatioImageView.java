package com.github.lany192.imageview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import com.github.lany192.view.R;
import com.google.android.material.imageview.ShapeableImageView;

/**
 * 比例图片，以宽为基准
 * <p>
 * 1.如果指定了比例，显示指定比例
 * 2.如果没有指定比例，自适应图片比例
 */
public class RatioImageView extends ShapeableImageView {
    /**
     * 宽高比例。
     */
    private float ratio;

    public RatioImageView(Context context) {
        this(context, null, 0);
    }

    public RatioImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RatioImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (attrs != null) {
            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.RatioImageView, 0, 0);
            ratio = array.getFloat(R.styleable.RatioImageView_riv_ratio, 0.0f);
            array.recycle();
        }
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
            //显示指定宽高比
            int width = MeasureSpec.getSize(widthMeasureSpec);
            int height = (int) (width / ratio);
            setMeasuredDimension(width, height);
        } else {
            Drawable drawable = getDrawable();
            if (drawable != null) {
                //自适应原始图片比
                int width = MeasureSpec.getSize(widthMeasureSpec);
                int height = (int) (width * drawable.getIntrinsicHeight() * 1f / drawable.getIntrinsicWidth());
                setMeasuredDimension(width, height);
            } else {
                //不处理，使用默认配置
                super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            }
        }
    }
}
