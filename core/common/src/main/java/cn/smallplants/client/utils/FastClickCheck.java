package cn.smallplants.client.utils;

import android.util.Log;

import androidx.annotation.Keep;

/**
 * 防抖控制类，请勿删除！
 */
@Keep

public class FastClickCheck {
    private static long lastClickTime;
    private final String TAG = getClass().getSimpleName();

    /**
     * 检查点击频率是否过快
     */
    public boolean isFastClick() {
        long time = System.currentTimeMillis();
        if (time - lastClickTime < 600) {
            Log.i(TAG, "点击过快，忽略点击");
            return false;
        }
        lastClickTime = time;
        return true;
    }
}
