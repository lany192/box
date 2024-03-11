package com.github.lany192.utils;

import android.content.Context;
import android.content.ContextWrapper;

import androidx.fragment.app.FragmentActivity;

public class ContextUtils {
    private static Context context;

    public static void setApplicationContext(Context context) {
        ContextUtils.context = context;
    }

    public static Context getContext() {
        return context;
    }

    public static FragmentActivity context2activity(Context context) {
        if (context == null) {
            return null;
        } else if (context instanceof FragmentActivity) {
            return (FragmentActivity) context;
        } else if (context instanceof ContextWrapper) {
            return context2activity(((ContextWrapper) context).getBaseContext());
        }
        return null;
    }
}
