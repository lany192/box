package com.github.lany192.utils;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * json工具
 */
public class JsonUtils {

    /**
     * json转对象
     */
    public static <T> T json2object(String json, Type type) {
        if (TextUtils.isEmpty(json)) {
            return null;
        }
        T t = null;
        try {
            t = new Gson().fromJson(json, type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }

    /**
     * json转对象
     */
    public static <T> T json2object(String json, Class<T> cls) {
        if (TextUtils.isEmpty(json)) {
            return null;
        }
        T t = null;
        try {
            t = new Gson().fromJson(json, cls);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }

    /**
     * 对象转json
     */
    public static String object2json(Object object) {
        if (object == null) {
            return "";
        }
        return new Gson().toJson(object);
    }

    /**
     * json转list
     */
    public static <T> List<T> json2List(String json, Class<T[]> clazz) {
        if (TextUtils.isEmpty(json)) {
            return null;
        }
        Gson gson = new Gson();
        T[] array = gson.fromJson(json, clazz);
        return Arrays.asList(array);
    }

    /**
     * json转ArrayList
     */
    public static <T> ArrayList<T> json2ArrayList(String json, Class<T> clazz) {
        if (TextUtils.isEmpty(json)) {
            return null;
        }
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<JsonObject>>() {
        }.getType();
        ArrayList<JsonObject> jsonObjects = gson.fromJson(json, type);
        ArrayList<T> arrayList = new ArrayList<>();
        for (JsonObject jsonObject : jsonObjects) {
            arrayList.add(gson.fromJson(jsonObject, clazz));
        }
        return arrayList;
    }
}