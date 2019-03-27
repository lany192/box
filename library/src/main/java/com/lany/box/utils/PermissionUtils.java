package com.lany.box.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;

public class PermissionUtils {

    /**
     * 检查权限
     */
    public static boolean checkPermission(Context context, String permission) {
        return PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(context, permission);
    }
}
