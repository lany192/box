package com.github.lany192.toolkit;

public class BoxToolKit {

    static {
        System.loadLibrary("toolkit");
    }

    public static native String getMMKVKey();

    /**
     * 不需要触发合规提示的获取进程名称方式
     */
    public static native String getCurrentProcess();

    /**
     * 是否是模拟器
     */
    public static native boolean isEmulator();
}