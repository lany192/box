package com.github.lany192.box.utils;


import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 根据设备特征生成一个不变的设备id
 */
public class DeviceUtils {
    private static final String TAG = "DeviceUtils";
    private static final String KEY_NAME = "android_unique_device_id";//任意，但不能和系统设置冲突

    /**
     * 根据设备特征生成一个不变的设备id
     */
    public static String getUniqueDeviceId(Context context) {
        Context ctx = context.getApplicationContext();
        if (ctx == null) {
            ctx = context;
        } else {
            ctx = ((Application) ctx).getBaseContext();
        }
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(ctx);
        String uniqueId = sp.getString(KEY_NAME, "");
        if (TextUtils.isEmpty(uniqueId)) {
            uniqueId = createUniqueId(ctx);
            Log.i(TAG, "生成唯一设备ID: " + uniqueId);
            //保存到SharedPreferences
            SharedPreferences.Editor editor = sp.edit();
            editor.putString(KEY_NAME, uniqueId);
            editor.apply();
            return uniqueId;
        }
        Log.i(TAG, "获得唯一设备ID: " + uniqueId);
        return uniqueId;
    }

    @SuppressLint("HardwareIds")
    private static String createUniqueId(Context ctx) {
        StringBuilder sb = new StringBuilder();
        TelephonyManager tm = (TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE);
        if (tm != null) {
            try {
                String deviceId = tm.getDeviceId();
                if (!TextUtils.isEmpty(deviceId)) {
                    sb.append(deviceId);
                }
            } catch (SecurityException e) {
                e.printStackTrace();
            }
        }
        String androidId = Settings.Secure.getString(ctx.getContentResolver(), Settings.Secure.ANDROID_ID);
        if (!TextUtils.isEmpty(androidId) && !androidId.equals("9774d56d682e549c") && !androidId.equals("0000000000000")) {
            sb.append(androidId);
        }
        sb.append(android.os.Build.BOARD);//获取设备基板名称
        sb.append(android.os.Build.BOOTLOADER);//获取设备引导程序版本号
        sb.append(android.os.Build.BRAND);//获取设备品牌
        sb.append(android.os.Build.CPU_ABI);//获取设备指令集名称（CPU的类型）
        sb.append(android.os.Build.CPU_ABI2);//获取第二个指令集名称
        sb.append(android.os.Build.DEVICE);//获取设备驱动名称
        sb.append(android.os.Build.DISPLAY);//获取设备显示的版本包（在系统设置中显示为版本号）和ID一样
        sb.append(android.os.Build.FINGERPRINT);//设备的唯一标识。由设备的多个信息拼接合成。
        sb.append(android.os.Build.HARDWARE);//设备硬件名称,一般和基板名称一样（BOARD）
        sb.append(android.os.Build.ID);//设备版本号。
        sb.append(android.os.Build.MODEL);//获取手机的型号 设备名称。
        sb.append(android.os.Build.MANUFACTURER);//获取设备制造商
        sb.append(android.os.Build.PRODUCT);//整个产品的名称
        sb.append(android.os.Build.TAGS);//设备标签。如release-keys 或测试的 test-keys
        sb.append(android.os.Build.TYPE);//设备版本类型 主要为 "user" 或 "eng".
        sb.append(android.os.Build.USER);//设备用户名 基本上都为android -build
        sb.append(android.os.Build.VERSION.RELEASE);//获取系统版本字符串。如4.1.2 或2.2 或2.3等
        sb.append(android.os.Build.VERSION.CODENAME);//设备当前的系统开发代号，一般使用REL代替
        sb.append(android.os.Build.VERSION.INCREMENTAL);//系统源代码控制值，一个数字或者git hash值
        sb.append(android.os.Build.VERSION.SDK_INT);//系统的API级别 数字表示
        sb.append(android.os.Build.SERIAL);
        return md5(sb.toString());
    }

    private static String md5(String str) {
        byte[] hash;
        try {
            hash = MessageDigest.getInstance("MD5").digest(str.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10)
                hex.append("0");
            hex.append(Integer.toHexString(b & 0xFF));
        }
        return hex.toString().toUpperCase();
    }
}
