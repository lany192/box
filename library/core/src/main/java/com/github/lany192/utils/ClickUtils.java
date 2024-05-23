package com.github.lany192.utils;

/**
 * 防止过快双击
 */
public class ClickUtils {
    private static long lastClickTime;

    public synchronized static boolean isFast() {
        long INTERVAL = 500;
        long time = System.currentTimeMillis();
        if (time - lastClickTime < INTERVAL) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    public synchronized static boolean isFast(int interval) {
        long time = System.currentTimeMillis();
        if (time - lastClickTime < interval) {
            return true;
        }
        lastClickTime = time;
        return false;
    }
}
