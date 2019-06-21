package com.github.lany192.box.utils;

/**
 * 防止过快双击
 */
public class ClickUtil {
    private static long lastClickTime;
    private static long INTERVAL = 500;

    public synchronized static boolean isFast() {
        long time = System.currentTimeMillis();
        if (time - lastClickTime < INTERVAL) {
            return true;
        }
        lastClickTime = time;
        return false;
    }
}
