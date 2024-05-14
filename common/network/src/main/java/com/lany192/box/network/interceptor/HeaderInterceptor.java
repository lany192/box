package com.lany192.box.network.interceptor;

import android.os.Build;

import androidx.annotation.NonNull;

import com.github.lany192.arch.utils.DeviceId;
import com.github.lany192.arch.utils.PhoneUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class HeaderInterceptor implements Interceptor {
    private static final String UA_NAME = "User-Agent";

    @NonNull
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder builder = request.newBuilder();
        builder.removeHeader(UA_NAME);
        builder.addHeader(UA_NAME, getUserAgent());
        builder.addHeader("deviceId", DeviceId.get().getDeviceId());
        builder.addHeader("version", "1.0.0");
        builder.addHeader("token", "xxxxxx");
        builder.addHeader("uid", String.valueOf(0));
        return chain.proceed(builder.build());
    }

    private String getUserAgent() {
        StringBuilder builder = new StringBuilder();
        builder.append("Box");
        builder.append("/");
        builder.append(PhoneUtils.getAppVersionCode());//app版本
        builder.append("(");
        builder.append("android");
        builder.append(Build.VERSION.SDK_INT);//系统版本号
        builder.append(";");
        builder.append(Build.BRAND);//手机品牌
        builder.append(";");
        builder.append(Build.MODEL);//手机型号
        builder.append(";");
        builder.append(Build.ID);
        builder.append(";");
        builder.append(Build.BOOTLOADER);
        builder.append(";");
        builder.append(PhoneUtils.getScreenInfo());//分辨率
        builder.append(")");
        return builder.toString();
    }
}
