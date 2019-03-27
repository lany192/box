package com.lany.box.utils;

import android.content.Context;
import android.content.pm.PackageManager;

public class PermissionUtils {

    /**
     * 检查权限
     */
    public static boolean checkPermission(Context context, String permissionName) {
        PackageManager pm = context.getPackageManager();
        return PackageManager.PERMISSION_GRANTED == pm.checkPermission(permissionName, context.getPackageName());
    }
}
