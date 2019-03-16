package com.lany.box.http;

import android.text.TextUtils;

import com.lany.box.Box;
import com.lany.box.utils.DeviceUtils;
import com.lany.box.utils.ListUtils;
import com.lany.box.utils.PhoneUtils;

import java.io.File;
import java.lang.reflect.Field;
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
    private String mApiUrl;
    private TreeMap<String, String> mParams = new TreeMap<>();

    private HttpRequest(String apiUrl, boolean addDefault) {
        mBuilder = new MultipartBody.Builder();
        mBuilder.setType(MultipartBody.FORM);

        mApiUrl = apiUrl;

        if (addDefault) {
            add("deviceId", DeviceUtils.getUniqueDeviceId(Box.of().getContext()));
            add("baseInfo", PhoneUtils.getBaseInfo());
        }
    }

    public static HttpRequest of(String apiUrl) {
        return of(apiUrl, true);
    }

    public static HttpRequest of(String apiUrl, boolean addDefault) {
        return new HttpRequest(apiUrl, addDefault);
    }

    public HttpRequest api(String apiUrl) {
        this.mApiUrl = apiUrl;
        return this;
    }

    public HttpRequest add(String key, String value) {
        if (TextUtils.isEmpty(key)) {
            return this;
        }
        mParams.put(key, TextUtils.isEmpty(value) ? "" : value);
        return this;
    }

    public HttpRequest add(String key, int value) {
        if (TextUtils.isEmpty(key)) {
            return this;
        }
        mParams.put(key, String.valueOf(value));
        return this;
    }

    public HttpRequest add(String key, long value) {
        if (TextUtils.isEmpty(key)) {
            return this;
        }
        mParams.put(key, String.valueOf(value));
        return this;
    }

    public HttpRequest add(String key, double value) {
        if (TextUtils.isEmpty(key)) {
            return this;
        }
        mParams.put(key, String.valueOf(value));
        return this;
    }

    public HttpRequest add(String key, float value) {
        if (TextUtils.isEmpty(key)) {
            return this;
        }
        mParams.put(key, String.valueOf(value));
        return this;
    }

    public HttpRequest add(String key, boolean value) {
        if (TextUtils.isEmpty(key)) {
            return this;
        }
        mParams.put(key, String.valueOf(value));
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

    public HttpRequest addImage(String key, File file) {
        if (TextUtils.isEmpty(key) || file == null) {
            return this;
        }
        mBuilder.addFormDataPart(key, file.getName(), RequestBody.create(MediaType.parse("image/*"), file));
        return this;
    }

    public HttpRequest addImage(String key, List<String> filePaths) {
        if (TextUtils.isEmpty(key)) {
            return this;
        }
        if (!ListUtils.isEmpty(filePaths)) {
            for (int i = 0; i < filePaths.size(); i++) {
                File file = new File(filePaths.get(i));
                mBuilder.addFormDataPart(key, file.getName(), RequestBody.create(MediaType.parse("image/*"), file));
            }
        }
        return this;
    }

    public HttpRequest addObject(Object object) {
        if (object != null) {
            Map<String, String> fields = object2map(object);
            if (!fields.isEmpty()) {
                for (Map.Entry<String, String> entry : fields.entrySet()) {
                    mParams.put(entry.getKey(), entry.getValue());
                }
            }
        }
        return this;
    }

    /**
     * 对象转map
     */
    private Map<String, String> object2map(Object obj) {
        if (obj == null) {
            return new HashMap<>();
        }
        Map<String, String> map = new HashMap<>();
        // 获取f对象对应类中的所有属性域
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            String fieldName = field.getName();
            try {
                // 获取原来的访问控制权限
                boolean accessFlag = field.isAccessible();
                // 修改访问控制权限
                field.setAccessible(true);
                // 获取在对象f中属性fields[i]对应的对象中的变量
                Object value = field.get(obj);
                if (value != null) {
                    map.put(fieldName, value.toString());
                }
                // 恢复访问控制权限
                field.setAccessible(accessFlag);
            } catch (IllegalArgumentException ex) {
                ex.printStackTrace();
            } catch (IllegalAccessException ex) {
                ex.printStackTrace();
            }
        }
        return map;
    }

    public Observable<String> post(ApiService service) {
        if (mParams != null && mParams.size() > 0) {
            for (Map.Entry<String, String> entry : mParams.entrySet()) {
                mBuilder.addFormDataPart(entry.getKey(), entry.getValue());
            }
        } else {
            //至少要有一个Part，不然会报错
            mBuilder.addPart(MultipartBody.Part.createFormData("", ""));
        }
        return service.post(mApiUrl, mBuilder.build()).compose(new ObservableTransformer<String, String>() {
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
        return service.get(mApiUrl, mParams).compose(new ObservableTransformer<String, String>() {
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
