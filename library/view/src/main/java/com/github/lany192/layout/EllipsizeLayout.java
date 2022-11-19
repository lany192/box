package com.github.lany192.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

/**
 * 左边第一个view显示宽度不足时，限制其使用宽度
 */
public class EllipsizeLayout extends LinearLayout {

    public EllipsizeLayout(Context context) {
        super(context);
    }

    public EllipsizeLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public EllipsizeLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (getOrientation() == HORIZONTAL && (MeasureSpec.getMode(widthMeasureSpec) == MeasureSpec.EXACTLY)) {
            int totalLength = 0;
            boolean outOfSpec = false;
            TextView ellipView = null;
            final int count = getChildCount();
            for (int ii = 0; ii < count && !outOfSpec; ++ii) {
                final View child = getChildAt(ii);
                if (child != null && child.getVisibility() != GONE) {
                    if (child instanceof TextView) {
                        final TextView tv = (TextView) child;
                        if (tv.getEllipsize() != null) {
                            if (ellipView == null) {
                                ellipView = tv;
                                ellipView.setMaxWidth(Integer.MAX_VALUE);
                            } else {
                                outOfSpec = true;
                            }
                        }
                    }
                    final LayoutParams lp = (LayoutParams) child.getLayoutParams();
                    outOfSpec |= (lp.weight > 0f);
                    measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, 0);
                    totalLength += child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
                }
            }
            outOfSpec |= (ellipView == null) || (totalLength == 0);
            final int parentWidth = MeasureSpec.getSize(widthMeasureSpec);

            if (!outOfSpec && totalLength > parentWidth) {
                int maxWidth = ellipView.getMeasuredWidth() - (totalLength - parentWidth);
                int minWidth = 0;
                if (maxWidth < minWidth) {
                    maxWidth = minWidth;
                }
                ellipView.setMaxWidth(maxWidth);
            }
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}