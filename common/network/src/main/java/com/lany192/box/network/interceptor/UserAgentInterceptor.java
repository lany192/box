package com.lany192.box.network.interceptor;

import android.os.Build;

import androidx.annotation.NonNull;

import com.github.lany192.arch.utils.PhoneUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

public final class UserAgentInterceptor implements Interceptor {
    private static final String USER_AGENT_HEADER_NAME = "User-Agent";

    private final String userAgent;

    public UserAgentInterceptor() {
        userAgent = getUserAgent();
    }

    @NonNull
    @Override
    public Response intercept(Chain chain) throws IOException {
        return chain.proceed(chain.request().newBuilder()
                //移除先前默认的UserAgent
                .removeHeader(USER_AGENT_HEADER_NAME)
                //设置UserAgent
                .addHeader(USER_AGENT_HEADER_NAME, userAgent)
                .build());
    }

    private String getUserAgent() {
        StringBuilder builder = new StringBuilder();
        builder.append("Box");
        builder.append("/");
        builder.append(PhoneUtils.getAppVersionCode());//app版本
        builder.append("(");
        builder.append("android");
        builder.append(";");
        builder.append(Build.BRAND);//手机品牌
        builder.append(";");
        builder.append(Build.MODEL);//手机型号
        builder.append(";");
        builder.append(Build.ID);
        builder.append(";");
        builder.append(Build.BOOTLOADER);
        builder.append(";");
        builder.append(Build.VERSION.RELEASE);//系统版本号
        builder.append(";");
        builder.append(PhoneUtils.getScreenInfo());//分辨率
        builder.append(";");
        builder.append(PhoneUtils.getOSVersionCode());
        builder.append(";");
        builder.append(PhoneUtils.getAppVersionCode());
        builder.append(")");
        return builder.toString();
    }
}
