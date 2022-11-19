package com.github.lany192.arch;

import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.github.lany192.arch.utils.PhoneUtils;
import com.github.lany192.log.LogUtils;
import com.github.lany192.log.XLog;
import com.github.lany192.utils.KVUtils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

/**
 * 异常处理
 */
public class CrashHelper implements Thread.UncaughtExceptionHandler {
    private volatile static CrashHelper instance = null;
    private final String KEY_APP_ERROR_INFO = "app_crash_error_info";

    private CrashHelper() {
        Thread.setDefaultUncaughtExceptionHandler(this);
        checkError();
    }

    public static CrashHelper getInstance() {
        if (instance == null) {
            synchronized (CrashHelper.class) {
                if (instance == null) {
                    instance = new CrashHelper();
                }
            }
        }
        return instance;
    }

    @Override
    public void uncaughtException(@NonNull Thread thread, @NonNull Throwable e) {
        String error = getError(e);
        KVUtils.get().putString(KEY_APP_ERROR_INFO, error);
        XLog.tag("app崩溃退出").i("基础信息:" + PhoneUtils.getBaseInfo());
        XLog.tag("app崩溃退出").i(error);
    }

    /**
     * 打印app上次崩溃信息，防止崩溃时没有记录到
     */
    private void checkError() {
        String error = KVUtils.get().getString(KEY_APP_ERROR_INFO);
        if (!TextUtils.isEmpty(error)) {
            LogUtils.i("上次app崩溃信息：" + error);
            KVUtils.get().putString(KEY_APP_ERROR_INFO, "");
        }
    }

    /**
     * 获取异常信息
     */
    private String getError(Throwable ex) {
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        return writer.toString();
    }
}
