package com.github.lany192.arch;

import android.os.Environment;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.github.lany192.arch.utils.FileUtils;
import com.github.lany192.arch.utils.OtherUtils;
import com.github.lany192.log.LogUtils;
import com.github.lany192.log.XLog;
import com.github.lany192.utils.ContextUtils;
import com.github.lany192.utils.KVUtils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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
        KVUtils.putString(KEY_APP_ERROR_INFO, error);
        XLog.tag("app崩溃退出").i(error);
        save2file(error);
    }

    /**
     * 打印app上次崩溃信息，防止崩溃时没有记录到
     */
    private void checkError() {
        String error = KVUtils.getString(KEY_APP_ERROR_INFO);
        if (!TextUtils.isEmpty(error)) {
            LogUtils.i("上次app崩溃信息：" + error);
            KVUtils.putString(KEY_APP_ERROR_INFO, "");
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
        return "基础信息:" + OtherUtils.getBaseInfo() + "\n" + writer;
    }

    private void save2file(String error) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/crash/" + ContextUtils.getContext().getPackageName() + "/";
            String fileName = "crash_" + new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date()) + ".log";
            FileUtils.save2file(dirPath + "/" + fileName, error);
        }
    }
}
