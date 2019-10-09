package com.github.lany192.box.utils;

import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class LogUtils {

    /**
     * 根据日期读取日志内容
     */
    public static String getLogFilePathByDate(Context context, Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String fileName = sdf.format(date);
        return context.getCacheDir().getPath() + "/XLog/" + fileName;
    }
}
