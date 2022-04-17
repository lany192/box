package com.github.lany192.arch.network;

import androidx.annotation.NonNull;

import com.github.lany192.arch.utils.DeviceId;
import com.github.lany192.arch.utils.PhoneUtils;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class ParamsInterceptor implements Interceptor {
    private HashMap<String, Object> headers = new HashMap<>();

    public ParamsInterceptor() {
    }

    public ParamsInterceptor(HashMap<String, Object> headers) {
        this.headers = headers;
    }

    @NonNull
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder builder = request.newBuilder();
        builder.addHeader("Content-Type", "application/json;charset=UTF-8");
        builder.addHeader("User-Agent", PhoneUtils.getBaseInfo());
        builder.addHeader("deviceId", DeviceId.get().getDeviceId());
        builder.addHeader("version", String.valueOf(PhoneUtils.getAppVersionCode()));
        builder.addHeader("client", "android");
        if (headers != null && headers.size() > 0) {
//            builder.addHeader("token", UserHelper.get().getToken());
        }
        return chain.proceed(builder.build());
    }
}
