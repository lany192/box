package com.github.lany192.utils;

import android.text.TextUtils;

import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * json工具
 */
public class JsonUtils {

    /**
     * json转对象
     */
    @SuppressWarnings("unchecked")
    public static <T> T json2object(String json, Type type) {
        if (TextUtils.isEmpty(json)) {
            return null;
        }
        try {
            return (T) new Moshi.Builder().build().adapter(type).nullSafe().fromJson(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * json转对象
     */
    public static <T> T json2object(String json, Class<T> cls) {
        if (TextUtils.isEmpty(json)) {
            return null;
        }
        try {
            return new Moshi.Builder().build().adapter(cls).nullSafe().fromJson(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 对象转json
     */
    public static String object2json(Object object) {
        if (object == null) {
            return "";
        }
        return new Moshi.Builder().build().adapter(Object.class).toJson(object);
    }

    /**
     * json转list
     */
    @SuppressWarnings("unchecked")
    public static <T> List<T> json2List(String json, Class<T> clazz) {
        if (TextUtils.isEmpty(json)) {
            return null;
        }
        Type type = Types.newParameterizedType(List.class, clazz);
        try {
            return (List<T>) new Moshi.Builder().build().adapter(type).fromJson(json);
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
}