package com.github.lany192.box.utils;

import androidx.annotation.ColorInt;

public class ColorUtils {

    /**
     * 判断是否是深颜色
     */
    public static boolean isDark(@ColorInt int color) {
        return androidx.core.graphics.ColorUtils.calculateLuminance(color) < 0.5;
    }
}
