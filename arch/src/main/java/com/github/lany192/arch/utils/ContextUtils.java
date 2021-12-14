package com.github.lany192.arch.utils;

import android.content.Context;

public class ContextUtils {
    private static Context context;

    public static void setApplicationContext(Context context) {
        ContextUtils.context = context;
    }

    public static Context getContext() {
        return context;
    }
}
