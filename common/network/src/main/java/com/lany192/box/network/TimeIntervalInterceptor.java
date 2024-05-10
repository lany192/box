package com.lany192.box.network;

import androidx.annotation.NonNull;

import com.github.lany192.arch.utils.ListUtils;
import com.github.lany192.log.XLog;
import com.github.lany192.utils.KVUtils;
import com.lany192.box.network.utils.TimeUtils;

import java.io.IOException;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * 获取本地时间和服务器时间的时间差（有1到2秒左右的误差）
 */
public class TimeIntervalInterceptor implements Interceptor {
    private final XLog log = XLog.tag(getClass().getSimpleName());

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
            currentServerMillis = TimeUtils.parseGMTToMillis(date);
        }
        if (currentServerMillis == 0) {
            return;
        }
        long timeInterval = System.currentTimeMillis() - currentServerMillis;
        log.i("时间测试,获取的时间信息: " + dates + ",interval: " + timeInterval);
        KVUtils.putLong(TimeUtils.KEY_TIME_INTERVAL, timeInterval);
    }
}
