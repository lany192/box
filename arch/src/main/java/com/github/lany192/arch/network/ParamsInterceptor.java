package com.github.lany192.arch.network;

import androidx.annotation.NonNull;

import com.github.lany192.arch.utils.DeviceId;
import com.github.lany192.arch.utils.PhoneUtils;
import com.github.lany192.arch.utils.UserInfo;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class ParamsInterceptor implements Interceptor {
    private final UserInfo userInfo;

    public ParamsInterceptor(UserInfo userInfo) {
        this.userInfo = userInfo;
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
        if (userInfo != null) {
            builder.addHeader("token", userInfo.getToken());
            builder.addHeader("uid", String.valueOf(userInfo.getUserId()));
        }
        return chain.proceed(builder.build());
    }
}
