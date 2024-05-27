package com.lany192.box.network.interceptor;

import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.github.lany192.log.XLog;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class DomainInterceptor implements Interceptor {
    private static final String DOMAIN_KEY = "domain";
    private final XLog log = XLog.tag(getClass().getSimpleName());

    @NonNull
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        String domain = request.header(DOMAIN_KEY);
        if (TextUtils.isEmpty(domain)) {
            return chain.proceed(request);
        }
        Request.Builder builder = request.newBuilder();
        log.i("需要动态替换域名：%s", domain);
        builder.removeHeader(DOMAIN_KEY);
        HttpUrl newHttpUrl = null;
        switch (domain.toLowerCase()) {
            case "oss":
                newHttpUrl = HttpUrl.parse("https://xzwcn.oss-cn-shanghai.aliyuncs.com");
                break;
            case "test1":
                //newHttpUrl = HttpUrl.parse("https://demo.oss-cn-shanghai.aliyuncs.com");
                break;
            case "test2":
                newHttpUrl = HttpUrl.parse("https://fssdd.oss-cn-shanghai.aliyuncs.com");
                break;
            default:
                newHttpUrl = HttpUrl.parse("https://www.wanandroid.com");
                break;
        }
        if (newHttpUrl != null) {
            builder.url(newHttpUrl);
        }
        return chain.proceed(builder.build());
    }
}
