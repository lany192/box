package com.lany.box.divider;

import android.support.annotation.ColorInt;
import android.support.v7.widget.RecyclerView;

/**
 * 适用于LinearLayoutManager
 */
public class LinearItemDecoration extends ItemDecoration {
    /**
     * 分割线宽度，默认是1dp
     */
    private int width = 1;
    /**
     * 默认透明色
     */
    @ColorInt
    private int color = 0x00000000;
    /**
     * 方向，默认是纵向
     */
    @RecyclerView.Orientation
    private int orientation;

    public LinearItemDecoration() {
        orientation = RecyclerView.VERTICAL;
    }

    public LinearItemDecoration(@RecyclerView.Orientation int orientation) {
        this.orientation = orientation;
    }

    public LinearItemDecoration(int width, @ColorInt int color, @RecyclerView.Orientation int orientation) {
        this.width = width;
        this.color = color;
        this.orientation = orientation;
    }

    public LinearItemDecoration(int width, @ColorInt int color) {
        this.width = width;
        this.color = color;
        this.orientation = RecyclerView.VERTICAL;
    }

    public LinearItemDecoration setWidth(int width) {
        this.width = width;
        return this;
    }

    public LinearItemDecoration setColor(@ColorInt int color) {
        this.color = color;
        return this;
    }

    public LinearItemDecoration setOrientation(@RecyclerView.Orientation int orientation) {
        this.orientation = orientation;
        return this;
    }

    @Override
    public Divider getDivider(int itemPosition) {
        if (orientation != RecyclerView.HORIZONTAL && orientation != RecyclerView.VERTICAL) {
            throw new IllegalArgumentException("invalid orientation:" + orientation);
        }
        if (orientation != RecyclerView.VERTICAL) {
            return new Divider.Builder()
                    .setRight(color, width)
                    .build();
        } else {
            return new Divider.Builder()
                    .setBottom(color, width)
                    .build();
        }
    }
}