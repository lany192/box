package com.github.lany192.box.utils;


import com.elvishew.xlog.LogLevel;
import com.elvishew.xlog.flattener.Flattener2;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * 日志文件输出格式
 */
public class LogFileFormat implements Flattener2 {
    private final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

    @Override
    public CharSequence flatten(long timeMillis, int logLevel, String tag, String message) {
        return formatter.format(timeMillis)
                + '|' + LogLevel.getShortLevelName(logLevel)
                + '|' + tag
                + '|' + message;
    }
}
