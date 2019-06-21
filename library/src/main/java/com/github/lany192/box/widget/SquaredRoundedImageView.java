package com.github.lany192.box.widget;

import android.content.Context;
import android.util.AttributeSet;

import com.makeramen.roundedimageview.RoundedImageView;

/**
 * 正方形ImageView
 */
public class SquaredRoundedImageView extends RoundedImageView {

    public SquaredRoundedImageView(Context context) {
        super(context);
    }

    public SquaredRoundedImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SquaredRoundedImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth());
    }
}
