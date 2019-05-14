package com.lany.box.http;

import android.text.TextUtils;

import com.lany.box.utils.JsonUtils;
import com.lany.box.utils.ListUtils;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class Request {
    private String apiUrl;
    private TreeMap<String, String> params = new TreeMap<>();
    private List<MultipartBody.Part> parts = new ArrayList<>();
    private ApiService apiService;

    private Request(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public static Request of(String apiUrl) {
        return new Request(apiUrl);
    }

    public Request service(ApiService apiService) {
        this.apiService = apiService;
        return this;
    }

    public Request add(String key, Object value) {
        if (TextUtils.isEmpty(key) || value == null) {
            return this;
        }
        if (isBaseType(value)) {
            params.put(key, String.valueOf(value));
        } else {
            if (value instanceof List) {
                List<String> list = (List<String>) value;
                if (!ListUtils.isEmpty(list)) {
                    for (String item : list) {
                        params.put(key, item);
                    }
                }
            } else if (value instanceof File) {
                File file = (File) value;
                parts.add(MultipartBody.Part.createFormData(key, file.getName(), RequestBody.create(MediaType.parse("image/*"), file)));
            } else {
                params.put(key, JsonUtils.object2json(value));
            }
        }
        return this;
    }

    /**
     * 添加图片
     *
     * @param key   名称
     * @param paths 图片地址集
     * @return HttpRequest
     */
    public Request addPaths(String key, List<String> paths) {
        if (TextUtils.isEmpty(key)) {
            return this;
        }
        if (!ListUtils.isEmpty(paths)) {
            for (String path : paths) {
                add(key, new File(path));
            }
        }
        return this;
    }

    public Request add(Object object) {
        if (object != null) {
            Map<String, Object> fields = object2map(object);
            if (!fields.isEmpty()) {
                for (Map.Entry<String, Object> entry : fields.entrySet()) {
                    add(entry.getKey(), entry.getValue());
                }
            }
        }
        return this;
    }

    /**
     * 对象转map
     */
    private Map<String, Object> object2map(Object obj) {
        if (obj == null) {
            return new HashMap<>();
        }
        Map<String, Object> map = new HashMap<>();
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
                    map.put(fieldName, value);
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

    /**
     * 判断object是否为基本类型
     */
    private boolean isBaseType(Object object) {
        Class clz = object.getClass();
        return clz.equals(String.class)
                || clz.equals(Integer.class)
                || clz.equals(Byte.class)
                || clz.equals(Long.class)
                || clz.equals(Double.class)
                || clz.equals(Float.class)
                || clz.equals(Character.class)
                || clz.equals(Short.class)
                || clz.equals(Boolean.class);
    }

    public Observable<String> post() {
        if (apiService == null) {
            throw new IllegalStateException("ApiService is null");
        }

        MultipartBody.Builder builder = new MultipartBody.Builder();
        if (params != null && params.size() > 0) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.addFormDataPart(entry.getKey(), entry.getValue());
            }
        } else {
            //至少要有一个Part，不然会报错
            builder.addPart(MultipartBody.Part.createFormData("", ""));
        }
        if (parts != null && parts.size() > 0) {
            for (MultipartBody.Part part : parts) {
                builder.addPart(part);
            }
        }
        builder.setType(MultipartBody.FORM);
        return apiService
                .post(apiUrl, builder.build())
                .compose(observable -> observable
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io()));
    }

    public Observable<String> get() {
        if (apiService == null) {
            throw new IllegalStateException("ApiService is null");
        }
        return apiService
                .get(apiUrl, params)
                .compose(observable -> observable
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io()));
    }
}
