package com.github.lany192.box.utils;

import android.support.annotation.ColorInt;

public class ColorUtils {

    /**
     * 判断是否是深颜色
     */
    public static boolean isDark(@ColorInt int color) {
        return android.support.v4.graphics.ColorUtils.calculateLuminance(color) < 0.5;
    }
}
