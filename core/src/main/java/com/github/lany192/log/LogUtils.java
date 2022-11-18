package com.github.lany192.log;

import android.app.Application;

import com.tencent.mars.xlog.Log;
import com.tencent.mars.xlog.Xlog;

public class LogUtils {

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
        //日志等级
        int level = debug ? Xlog.LEVEL_VERBOSE : Xlog.LEVEL_INFO;

        //init xlog
        Xlog xlog = new Xlog();
        Log.setLogImp(xlog);

        //是否控制台打印
        Log.setConsoleLogOpen(debug);

        Log.appenderOpen(level, Xlog.AppednerModeAsync, cachePath, logPath, namePrefix, cacheDays);

        Log.i("Xlog", "日志debug:" + debug);
        Log.i("Xlog", "日志储存路径:" + logPath);
    }

    public static XLog tag(String tag) {
        return new XLog(tag);
    }

    public static void exit() {
        Log.appenderClose();
    }

    public static void f(String msg) {
        Log.f("Xlog", msg);
    }

    public static void e(String msg) {
        Log.e("Xlog", msg);
    }

    public static void w(String msg) {
        Log.w("Xlog", msg);
    }

    public static void i(String msg) {
        Log.i("Xlog", msg);
    }

    public static void d(String msg) {
        Log.d("Xlog", msg);
    }

    public static void v(String msg) {
        Log.v("Xlog", msg);
    }

    public static void f(String format, Object... obj) {
        Log.f("Xlog", format, obj);
    }

    public static void e(String format, Object... obj) {
        Log.e("Xlog", format, obj);
    }

    public static void w(String format, Object... obj) {
        Log.w("Xlog", format, obj);
    }

    public static void i(String format, Object... obj) {
        Log.i("Xlog", format, obj);
    }

    public static void d(String format, Object... obj) {
        Log.d("Xlog", format, obj);
    }

    public static void v(String format, Object... obj) {
        Log.v("Xlog", format, obj);
    }
}
