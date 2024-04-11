package com.github.lany192.toolkit;

public class BoxToolKit {

    static {
        System.loadLibrary("toolkit");
    }

    public static native String stringFromJNI();

    /**
     * 不需要触发合规提示的获取进程名称方式
     */
    public static native String getCurrentProcess();
}