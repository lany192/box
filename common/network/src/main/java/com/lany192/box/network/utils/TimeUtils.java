package com.lany192.box.network.utils;

import com.github.lany192.utils.KVUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class TimeUtils {
    public static final String KEY_TIME_INTERVAL = "time_interval";

    /**
     * 获取当前时间戳，已经经过服务器时间校正后的
     * （不精准，有大于1秒的误差，时间精度要求不高的情况可以用）
     */
    public static long currentTimeMillis() {
        long interval = KVUtils.getLong(KEY_TIME_INTERVAL);
        return System.currentTimeMillis() - interval;
    }

    /**
     * 解析GMT时间
     */
    public static long parseGMTToMillis(String gmtTime) {
        SimpleDateFormat formatter = new SimpleDateFormat("EEE, dd MMM y HH:mm:ss 'GMT'", Locale.US);
        formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
        try {
            Date date = formatter.parse(gmtTime);
            if (date != null) {
                return date.getTime();
            }
        } catch (ParseException e) {
            e.fillInStackTrace();
        }
        return 0;
    }
}
