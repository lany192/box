package com.lany192.box.network;

import android.text.TextUtils;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class DomainInterceptor implements Interceptor {
    private static final String DOMAIN_KEY = "domain";

    @NonNull
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder builder = request.newBuilder();
        String domain = request.header(DOMAIN_KEY);
        if (!TextUtils.isEmpty(domain)) {
            builder.removeHeader(DOMAIN_KEY);
            HttpUrl httpUrl = request.url();
            if ("test1".equals(domain)) {
                builder.url(httpUrl.newBuilder()
                        .host("hello1")//更换域名
                        .build());
            } else if ("test2".equals(domain)) {
                builder.url(httpUrl.newBuilder()
                        .host("hello2")//更换域名
                        .build());
            }
        }
        return chain.proceed(builder.build());
    }
}
