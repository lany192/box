package com.lany.box.http;

import com.lany.box.BaseApp;
import com.lany.box.utils.ListUtils;
import com.lany.uniqueid.DeviceUtils;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class HttpBody {
    protected MultipartBody.Builder builder;

    public HttpBody() {
        builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);
        builder.addFormDataPart("deviceId", DeviceUtils.getUniqueDeviceId(BaseApp.getContext()));
        builder.addFormDataPart("client", "android");
    }

    public static HttpBody Builder() {
        return new HttpBody();
    }

    public MultipartBody build() {
        return builder.build();
    }

    public HttpBody add(String key, String value) {
        builder.addFormDataPart(key, value);
        return this;
    }

    public HttpBody add(String key, int value) {
        builder.addFormDataPart(key, String.valueOf(value));
        return this;
    }

    public HttpBody add(String key, long value) {
        builder.addFormDataPart(key, String.valueOf(value));
        return this;
    }

    public HttpBody add(String key, double value) {
        builder.addFormDataPart(key, String.valueOf(value));
        return this;
    }

    public HttpBody add(String key, float value) {
        builder.addFormDataPart(key, String.valueOf(value));
        return this;
    }

    public HttpBody addImage(String name, File file) {
        builder.addFormDataPart(name, file.getName(), RequestBody.create(MediaType.parse("image/*"), file));
        return this;
    }

    public HttpBody addImages(String name, List<String> paths) {
        if (!ListUtils.isEmpty(paths)) {
            for (int i = 0; i < paths.size(); i++) {
                File file = new File(paths.get(i));
                builder.addFormDataPart(name, file.getName(), RequestBody.create(MediaType.parse("image/*"), file));
            }
        }
        return this;
    }
}
