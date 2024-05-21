package com.github.lany192.utils;

import android.content.Context;
import android.text.TextUtils;

import com.tencent.mmkv.MMKV;
import com.tencent.mmkv.MMKVLogLevel;

import java.util.HashSet;
import java.util.Set;

/**
 * MMKV封装工具类
 */
public final class KVUtils {
    /**
     * 秘钥,配置默认秘钥，可修改
     */
    private static String mmkvCryptKey = "";

    private static MMKV defaultMMKV;

    private KVUtils() {
    }

    /**
     * 初始化
     */
    public static void init(Context context, String cryptKey) {
        mmkvCryptKey = cryptKey;
        MMKV.initialize(context);
    }

    /**
     * 日志等级
     */
    public static void setLogLevel(MMKVLogLevel level) {
        MMKV.setLogLevel(level);
    }

    private static MMKV getMMKV() {
        return getMMKV(null);
    }

    private static MMKV getMMKV(String mapId) {
        if (TextUtils.isEmpty(mapId) && defaultMMKV != null) {
            return defaultMMKV;
        } else {
            if (TextUtils.isEmpty(mapId)) {
                defaultMMKV = MMKV.defaultMMKV(MMKV.MULTI_PROCESS_MODE, mmkvCryptKey);
                return defaultMMKV;
            } else {
                return MMKV.mmkvWithID(mapId, MMKV.MULTI_PROCESS_MODE, mmkvCryptKey);
            }
        }
    }

    //String Set类型---------------------------------------------------------------------------------
    public static Set<String> getStringSet(String key) {
        return getMMKV().getStringSet(key, new HashSet<>());
    }

    public static Set<String> getStringSet(String key, Set<String> defaultValues) {
        return getMMKV().getStringSet(key, defaultValues);
    }

    public static Set<String> getStringSet(String mapId, String key, Set<String> defaultValues) {
        return getMMKV(mapId).getStringSet(key, defaultValues);
    }

    public static void putStringSet(String key, Set<String> values) {
        getMMKV().putStringSet(key, values);
    }

    public static void putStringSet(String mapId, String key, Set<String> values) {
        getMMKV(mapId).putStringSet(key, values);
    }

    //Double类型---------------------------------------------------------------------------------
    public static void putDouble(String key, double value) {
        getMMKV().encode(key, value);
    }

    public static double getDouble(String key) {
        return getMMKV().decodeDouble(key);
    }

    public static double getDouble(String key, double defaultValue) {
        return getMMKV().decodeDouble(key, defaultValue);
    }

    public static double getDouble(String mapId, String key, double defaultValue) {
        return getMMKV(mapId).decodeDouble(key, defaultValue);
    }
    //byte[]类型---------------------------------------------------------------------------------

    public static void putByte(String key, byte[] value) {
        getMMKV().encode(key, value);
    }

    public static byte[] getBytes(String key) {
        return getMMKV().decodeBytes(key);
    }

    public static byte[] getBytes(String key, byte[] defaultValue) {
        return getMMKV().decodeBytes(key, defaultValue);
    }

    public static byte[] getBytes(String mapId, String key, byte[] defaultValue) {
        return getMMKV(mapId).decodeBytes(key, defaultValue);
    }
    //String类型---------------------------------------------------------------------------------

    public static String getString(String key) {
        return getMMKV().getString(key, "");
    }

    public static String getString(String key, String defaultValue) {
        return getMMKV().getString(key, defaultValue);
    }

    public static String getString(String mapId, String key, String defaultValue) {
        return getMMKV(mapId).getString(key, defaultValue);
    }

    public static void putString(String key, String value) {
        getMMKV().putString(key, value);
    }

    public static void putString(String mapId, String key, String value) {
        getMMKV(mapId).putString(key, value);
    }

    //Boolean类型-----------------------------------------------------------------------------------------------
    public static boolean getBoolean(String key) {
        return getMMKV().getBoolean(key, false);
    }

    public static boolean getBoolean(String key, boolean defaultValue) {
        return getMMKV().getBoolean(key, defaultValue);
    }

    public static boolean getBoolean(String mapId, String key, boolean defaultValue) {
        return getMMKV(mapId).getBoolean(key, defaultValue);
    }

    public static void putBoolean(String key, boolean value) {
        getMMKV().putBoolean(key, value);
    }

    public static void putBoolean(String mapId, String key, boolean value) {
        getMMKV(mapId).putBoolean(key, value);
    }

    //Int类型-----------------------------------------------------------------------------------------------
    public static void putInt(String key, int value) {
        getMMKV().putInt(key, value);
    }

    public static void putInt(String mapId, String key, int value) {
        getMMKV(mapId).putInt(key, value);
    }

    public static int getInt(String key) {
        return getMMKV().getInt(key, 0);
    }

    public static int getInt(String key, int defaultValue) {
        return getMMKV().getInt(key, defaultValue);
    }

    public static int getInt(String mapId, String key, int defaultValue) {
        return getMMKV(mapId).getInt(key, defaultValue);
    }

    //Float类型-----------------------------------------------------------------------------------------------
    public static void putFloat(String key, float value) {
        getMMKV().putFloat(key, value);
    }

    public static void putFloat(String mapId, String key, float value) {
        getMMKV(mapId).putFloat(key, value);
    }

    public static float getFloat(String key) {
        return getMMKV().getFloat(key, 0f);
    }

    public static float getFloat(String key, float defaultValue) {
        return getMMKV().getFloat(key, defaultValue);
    }

    public static float getFloat(String mapId, String key, float defaultValue) {
        return getMMKV(mapId).getFloat(key, defaultValue);
    }

    //Long类型-----------------------------------------------------------------------------------------------
    public static void putLong(String key, long value) {
        getMMKV().putLong(key, value);
    }

    public static void putLong(String mapId, String key, long value) {
        getMMKV(mapId).putLong(key, value);
    }

    public static long getLong(String key) {
        return getMMKV().getLong(key, 0L);
    }

    public static long getLong(String key, long defaultValue) {
        return getMMKV().getLong(key, defaultValue);
    }

    public static long getLong(String mapId, String key, long defaultValue) {
        return getMMKV(mapId).getLong(key, defaultValue);
    }

    //其他方法-----------------------------------------------------------------------------------------------
    public static void remove(String key) {
        getMMKV().remove(key);
    }

    public static void remove(String mapId, String key) {
        getMMKV(mapId).remove(key);
    }

    public static void clear() {
        getMMKV().clear();
    }

    public static void clear(String mapId) {
        getMMKV(mapId).clear();
    }

    public static boolean contains(String key) {
        return getMMKV().contains(key);
    }

    public static boolean contains(String mapId, String key) {
        return getMMKV(mapId).contains(key);
    }
}
