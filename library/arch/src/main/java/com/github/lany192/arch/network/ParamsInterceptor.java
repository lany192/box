package com.github.lany192.arch.network;

import androidx.annotation.NonNull;

import com.github.lany192.arch.utils.DeviceId;
import com.github.lany192.arch.utils.PhoneUtils;

import java.io.IOException;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class ParamsInterceptor implements Interceptor {
    private final Map<String, String> headers;

    public ParamsInterceptor(Map<String, String> headers) {
        this.headers = headers;
    }

    @NonNull
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder builder = request.newBuilder();
        builder.addHeader("User-Agent", PhoneUtils.getBaseInfo());
        builder.addHeader("deviceId", DeviceId.getInstance().getDeviceId());
        builder.addHeader("version", String.valueOf(PhoneUtils.getAppVersionCode()));
        if (headers != null && headers.size() > 0) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                builder.addHeader(entry.getKey(), "" + entry.getValue());
            }
        }
        return chain.proceed(builder.build());
    }
}
