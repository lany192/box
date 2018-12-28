package com.lany.box.http;

import android.text.TextUtils;

import com.lany.box.Box;
import com.lany.box.utils.DeviceUtils;
import com.lany.box.utils.ListUtils;
import com.lany.box.utils.PhoneUtils;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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
    private MultipartBody.Builder mBuilder;
    private String url;
    private HashMap<String, String> params = new HashMap<>();

    private static HashMap<String, String> mParams = new HashMap<>();

    public static void addDefaultParams(String key, String value) {
        mParams.put(key, value);
    }

    /**
     * @param addDefault 是否添加默认参数
     */
    private HttpRequest(boolean addDefault) {
        mBuilder = new MultipartBody.Builder();
        mBuilder.setType(MultipartBody.FORM);
        if (addDefault) {
            mParams.put("deviceId", DeviceUtils.getUniqueDeviceId(Box.of().getContext()));
            mParams.put("baseInfo", PhoneUtils.getBaseInfo());
        }
    }

    public static HttpRequest of(String url) {
        return of(url, true);
    }

    public static HttpRequest of(String url, boolean addDefaultParams) {
        HttpRequest body = new HttpRequest(addDefaultParams);
        body.setUrl(url);
        for (String key : mParams.keySet()) {
            body.add(key, mParams.get(key));
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
                mBuilder.addFormDataPart(key, values.get(i));
            }
        }
        return this;
    }

    public HttpRequest addJson(String json) {
        if (TextUtils.isEmpty(json)) {
            return this;
        }
        mBuilder.addPart(FormBody.create(MediaType.parse("application/json; charset=utf-8"), json));
        return this;
    }

    public HttpRequest addImageFile(String key, File file) {
        if (TextUtils.isEmpty(key) || file == null) {
            return this;
        }
        mBuilder.addFormDataPart(key, file.getName(), RequestBody.create(MediaType.parse("image/*"), file));
        return this;
    }

    public HttpRequest addImagePaths(String key, List<String> paths) {
        if (TextUtils.isEmpty(key)) {
            return this;
        }
        if (!ListUtils.isEmpty(paths)) {
            for (int i = 0; i < paths.size(); i++) {
                File file = new File(paths.get(i));
                mBuilder.addFormDataPart(key, file.getName(), RequestBody.create(MediaType.parse("image/*"), file));
            }
        }
        return this;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Observable<String> post(ApiService service) {
        for (Map.Entry<String, String> entry : new TreeMap<>(params).entrySet()) {
            mBuilder.addFormDataPart(entry.getKey(), entry.getValue());
        }
        return service.post(url, mBuilder.build()).compose(new ObservableTransformer<String, String>() {
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
        return service.get(url, new TreeMap<>(params)).compose(new ObservableTransformer<String, String>() {
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
