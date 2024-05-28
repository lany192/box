package com.github.lany192.utils;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.pm.ApplicationInfo;

import androidx.fragment.app.FragmentActivity;

public class ContextUtils {
    private static Context mContext;

    public static void setApplicationContext(Context context) {
        mContext = context;
    }

    public static Context getContext() {
        return mContext;
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

    public static boolean isDebug() {
        return mContext.getApplicationInfo() != null &&
                (mContext.getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
    }
}
