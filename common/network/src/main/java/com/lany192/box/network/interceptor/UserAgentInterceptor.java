package com.lany192.box.network.interceptor;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

public final class UserAgentInterceptor implements Interceptor {
    private static final String USER_AGENT_HEADER_NAME = "User-Agent";

    private String mUserAgent = "Android-Box";

    public UserAgentInterceptor() {

    }

    @NonNull
    @Override
    public Response intercept(Chain chain) throws IOException {
        return chain.proceed(chain.request().newBuilder()
                //移除先前默认的UA
                .removeHeader(USER_AGENT_HEADER_NAME)
                //设置UA
                .addHeader(USER_AGENT_HEADER_NAME, mUserAgent)
                .build());
    }
}
