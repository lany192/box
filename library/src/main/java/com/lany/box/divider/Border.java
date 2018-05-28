package com.lany.box.divider;

import android.content.res.Resources;
import android.support.annotation.ColorInt;
import android.util.TypedValue;

/**
 * 边
 */
public class Border {
    /**
     * 是否存在这个边
     */
    public boolean isExist;
    /**
     * 边的颜色
     **/
    @ColorInt
    public int color;
    /**
     * 边的宽度，单位dp
     */
    public float width;
    /**
     * startPadding,分割线起始的padding，水平方向左为start，垂直方向上为start
     */
    public float startPadding;
    /**
     * endPadding,分割线尾部的padding，水平方向右为end，垂直方向下为end
     */
    public float endPadding;

    /**
     * @param isExist      是否存在这个边
     * @param color        边的颜色
     * @param width        边的宽度，单位dp
     * @param startPadding 起始的padding，单位dp
     * @param endPadding   尾部的padding，单位dp
     */
    public Border(boolean isExist, @ColorInt int color, float width, float startPadding, float endPadding) {
        this.isExist = isExist;
        this.color = color;
        this.width = width;
        this.startPadding = startPadding;
        this.endPadding = endPadding;
    }

    public int getWidth() {
        if (isExist) {
            return dp2px(width);
        } else {
            return 0;
        }
    }

    public int getStartPadding() {
        return dp2px(startPadding);
    }

    public int getEndPadding() {
        return dp2px(endPadding);
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    private int dp2px(float dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, Resources.getSystem().getDisplayMetrics());
    }

    public boolean isExist() {
        return isExist;
    }

    public void setExist(boolean exist) {
        isExist = exist;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public void setStartPadding(float startPadding) {
        this.startPadding = startPadding;
    }

    public void setEndPadding(float endPadding) {
        this.endPadding = endPadding;
    }
}
