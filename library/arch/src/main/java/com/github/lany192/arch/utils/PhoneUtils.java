package com.github.lany192.arch.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PermissionInfo;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Process;
import android.provider.Settings;
import android.text.TextUtils;

import androidx.fragment.app.FragmentActivity;

import com.github.lany192.dialog.SimpleDialog;
import com.github.lany192.utils.ContextUtils;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

public class PhoneUtils {
    private static String BASE_INFO;

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
     * 获取手机的基本信息:系统类型/屏幕信息/手机型号/系统版本号/app版本号/发布渠道/手机号码
     */
    public static String getBaseInfo() {
        if (TextUtils.isEmpty(BASE_INFO)) {
            BASE_INFO = "android" + ";"
                    + getScreenInfo() + ";"
                    + getOSVersionCode() + ";"
                    + getAppVersionCode() + ";"
                    + getAppVersionName() + ";"
                    + DeviceId.get().getDeviceId() + ";";
        }
        return BASE_INFO;
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
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    /**
     * 获取屏幕的高度(px)
     */
    public static int getDeviceHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
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
     * 检查是否开启'不保留活动'
     */
    public static void checkAlwaysFinishDialog(final FragmentActivity activity) {
        int alwaysFinish = Settings.Global.getInt(activity.getContentResolver(), Settings.Global.ALWAYS_FINISH_ACTIVITIES, 0);
        if (alwaysFinish == 1) {
            SimpleDialog dialog = new SimpleDialog();
            dialog.setMessage("检测到您已开启'不保留活动'功能,导致APP部分功能无法正常使用。建议您找到'系统设置'下的'开发者选项'，将'不保留活动'功能关闭。");
            dialog.setRightButton("设置", () -> {
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS);
                activity.startActivity(intent);
            });
            dialog.setLeftButton("取消", null);
            dialog.show(activity);
        }
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
