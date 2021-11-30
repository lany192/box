package com.github.lany192.log;

import com.elvishew.xlog.printer.file.naming.FileNameGenerator;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class LogFileNameGenerator implements FileNameGenerator {

    private final ThreadLocal<SimpleDateFormat> localDateFormat = new ThreadLocal<SimpleDateFormat>() {

        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        }
    };

    @Override
    public boolean isFileNameChangeable() {
        return true;
    }

    /**
     * Generate a file name which represent a specific date.
     */
    @Override
    public String generateFileName(int logLevel, long timestamp) {
        SimpleDateFormat sdf = localDateFormat.get();
        sdf.setTimeZone(TimeZone.getDefault());
        return "app_log_" + sdf.format(new Date(timestamp)) + ".log";
    }
}
