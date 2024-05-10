package com.lany192.box.network;

import androidx.annotation.NonNull;

import com.github.lany192.arch.utils.ListUtils;
import com.github.lany192.log.XLog;
import com.github.lany192.utils.KVUtils;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import okhttp3.Interceptor;
import okhttp3.Response;

public class TimeIntervalInterceptor implements Interceptor {
    private final XLog log = XLog.tag(getClass().getSimpleName());
    public static final String KEY_TIME_INTERVAL = "time_interval";

    @NonNull
    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());
        parseServerTimeByResponse(response);
        return response;
    }

    private void parseServerTimeByResponse(Response response) {
        List<String> dates = response.headers("Date");
        if (ListUtils.isEmpty(dates)) {
            dates = response.headers("date");
        }
        if (ListUtils.isEmpty(dates)) {
            dates = response.headers("DATE");
        }
        long currentServerMillis = System.currentTimeMillis();
        if (!ListUtils.isEmpty(dates)) {
            String date = dates.get(0);
            try {
                currentServerMillis = parseGMTToMillis(date);
            } catch (Exception e) {
                e.fillInStackTrace();
            }
        }
        long timeInterval = System.currentTimeMillis() - currentServerMillis;
        log.i("时间测试,获取的时间信息: " + dates + ",interval: " + timeInterval);
        KVUtils.putLong(KEY_TIME_INTERVAL, timeInterval);
    }

    private long parseGMTToMillis(String gmtTime) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("EEE, dd MMM y HH:mm:ss 'GMT'", Locale.US);
        formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
        Date date = formatter.parse(gmtTime);
        if (date != null) {
            return date.getTime();
        }
        return System.currentTimeMillis();
    }
}
