package com.lany.box.http;

import android.text.TextUtils;

import com.lany.box.Box;
import com.lany.box.utils.ListUtils;
import com.lany.box.utils.PhoneUtils;
import com.lany.uniqueid.DeviceUtils;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
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
        defaultParams.put("deviceId", DeviceUtils.getUniqueDeviceId(Box.of().getContext()));
        defaultParams.put("baseInfo", PhoneUtils.getBaseInfo());
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
        if (TextUtils.isEmpty(key)) {
            return this;
        }
        params.put(key, TextUtils.isEmpty(value) ? "" : value);
        return this;
    }

    public HttpRequest add(String key, int value) {
        if (TextUtils.isEmpty(key)) {
            return this;
        }
        params.put(key, String.valueOf(value));
        return this;
    }

    public HttpRequest add(String key, long value) {
        if (TextUtils.isEmpty(key)) {
            return this;
        }
        params.put(key, String.valueOf(value));
        return this;
    }

    public HttpRequest add(String key, double value) {
        if (TextUtils.isEmpty(key)) {
            return this;
        }
        params.put(key, String.valueOf(value));
        return this;
    }

    public HttpRequest add(String key, float value) {
        if (TextUtils.isEmpty(key)) {
            return this;
        }
        params.put(key, String.valueOf(value));
        return this;
    }

    public HttpRequest add(String key, boolean value) {
        if (TextUtils.isEmpty(key)) {
            return this;
        }
        params.put(key, String.valueOf(value));
        return this;
    }

    public HttpRequest addList(String key, List<String> values) {
        if (TextUtils.isEmpty(key)) {
            return this;
        }
        if (!ListUtils.isEmpty(values)) {
            for (int i = 0; i < values.size(); i++) {
                builder.addFormDataPart(key, values.get(i));
            }
        }
        return this;
    }

    public HttpRequest addJson(String json) {
        if (TextUtils.isEmpty(json)) {
            return this;
        }
        builder.addPart(FormBody.create(MediaType.parse("application/json; charset=utf-8"), json));
        return this;
    }

    public HttpRequest addImageFile(String key, File file) {
        if (TextUtils.isEmpty(key) || file == null) {
            return this;
        }
        builder.addFormDataPart(key, file.getName(), RequestBody.create(MediaType.parse("image/*"), file));
        return this;
    }

    public HttpRequest addImagePaths(String key, List<String> paths) {
        if (TextUtils.isEmpty(key)) {
            return this;
        }
        if (!ListUtils.isEmpty(paths)) {
            for (int i = 0; i < paths.size(); i++) {
                File file = new File(paths.get(i));
                builder.addFormDataPart(key, file.getName(), RequestBody.create(MediaType.parse("image/*"), file));
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
        return service.post(url, builder.build()).compose(new ObservableTransformer<String, String>() {
            @Override
            public ObservableSource<String> apply(Observable<String> observable) {
                return observable
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io());
            }
        });
    }

    public Observable<String> get(ApiService service) {
        return service.get(url, params).compose(new ObservableTransformer<String, String>() {
            @Override
            public ObservableSource<String> apply(Observable<String> observable) {
                return observable
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io());
            }
        });
    }
}
