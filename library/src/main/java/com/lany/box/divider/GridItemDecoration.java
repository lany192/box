package com.lany.box.divider;

import android.support.annotation.ColorInt;
import android.support.v7.widget.GridLayoutManager;

/**
 * 适用于GridLayoutManager
 */
public class GridItemDecoration extends ItemDecoration {
    private int spanCount;
    private int width = 2;
    /**
     * 默认透明色
     */
    @ColorInt
    private int color = 0x00000000;

    public GridItemDecoration() {

    }

    public GridItemDecoration(int spanCount, int width, int color) {
        this.spanCount = spanCount;
        checkSpanCount(spanCount);
        this.width = width;
        this.color = color;
    }

    public GridItemDecoration(GridLayoutManager manager) {
        this.spanCount = manager.getSpanCount();
        checkSpanCount(spanCount);
    }

    public GridItemDecoration setSpanCount(int spanCount) {
        this.spanCount = spanCount;
        checkSpanCount(spanCount);
        return this;
    }

    public GridItemDecoration setWidth(int width) {
        this.width = width;
        return this;
    }

    public GridItemDecoration setColor(@ColorInt int color) {
        this.color = color;
        return this;
    }

    @Override
    public Divider getDivider(int itemPosition) {
        checkSpanCount(spanCount);
        int remainder = itemPosition % spanCount;
        if (remainder == spanCount - 1) {
            //最后一个只显示bottom
            return new Divider.Builder()
                    .setBottom(color, width)
                    .build();
        } else {
            return new Divider.Builder()
                    .setRight(color, width)
                    .setBottom(color, width)
                    .build();
        }
    }

    private void checkSpanCount(int spanCount) {
        if (spanCount < 2) {
            throw new RuntimeException("SpanCount minimum is 2");
        }
    }
}