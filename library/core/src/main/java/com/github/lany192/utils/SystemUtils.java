package com.github.lany192.utils;

import android.content.Context;
import android.provider.Settings;

public class SystemUtils {
    /**
     * 系统是否关闭动画
     */
    public static boolean isAnimationsEnabled(Context context) {
        float windowAnimationScale = Settings.Global.getFloat(context.getContentResolver(),
                Settings.Global.WINDOW_ANIMATION_SCALE, 1.0f);
        float transitionAnimationScale = Settings.Global.getFloat(context.getContentResolver(),
                Settings.Global.TRANSITION_ANIMATION_SCALE, 1.0f);
        float animatorDurationScale = Settings.Global.getFloat(context.getContentResolver(),
                Settings.Global.ANIMATOR_DURATION_SCALE, 1.0f);
        return windowAnimationScale > 0.0f || transitionAnimationScale > 0.0f || animatorDurationScale > 0.0f;
    }

}
