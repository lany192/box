package com.github.lany192.log;

import com.tencent.mars.xlog.Log;

import org.json.JSONArray;
import org.json.JSONObject;

public class XLog {
    private final String tag;

    public XLog(String tag) {
        this.tag = tag;
    }

    public static XLog tag(String tag) {
        return new XLog(tag);
    }

    /**
     * 格式化json
     */
    private String format(String json) {
        String formattedString;
        if (json == null || json.trim().length() == 0) {
            w("JSON empty.");
            return "";
        }
        final int JSON_INDENT = 4;
        try {
            if (json.startsWith("{")) {
                JSONObject jsonObject = new JSONObject(json);
                formattedString = jsonObject.toString(JSON_INDENT);
            } else if (json.startsWith("[")) {
                JSONArray jsonArray = new JSONArray(json);
                formattedString = jsonArray.toString(JSON_INDENT);
            } else {
                w("JSON should start with { or [");
                return json;
            }
        } catch (Exception e) {
            e(e.getMessage());
            return json;
        }
        return formattedString;
    }

    public void json(String json) {
        Log.i(tag, format(json));
    }

    public void f(String msg) {
        Log.f(tag, msg);
    }

    public void e(String msg) {
        Log.e(tag, msg);
    }

    public void w(String msg) {
        Log.w(tag, msg);
    }

    public void i(String msg) {
        Log.i(tag, msg);
    }

    public void d(String msg) {
        Log.d(tag, msg);
    }

    public void v(String msg) {
        Log.v(tag, msg);
    }

    public void f(String format, Object... obj) {
        Log.f(tag, format, obj);
    }

    public void e(String format, Object... obj) {
        Log.e(tag, format, obj);
    }

    public void w(String format, Object... obj) {
        Log.w(tag, format, obj);
    }

    public void i(String format, Object... obj) {
        Log.i(tag, format, obj);
    }

    public void d(String format, Object... obj) {
        Log.d(tag, format, obj);
    }

    public void v(String format, Object... obj) {
        Log.v(tag, format, obj);
    }
}
