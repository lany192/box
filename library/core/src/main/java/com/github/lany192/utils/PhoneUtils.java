package com.github.lany192.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PermissionInfo;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Process;
import android.view.Display;
import android.view.WindowManager;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

public class PhoneUtils {

    /**
     * 获取声明的权限
     */
    public static String[] getRequestedPermissions(Context context) {
        PackageManager packageManager = context.getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), PackageManager.GET_PERMISSIONS);
            return packageInfo.requestedPermissions;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static PermissionInfo[] getPermissionInfo(Context context) {
        PackageManager packageManager = context.getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), PackageManager.GET_PERMISSIONS);
            return packageInfo.permissions;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * app重启
     */
    public static void restart() {
        Context context = ContextUtils.getContext();
        Intent intent = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
        System.exit(0);
        Process.killProcess(Process.myPid());
    }

    /**
     * 获取屏幕信息
     */
    public static String getScreenInfo() {
        return getDeviceWidth() + "*" + getDeviceHeight();
    }

    /**
     * 获取屏幕宽度(px)
     */
    public static int getDeviceWidth() {
        int width = Resources.getSystem().getDisplayMetrics().widthPixels;
        int height = Resources.getSystem().getDisplayMetrics().heightPixels;
        return Math.min(width, height);
    }

    /**
     * 获取屏幕的高度(px)
     */
    public static int getDeviceHeight() {
        int width = Resources.getSystem().getDisplayMetrics().widthPixels;
        int height = Resources.getSystem().getDisplayMetrics().heightPixels;
        return Math.max(width, height);
    }

    /**
     * 获取手机型号
     */
    public static String getPhoneModel() {
        return android.os.Build.MODEL;
    }

    /**
     * 获取系统版本号
     */
    public static String getOSVersionName() {
        return android.os.Build.VERSION.RELEASE;
    }

    /**
     * 获取系统版本号
     */
    public static int getOSVersionCode() {
        return android.os.Build.VERSION.SDK_INT;
    }

    /**
     * 获取app版本code
     */
    public static int getAppVersionCode() {
        PackageManager pm = ContextUtils.getContext().getPackageManager();
        try {
            PackageInfo pi = pm.getPackageInfo(ContextUtils.getContext().getPackageName(), 0);
            return pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取app版本name
     */
    public static String getAppVersionName() {
        PackageManager pm = ContextUtils.getContext().getPackageManager();
        try {
            PackageInfo pi = pm.getPackageInfo(ContextUtils.getContext().getPackageName(), 0);
            return pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "1.0.0";
    }

    public static String getMetaData(String key) {
        ApplicationInfo appInfo = null;
        String value = "";
        Context context = ContextUtils.getContext();
        try {
            appInfo = context.getPackageManager().getApplicationInfo(
                    context.getPackageName(), PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (appInfo != null) {
            Bundle bundle = appInfo.metaData;
            if (bundle != null) {
                value = bundle.getString(key);
                //XLog.tag(TAG).i("key:" + key + "-----value:" + value);
            }
        }
        return value;
    }


    /**
     * 获取状态栏高度
     */
    public static int getStatusBarHeight() {
        int resId = Resources.getSystem().getIdentifier("status_bar_height", "dimen", "android");
        if (resId > 0) {
            return Resources.getSystem().getDimensionPixelSize(resId);
        }
        return 0;
    }

    /**
     * 获取导航栏高度
     */
    public static int getNavigationBarHeight() {
        int resId = Resources.getSystem().getIdentifier("navigation_bar_height", "dimen", "android");
        if (resId > 0) {
            return Resources.getSystem().getDimensionPixelSize(resId);
        }
        WindowManager windowManager = (WindowManager) ContextUtils.getContext().getSystemService(Context.WINDOW_SERVICE);
        if (windowManager != null) {
            Display display = windowManager.getDefaultDisplay();
            Point size = new Point();
            Point realSize = new Point();
            display.getSize(size);
            display.getRealSize(realSize);
            return realSize.y - size.y;
        }
        return 0;
    }

    /**
     * 是否有导航栏
     */
    public static boolean hasNavigationBar() {
        boolean hasNavigationBar = false;
        try {
            Resources rs = ContextUtils.getContext().getResources();
            int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
            if (id > 0) {
                hasNavigationBar = rs.getBoolean(id);
            }
            Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method m = systemPropertiesClass.getMethod("get", String.class);
            String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
            if ("1".equals(navBarOverride)) {
                hasNavigationBar = false;
            } else if ("0".equals(navBarOverride)) {
                hasNavigationBar = true;
            }
        } catch (Exception e) {

        }
        if (!hasNavigationBar) {
            WindowManager windowManager = (WindowManager) ContextUtils.getContext().getSystemService(Context.WINDOW_SERVICE);
            if (windowManager != null) {
                Display display = windowManager.getDefaultDisplay();
                Point size = new Point();
                Point realSize = new Point();
                display.getSize(size);
                display.getRealSize(realSize);
                hasNavigationBar = realSize.y != size.y;
            }
        }
        return hasNavigationBar;
    }

    /**
     * 是否安装某个应用
     */
    public static boolean isInstall(Context context, String packageName) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> pi = packageManager.getInstalledPackages(0);
        if (pi != null) {
            for (int i = 0; i < pi.size(); i++) {
                String pn = pi.get(i).packageName;
                if (pn.equals(packageName)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 清除app数据，包括撤销授权
     */
    public static void clearApp() {
        try {
            Runtime.getRuntime().exec("pm clear " + ContextUtils.getContext().getPackageName());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
