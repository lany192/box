package com.github.lany192.utils;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.pm.ApplicationInfo;

import androidx.fragment.app.FragmentActivity;

public class ContextUtils {
    private static Context mContext;

    public static void setContext(Context context) {
        mContext = context;
    }

    public static Context getContext() {
        return mContext.getApplicationContext();
    }

    public static FragmentActivity context2FragmentActivity(Context context) {
        if (context == null) {
            return null;
        } else if (context instanceof FragmentActivity) {
            return (FragmentActivity) context;
        } else if (context instanceof ContextWrapper) {
            return context2FragmentActivity(((ContextWrapper) context).getBaseContext());
        }
        return null;
    }

    public static Activity context2activity(Context context) {
        if (context == null) {
            return null;
        } else if (context instanceof Activity) {
            return (Activity) context;
        } else if (context instanceof ContextWrapper) {
            return context2activity(((ContextWrapper) context).getBaseContext());
        }
        return null;
    }

    /**
     * 检查上下文是否失效
     */
    public static boolean check(Context context) {
        Activity activity = context2activity(context);
        return activity != null && !activity.isFinishing() && !activity.isDestroyed();
    }

    public static boolean isDebug() {
        return mContext.getApplicationInfo() != null &&
                (mContext.getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
    }
}
