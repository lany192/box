package com.github.lany192.update.utils;

public final class Constant {
    /**
     * 网络连接超时时间
     */
    public static final int HTTP_TIME_OUT = 30_000;
    /**
     * Logcat日志输出Tag
     */
    public static final String TAG = "AppUpdate.";
    /**
     * 渠道通知id
     */
    public static final String DEFAULT_CHANNEL_ID = "appUpdate";
    /**
     * 渠道通知名称
     */
    public static final String DEFAULT_CHANNEL_NAME = "AppUpdate";
    /**
     * 新版本下载线程名称
     */
    public static final String THREAD_NAME = "app update thread";
    /**
     * apk文件后缀
     */
    public static final String APK_SUFFIX = ".apk";
    /**
     * 兼容Android N Uri 授权
     */
    public static String AUTHORITIES;
}
