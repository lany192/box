package com.lany.box.utils;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;

public class PermissionUtils {

    /**
     * 检查权限
     */
    public static boolean checkPermission(Context context, String... permissions) {
        if (permissions != null && permissions.length != 0) {
            for (String permission : permissions) {
                if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(context, permission)) {
                    return false;
                }
            }
            return true;
        } else {
            throw new IllegalArgumentException("至少要添加一个权限进行判断");
        }
    }

    /**
     * 检查权限
     */
    public static boolean checkPermission(Context context, String permission) {
        return PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(context, permission);
    }

    /**
     * 跳转到app权限管理界面
     *
     * @param activity
     */
    public static void gotoPermissionManage(Activity activity) {
        String brand = Build.BRAND;//手机厂商
        if (TextUtils.equals(brand.toLowerCase(), "redmi") || TextUtils.equals(brand.toLowerCase(), "xiaomi")) {
            gotoMiuiPermission(activity);//小米
        } else if (TextUtils.equals(brand.toLowerCase(), "meizu")) {
            gotoMeizuPermission(activity);
        } else if (TextUtils.equals(brand.toLowerCase(), "huawei") || TextUtils.equals(brand.toLowerCase(), "honor")) {
            gotoHuaweiPermission(activity);
        } else {
            activity.startActivity(getAppDetailSettingIntent(activity));
        }
    }

    /**
     * 跳转到miui的权限管理页面
     */
    private static void gotoMiuiPermission(Activity activity) {
        String packageName = activity.getApplicationInfo().packageName;
        try { // MIUI 8
            Intent intent = new Intent("miui.intent.action.APP_PERM_EDITOR");
            intent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.PermissionsEditorActivity");
            intent.putExtra("extra_pkgname", packageName);
            activity.startActivity(intent);
        } catch (Exception e) {
            try { // MIUI 5/6/7
                Intent intent = new Intent("miui.intent.action.APP_PERM_EDITOR");
                intent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.AppPermissionsEditorActivity");
                intent.putExtra("extra_pkgname", packageName);
                activity.startActivity(intent);
            } catch (Exception e1) { // 否则跳转到应用详情
                activity.startActivity(getAppDetailSettingIntent(activity));
            }
        }
    }

    /**
     * 跳转到魅族的权限管理系统
     */
    private static void gotoMeizuPermission(Activity activity) {
        String packageName = activity.getApplicationInfo().packageName;
        try {
            Intent intent = new Intent("com.meizu.safe.security.SHOW_APPSEC");
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.putExtra("packageName", packageName);
            activity.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            activity.startActivity(getAppDetailSettingIntent(activity));
        }
    }

    /**
     * 华为的权限管理页面
     */
    private static void gotoHuaweiPermission(Activity activity) {
        try {
            Intent intent = new Intent();
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ComponentName comp = new ComponentName("com.huawei.systemmanager", "com.huawei.permissionmanager.ui.MainActivity");//华为权限管理
            intent.setComponent(comp);
            activity.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            activity.startActivity(getAppDetailSettingIntent(activity));
        }
    }

    /**
     * 获取应用详情页面intent（如果找不到要跳转的界面，也可以先把用户引导到系统设置页面）
     */
    private static Intent getAppDetailSettingIntent(Activity activity) {
        String packageName = activity.getApplicationInfo().packageName;
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent.setData(Uri.fromParts("package", packageName, null));
        return intent;
    }
}
