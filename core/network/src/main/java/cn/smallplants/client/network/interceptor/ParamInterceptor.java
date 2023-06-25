package cn.smallplants.client.network.interceptor;

import android.text.TextUtils;

import androidx.annotation.NonNull;

import java.io.IOException;

import cn.smallplants.client.App;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

/**
 * 添加公共参数
 */
public class ParamInterceptor implements Interceptor {

    @NonNull
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (request.body() instanceof FormBody) {
            FormBody.Builder newFormBody = new FormBody.Builder();
            FormBody oldFormBody = (FormBody) request.body();
            for (int i = 0; i < oldFormBody.size(); i++) {
                newFormBody.addEncoded(oldFormBody.encodedName(i), oldFormBody.encodedValue(i));
            }
            newFormBody.add("uid", String.valueOf(App.getUser().getUserId()));
            newFormBody.add("test2", "测试2");

            Request.Builder requestBuilder = request.newBuilder();
            requestBuilder.method(request.method(), newFormBody.build());
            return chain.proceed(requestBuilder.build());
        } else if (request.body() instanceof MultipartBody) {
            MultipartBody oldMultipartBody = (MultipartBody) request.body();


            MultipartBody.Builder builder = new MultipartBody.Builder();
            builder.setType(MultipartBody.FORM);
            for (MultipartBody.Part part : oldMultipartBody.parts()) {
                Headers headers = part.headers();
                RequestBody requestBody = part.body();
                //类型为空标识传的是参数
                if (requestBody.contentType() == null && headers != null) {
                    for (int i = 0; i < headers.names().size(); i++) {
                        String header = headers.value(i);
                        String key = getKeyFormHeader(header);
                        if (!TextUtils.isEmpty(key)) {
                            //加密前的值
                            String value = requestBody2String(part.body());
                            //加密后的值
                            builder.addFormDataPart(key.trim(), value);//去空格
                            break;
                        }
                    }
                } else {
                    builder.addPart(part);
                }
            }
            return chain.proceed(request.newBuilder()
                    .method(request.method(), builder.build())
                    .build());
        } else {
            return chain.proceed(request);
        }
    }

    /**
     * 提取key，
     * header示例:form-data; name="app_version"
     */
    private String getKeyFormHeader(String header) {
        //这个标识在MultipartBody.Part源码中看到
        String replaceValue = "form-data; name=";
        if (header.contains(replaceValue)) {
            //获取带引号的name
            String key = header.replace(replaceValue, "");
            //去除引号
            key = key.replaceAll("\"", "");
            return key;
        }
        return "";
    }

    /**
     * 提取value
     */
    private String requestBody2String(RequestBody requestBody) throws IOException {
        Buffer requestBuffer = new Buffer();
        requestBody.writeTo(requestBuffer);
        String value = requestBuffer.readUtf8();
        requestBuffer.close();
        return value;
    }
}
