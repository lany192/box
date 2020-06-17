package com.github.lany192.box.helper;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;

import java.util.Map;


/**
 * SharedPreferences封装工具类
 */
public final class SPHelper {
    private final String TAG = getClass().getSimpleName();
    private volatile static SPHelper instance;
    private String mSpaceName;//默认存储空间名称
    private Context mContext;
    private SharedPreferences sp;

    private SPHelper() {

    }

    public static SPHelper of() {
        if (instance == null) {
            synchronized (SPHelper.class) {
                if (instance == null) {
                    instance = new SPHelper();
                }
            }
        }
        return instance;
    }

    public void init(Context ctx) {
        init(ctx, "");
    }

    public void init(Context ctx, String spaceName) {
        Context app = ctx.getApplicationContext();
        if (app == null) {
            this.mContext = ctx;
        } else {
            this.mContext = ((Application) app).getBaseContext();
        }
        this.mSpaceName = spaceName;
    }

    private SharedPreferences getSharedPreferences() {
        return getSharedPreferences(mSpaceName);
    }

    private SharedPreferences getSharedPreferences(String spaceName) {
        if (mContext == null) {
            Log.e(TAG, "The context is null, please call the initialization method first!");
            throw new IllegalArgumentException("The context is null, please call the initialization method first!");
        }
        if (sp == null) {
            if (TextUtils.isEmpty(spaceName)) {
                sp = PreferenceManager.getDefaultSharedPreferences(mContext);
            } else {
                sp = mContext.getSharedPreferences(spaceName, Context.MODE_PRIVATE);
            }
        }
        return sp;
    }

    public boolean exists(String key) {
        return getSharedPreferences().contains(key);
    }

    public boolean exists(String spaceName, String key) {
        return getSharedPreferences(spaceName).contains(key);
    }

    public String getString(String key) {
        return getSharedPreferences().getString(key, "");
    }

    public String getString(String key, String defaultValue) {
        return getSharedPreferences().getString(key, defaultValue);
    }

    public String getString(String spaceName, String key, String defaultValue) {
        return getSharedPreferences(spaceName).getString(key, defaultValue);
    }

    public void putString(String key, String value) {
        getSharedPreferences().edit().putString(key, value).apply();
    }

    public void putString(String spaceName, String key, String value) {
        getSharedPreferences(spaceName).edit().putString(key, value).apply();
    }

    public boolean putStringForResult(String key, String value) {
        return getSharedPreferences().edit().putString(key, value).commit();
    }

    public boolean putStringForResult(String spaceName, String key, String value) {
        return getSharedPreferences(spaceName).edit().putString(key, value).commit();
    }

    public boolean getBoolean(String key) {
        return getSharedPreferences().getBoolean(key, false);
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return getSharedPreferences().getBoolean(key, defaultValue);
    }

    public boolean getBoolean(String spaceName, String key, boolean defaultValue) {
        return getSharedPreferences(spaceName).getBoolean(key, defaultValue);
    }

    public void putBoolean(String key, boolean value) {
        getSharedPreferences().edit().putBoolean(key, value).apply();
    }

    public void putBoolean(String spaceName, String key, boolean value) {
        getSharedPreferences(spaceName).edit().putBoolean(key, value).apply();
    }

    public boolean putBooleanForResult(String key, boolean value) {
        return getSharedPreferences().edit().putBoolean(key, value).commit();
    }

    public boolean putBooleanForResult(String spaceName, String key, boolean value) {
        return getSharedPreferences(spaceName).edit().putBoolean(key, value).commit();
    }

    public void putInt(String key, int value) {
        getSharedPreferences().edit().putInt(key, value).apply();
    }

    public void putInt(String spaceName, String key, int value) {
        getSharedPreferences(spaceName).edit().putInt(key, value).apply();
    }

    public boolean putIntForResult(String key, int value) {
        return getSharedPreferences().edit().putInt(key, value).commit();
    }

    public boolean putIntForResult(String spaceName, String key, int value) {
        return getSharedPreferences(spaceName).edit().putInt(key, value).commit();
    }

    public int getInt(String key) {
        return getSharedPreferences().getInt(key, 0);
    }

    public int getInt(String key, int defaultValue) {
        return getSharedPreferences().getInt(key, defaultValue);
    }

    public int getInt(String spaceName, String key, int defaultValue) {
        return getSharedPreferences(spaceName).getInt(key, defaultValue);
    }

    public void putFloat(String key, float value) {
        getSharedPreferences().edit().putFloat(key, value).apply();
    }

    public void putFloat(String spaceName, String key, float value) {
        getSharedPreferences(spaceName).edit().putFloat(key, value).apply();
    }

    public boolean putFloatForResult(String key, float value) {
        return getSharedPreferences().edit().putFloat(key, value).commit();
    }

    public boolean putFloatForResult(String spaceName, String key, float value) {
        return getSharedPreferences(spaceName).edit().putFloat(key, value).commit();
    }

    public float getFloat(String key) {
        return getSharedPreferences().getFloat(key, 0f);
    }

    public float getFloat(String key, float defaultValue) {
        return getSharedPreferences().getFloat(key, defaultValue);
    }

    public float getFloat(String spaceName, String key, float defaultValue) {
        return getSharedPreferences(spaceName).getFloat(key, defaultValue);
    }

    public void putLong(String key, long value) {
        getSharedPreferences().edit().putLong(key, value).apply();
    }

    public void putLong(String spaceName, String key, long value) {
        getSharedPreferences(spaceName).edit().putLong(key, value).apply();
    }

    public boolean putLongForResult(String key, long value) {
        return getSharedPreferences().edit().putLong(key, value).commit();
    }

    public boolean putLongForResult(String spaceName, String key, long value) {
        return getSharedPreferences(spaceName).edit().putLong(key, value).commit();
    }

    public long getLong(String key) {
        return getSharedPreferences().getLong(key, 0L);
    }

    public long getLong(String key, long defaultValue) {
        return getSharedPreferences().getLong(key, defaultValue);
    }

    public long getLong(String spaceName, String key, long defaultValue) {
        return getSharedPreferences(spaceName).getLong(key, defaultValue);
    }

    public void clear() {
        Editor editor = getSharedPreferences().edit();
        editor.clear();
        editor.apply();
    }

    public void clear(String spaceName) {
        Editor editor = getSharedPreferences(spaceName).edit();
        editor.clear();
        editor.apply();
    }

    public void put(Map<String, Object> map) {
        put("", map);
    }

    /**
     * 批量提交
     */
    public void put(String spaceName, Map<String, Object> map) {
        if (map != null && !map.isEmpty()) {
            Editor editor;
            if (TextUtils.isEmpty(spaceName)) {
                editor = getSharedPreferences().edit();
            } else {
                editor = getSharedPreferences(spaceName).edit();
            }
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                if (value != null) {
                    Class clz = value.getClass();
                    if (clz.equals(String.class)) {
                        editor.putString(key, (String) value);
                    } else if (clz.equals(Integer.class)) {
                        editor.putInt(key, (int) value);
                    } else if (clz.equals(Long.class)) {
                        editor.putLong(key, (long) value);
                    } else if (clz.equals(Float.class)) {
                        editor.putFloat(key, (float) value);
                    } else if (clz.equals(Boolean.class)) {
                        editor.putBoolean(key, (boolean) value);
                    }
                }
            }
            editor.apply();
        }
    }
}