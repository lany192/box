package com.github.lany192.arch.network;

import androidx.annotation.NonNull;

import com.elvishew.xlog.XLog;

import java.io.EOFException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

import okhttp3.Connection;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.http.HttpHeaders;
import okio.Buffer;
import okio.BufferedSource;
import okio.GzipSource;

public class HttpLogInterceptor implements Interceptor {
    private final boolean debug;

    public HttpLogInterceptor(boolean debug) {
        this.debug = debug;
    }

    private static boolean bodyHasUnknownEncoding(Headers headers) {
        String contentEncoding = headers.get("Content-Encoding");
        return contentEncoding != null
                && !contentEncoding.equalsIgnoreCase("identity")
                && !contentEncoding.equalsIgnoreCase("gzip");
    }

    @NonNull
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (!debug) {
            return chain.proceed(request);
        }
        printRequestInfo(chain);
        return printResponseInfo(chain);
    }

    private void printRequestInfo(Chain chain) throws IOException {
        Request request = chain.request();
        RequestBody requestBody = request.body();
        boolean hasRequestBody = requestBody != null;

        Connection connection = chain.connection();
        XLog.i("--> 请求开始：" + request.method() + ' ' + request.url() + (connection != null ? " " + connection.protocol() : ""));

        if (hasRequestBody) {
            // Request body requestHeaders are only present when installed as a network interceptor. Force
            // them to be included (when available) so there values are known.
            if (requestBody.contentType() != null) {
                XLog.i("Content-Type: " + requestBody.contentType());
            }
            if (requestBody.contentLength() != -1) {
                XLog.i("Content-Length: " + requestBody.contentLength());
            }
        }

        Headers requestHeaders = request.headers();
        for (int i = 0, count = requestHeaders.size(); i < count; i++) {
            String name = requestHeaders.name(i);
            // Skip requestHeaders from the request body as they are explicitly logged above.
            if (!"Content-Type".equalsIgnoreCase(name) && !"Content-Length".equalsIgnoreCase(name)) {
                XLog.i(requestHeaders.name(i) + ": " + requestHeaders.value(i));
            }
        }

        if (!hasRequestBody) {
            XLog.i("--> END " + request.method());
        } else if (bodyHasUnknownEncoding(request.headers())) {
            XLog.i("--> END " + request.method() + " (encoded body omitted)");
        } else if (requestBody.isDuplex()) {
            XLog.i("--> END " + request.method() + " (duplex request body omitted)");
        } else {
            Buffer buffer = new Buffer();
            requestBody.writeTo(buffer);

            Charset charset = StandardCharsets.UTF_8;
            MediaType contentType = requestBody.contentType();
            if (contentType != null) {
                charset = contentType.charset(StandardCharsets.UTF_8);
            }

            XLog.i("");
            if (isPlaintext(buffer)) {
                XLog.i(buffer.readString(charset));
                XLog.i("--> END " + request.method() + " (" + requestBody.contentLength() + "-byte body)");
            } else {
                XLog.i("--> END " + request.method() + " (binary " + requestBody.contentLength() + "-byte body omitted)");
            }
        }
    }

    private Response printResponseInfo(Chain chain) throws IOException {
        long startNs = System.nanoTime();
        Response response;
        try {
            response = chain.proceed(chain.request());
        } catch (Exception e) {
            XLog.i("<-- 请求失败: " + e);
            throw e;
        }
        long tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs);

        ResponseBody responseBody = response.body();
        long contentLength = responseBody.contentLength();
        String bodySize = contentLength != -1 ? contentLength + "-byte" : "unknown-length";
        XLog.i("<-- "
                + response.code()
                + (response.message().isEmpty() ? "" : ' ' + response.message())
                + ' ' + response.request().url()
                + " (" + tookMs + "ms" + "" + ')');

        Headers responseHeaders = response.headers();
        for (int i = 0, count = responseHeaders.size(); i < count; i++) {
            XLog.i(responseHeaders.name(i) + ": " + responseHeaders.value(i));
        }

        if (!HttpHeaders.hasBody(response)) {
            XLog.i("<-- 请求结束");
        } else if (bodyHasUnknownEncoding(response.headers())) {
            XLog.i("<-- 请求结束 (encoded body omitted)");
        } else {
            BufferedSource source = responseBody.source();
            source.request(Long.MAX_VALUE); // Buffer the entire body.
            Buffer buffer = source.getBuffer();

            Long gzippedLength = null;
            if ("gzip".equalsIgnoreCase(responseHeaders.get("Content-Encoding"))) {
                gzippedLength = buffer.size();
                try (GzipSource gzippedResponseBody = new GzipSource(buffer.clone())) {
                    buffer = new Buffer();
                    buffer.writeAll(gzippedResponseBody);
                }
            }
            Charset charset = StandardCharsets.UTF_8;
            MediaType contentType = responseBody.contentType();
            if (contentType != null) {
                charset = contentType.charset(StandardCharsets.UTF_8);
            }
            if (!isPlaintext(buffer)) {
                XLog.i("");
                XLog.i("<-- 请求结束 (binary " + buffer.size() + "-byte body omitted)");
                return response;
            }
            if (contentLength != 0) {
                XLog.i("请求结果：");
                String result = buffer.clone().readString(charset);
                XLog.json(result);
            }
            if (gzippedLength != null) {
                XLog.i("<-- 请求结束 (" + buffer.size() + "-byte, " + gzippedLength + "-gzipped-byte body)");
            } else {
                XLog.i("<-- 请求结束 (" + buffer.size() + "-byte body)");
            }
        }
        return response;
    }

    /**
     * Returns true if the body in question probably contains human readable text. Uses a small sample
     * of code points to detect unicode control characters commonly used in binary file signatures.
     */
    private boolean isPlaintext(Buffer buffer) {
        try {
            Buffer prefix = new Buffer();
            long byteCount = buffer.size() < 64 ? buffer.size() : 64;
            buffer.copyTo(prefix, 0, byteCount);
            for (int i = 0; i < 16; i++) {
                if (prefix.exhausted()) {
                    break;
                }
                int codePoint = prefix.readUtf8CodePoint();
                if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                    return false;
                }
            }
            return true;
        } catch (EOFException e) {
            return false; // Truncated UTF-8 sequence.
        }
    }
}