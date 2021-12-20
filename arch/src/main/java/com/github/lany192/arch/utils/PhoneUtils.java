package com.github.lany192.arch.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;

import androidx.fragment.app.FragmentActivity;

import com.github.lany192.dialog.SimpleDialog;
import com.github.lany192.utils.MD5Utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

public class PhoneUtils {
    private static String BASE_INFO;

    /**
     * 获取手机的基本信息:系统类型/屏幕信息/手机型号/系统版本号/app版本号/发布渠道/手机号码
     */
    public static String getBaseInfo() {
        if (TextUtils.isEmpty(BASE_INFO)) {
            BASE_INFO = "android" + ";"
                    + getScreenInfo() + ";"
                    + getPhoneModel() + ";"
                    + getOSVersionCode() + ";"
                    + getAppVersionCode() + ";"
                    + getChannelCode() + ";"
                    + getPhoneNumber();
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
        return ContextUtils.getContext().getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 获取屏幕的高度(px)
     */
    public static int getDeviceHeight() {
        return ContextUtils.getContext().getResources().getDisplayMetrics().heightPixels;
    }

    /**
     * 获取本机号码，注意要添加权限android.permission.READ_PHONE_STATE
     */
    public static String getPhoneNumber() {
        try {
            TelephonyManager mTelephonyMgr = (TelephonyManager) ContextUtils.getContext().getSystemService(Context.TELEPHONY_SERVICE);
            return mTelephonyMgr.getLine1Number();
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 获取手机型号
     *
     * @return
     */
    public static String getPhoneModel() {
        return android.os.Build.MODEL;
    }

    /**
     * 序列号
     *
     * @return
     */
    public static String getSerial() {
        return android.os.Build.SERIAL;
    }

    /**
     * 获取系统版本号
     *
     * @return
     */
    public static String getOSVersionName() {
        return android.os.Build.VERSION.RELEASE;
    }

    /**
     * 获取系统版本号
     *
     * @return
     */
    public static int getOSVersionCode() {
        return android.os.Build.VERSION.SDK_INT;
    }

    /**
     * 获取app版本code
     *
     * @return
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
     *
     * @return
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

    /**
     * 获取androidId
     */
    public static String getAndroidId() {
        return Settings.Secure.getString(ContextUtils.getContext().getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    /**
     * 设备唯一编号
     *
     * @return
     */
    public static String getPhoneId() {
        StringBuilder sb = new StringBuilder();
        sb.append(getDeviceId());
        sb.append(getAndroidId());
        sb.append(getSerial());
        return MD5Utils.md5(sb.toString());
    }

    /**
     * 获取设备号DeviceId
     */
    public static String getDeviceId() {
        TelephonyManager tm = (TelephonyManager) ContextUtils.getContext().getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getDeviceId();
    }

    public static String getMac() {
        String result;
        String Mac;
        result = callCmd("busybox ifconfig", "HWaddr");

        if (result == null) {
            return "网络出错，请检查网络";
        }
        if (result.length() > 0 && result.contains("HWaddr")) {
            Mac = result.substring(result.indexOf("HWaddr") + 6, result.length() - 1);
            if (Mac.length() > 1) {
                result = Mac.toLowerCase();
            }
        }
        return result.trim();
    }

    private static String callCmd(String cmd, String filter) {
        String result = "";
        String line = "";
        try {
            Process proc = Runtime.getRuntime().exec(cmd);
            InputStreamReader is = new InputStreamReader(proc.getInputStream());
            BufferedReader br = new BufferedReader(is);

            //执行命令cmd，只取结果中含有filter的这一行
            while ((line = br.readLine()) != null && line.contains(filter) == false) {
                //result += line;
                Log.i("test", "line: " + line);
            }

            result = line;
            Log.i("test", "result: " + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * 读取配置文件中的渠道代码
     *
     * @return
     */
    public static String getChannelCode() {
        String code = getMetaData("UMENG_CHANNEL");
        //XLog.tag(TAG).i(" 渠道号 == " + code);
        return code;
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
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR1) {
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
    }

    /**
     * 获取状态栏高度
     */
    public static int getStatusBarHeight() {
        int result = 24;
        int resId = Resources.getSystem().getIdentifier("status_bar_height", "dimen", "android");
        if (resId > 0) {
            result = Resources.getSystem().getDimensionPixelSize(resId);
        } else {
            result = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                    result, Resources.getSystem().getDisplayMetrics());
        }
        return result;
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
}
