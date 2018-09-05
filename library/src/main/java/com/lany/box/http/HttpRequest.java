package com.lany.box.http;

import com.lany.box.BaseApp;
import com.lany.box.utils.ListUtils;
import com.lany.uniqueid.DeviceUtils;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class HttpRequest {
    private MultipartBody.Builder builder;
    private String url;
    private HashMap<String, String> params = new HashMap<>();

    private static HashMap<String, String> defaultParams = new HashMap<>();

    {
        defaultParams.put("deviceId", DeviceUtils.getUniqueDeviceId(BaseApp.getContext()));
        defaultParams.put("client", "android");
    }

    public static void addDefaultParams(String key, String value) {
        defaultParams.put(key, value);
    }

    private HttpRequest() {
        builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);
    }

    public static HttpRequest of(String url) {
        HttpRequest body = new HttpRequest();
        body.setUrl(url);
        for (String key : defaultParams.keySet()) {
            body.add(key, defaultParams.get(key));
        }
        return body;
    }

    public HttpRequest url(String url) {
        this.url = url;
        return this;
    }

    public HttpRequest add(String key, String value) {
        params.put(key, value);
        return this;
    }

    public HttpRequest add(String key, int value) {
        params.put(key, String.valueOf(value));
        return this;
    }

    public HttpRequest add(String key, long value) {
        params.put(key, String.valueOf(value));
        return this;
    }

    public HttpRequest add(String key, double value) {
        params.put(key, String.valueOf(value));
        return this;
    }

    public HttpRequest add(String key, float value) {
        params.put(key, String.valueOf(value));
        return this;
    }

    public HttpRequest addJson(String json) {
        builder.addPart(FormBody.create(MediaType.parse("application/json; charset=utf-8"), json));
        return this;
    }

    public HttpRequest addImageFile(String name, File file) {
        builder.addFormDataPart(name, file.getName(), RequestBody.create(MediaType.parse("image/*"), file));
        return this;
    }

    public HttpRequest addImagePaths(String name, List<String> paths) {
        if (!ListUtils.isEmpty(paths)) {
            for (int i = 0; i < paths.size(); i++) {
                File file = new File(paths.get(i));
                builder.addFormDataPart(name, file.getName(), RequestBody.create(MediaType.parse("image/*"), file));
            }
        }
        return this;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Observable<String> post(ApiService service) {
        for (Map.Entry<String, String> entry : params.entrySet()) {
            builder.addFormDataPart(entry.getKey(), entry.getValue());
        }
        return service.post(url, builder.build()).compose(observable ->
                observable
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io()));
    }

    public Observable<String> get(ApiService service) {
        return service.get(url, params).compose(observable ->
                observable
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io()));
    }
}
