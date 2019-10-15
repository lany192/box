package com.github.lany192.box.utils;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class LogUtils {
    private final String TAG = this.getClass().getSimpleName();

    /**
     * 根据日期读取日志内容
     */
    public String getLogPathByDate(Context context, Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String fileName = sdf.format(date);

        File cacheDir = context.getExternalCacheDir();
        if (cacheDir == null || !cacheDir.exists()) {
            return "没有找到缓存目录";
        }
        String path = cacheDir.getPath() + "/XLog/" + fileName + ".log";
        File file = new File(path);
        if (!file.exists()) {
            return "没有找到相关日志文件：" + path;
        }
        FileInputStream inputStream = null;
        StringBuilder sb = new StringBuilder();
        try {
            inputStream = context.openFileInput(path);
            byte[] buffer = new byte[10240];
            while (true) {
                int len = inputStream.read(buffer);
                if (len <= 0) {
                    break;
                }
                sb.append(new String(buffer, 0, len));
            }
            inputStream.close();
        } catch (FileNotFoundException e) {
            return "未找到对应的日志文件";
        } catch (Exception e) {
            Log.e(TAG, "获取日志文本异常", e);
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw, true));
            return sw.toString();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    Log.e(TAG, "获取日志文本异常", e);
                }
            }
        }
        return sb.toString();
    }
}
