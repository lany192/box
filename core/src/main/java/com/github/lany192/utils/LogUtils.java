package com.github.lany192.utils;

import android.app.Application;

import com.tencent.mars.xlog.Log;
import com.tencent.mars.xlog.Xlog;

public class LogUtils {
    //默认TAG
    private final static String TAG = "Xlog";

    public static void init(Application application, boolean debug) {
        //加载必要so
        System.loadLibrary("c++_shared");
        System.loadLibrary("marsxlog");

        //日志储存路径
        final String logPath = application.getFilesDir() + "/xlog";
        //缓存路径
        final String cachePath = application.getCacheDir() + "/xlog";
        //日志文件前缀
        final String namePrefix = application.getClass().getSimpleName() + "_box";
        //缓存天数
        final int cacheDays = 7;

        //init xlog
        Xlog xlog = new Xlog();
        Log.setLogImp(xlog);

        Log.setConsoleLogOpen(debug);
        if (debug) {
            Log.appenderOpen(Xlog.LEVEL_VERBOSE, Xlog.AppednerModeAsync, cachePath, logPath, namePrefix, cacheDays);
        } else {
            Log.appenderOpen(Xlog.LEVEL_INFO, Xlog.AppednerModeAsync, cachePath, logPath, namePrefix, cacheDays);
        }
        Log.i(TAG, "日志储存路径:" + logPath);
    }

    public static void exit() {
        Log.appenderClose();
    }

    public static XLog tag(String tag) {
        return new XLog(tag);
    }

    public static void f(String tag, String msg) {
        Log.f(tag, msg);
    }

    public static void f(String msg) {
        Log.f(TAG, msg);
    }

    public static void e(String tag, String msg) {
        Log.e(tag, msg);
    }

    public static void e(String msg) {
        Log.e(TAG, msg);
    }

    public static void w(String tag, String msg) {
        Log.w(tag, msg);
    }

    public static void w(String msg) {
        Log.w(TAG, msg);
    }

    public static void i(String tag, String msg) {
        Log.i(tag, msg);
    }

    public static void i(String msg) {
        Log.i(TAG, msg);
    }

    public static void d(String tag, String msg) {
        Log.d(tag, msg);
    }

    public static void d(String msg) {
        Log.d(TAG, msg);
    }

    public static void v(String tag, String msg) {
        Log.v(tag, msg);
    }

    public static void v(String msg) {
        Log.v(TAG, msg);
    }

    public static void f(String tag, String format, Object... obj) {
        Log.f(tag, format, obj);
    }

    public static void e(String tag, String format, Object... obj) {
        Log.e(tag, format, obj);
    }

    public static void w(String tag, String format, Object... obj) {
        Log.w(tag, format, obj);
    }

    public static void i(String tag, String format, Object... obj) {
        Log.i(tag, format, obj);
    }

    public static void d(String tag, String format, Object... obj) {
        Log.d(tag, format, obj);
    }

    public static void v(String tag, String format, Object... obj) {
        Log.v(tag, format, obj);
    }
}
