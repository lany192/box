package com.github.lany192.arch.utils;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.ColorInt;
import androidx.fragment.app.FragmentActivity;

import com.github.lany192.dialog.SimpleDialog;
import com.github.lany192.interfaces.OnSimpleListener;
import com.github.lany192.utils.PhoneUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Random;

public class OtherUtils {
    private static String BASE_INFO;

    /**
     * 获取手机的基本信息:系统类型/屏幕信息/手机型号/系统版本号/app版本号/发布渠道/手机号码
     */
    public static String getBaseInfo() {
        if (TextUtils.isEmpty(BASE_INFO)) {
            BASE_INFO = "android" + ";"
                    + PhoneUtils.getScreenInfo() + ";"
                    + PhoneUtils.getOSVersionCode() + ";"
                    + PhoneUtils.getAppVersionCode() + ";"
                    + PhoneUtils.getAppVersionName() + ";"
                    + DeviceId.get().getDeviceId() + ";";
        }
        return BASE_INFO;
    }

    /**
     * 检查是否开启'不保留活动'
     */
    public static void checkAlwaysFinishDialog(final FragmentActivity activity) {
        int alwaysFinish = Settings.Global.getInt(activity.getContentResolver(), Settings.Global.ALWAYS_FINISH_ACTIVITIES, 0);
        if (alwaysFinish == 1) {
            SimpleDialog dialog = new SimpleDialog();
            dialog.setMessage("检测到您已开启'不保留活动'功能,导致APP部分功能无法正常使用。建议您找到'系统设置'下的'开发者选项'，将'不保留活动'功能关闭。");
            dialog.setRightButton("设置");
            dialog.setRightClickListener(() -> {
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS);
                activity.startActivity(intent);
            });
            dialog.setLeftButton("取消");
            dialog.show(activity);
        }
    }

    public static void closeAndroidPWarningDialog(boolean debug) {
        if (debug && Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            try {
                Class aClass = Class.forName("android.content.pm.PackageParser$Package");
                Constructor declaredConstructor = aClass.getDeclaredConstructor(String.class);
                declaredConstructor.setAccessible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                Class cls = Class.forName("android.app.ActivityThread");
                Method declaredMethod = cls.getDeclaredMethod("currentActivityThread");
                declaredMethod.setAccessible(true);
                Object activityThread = declaredMethod.invoke(null);
                Field mHiddenApiWarningShown = cls.getDeclaredField("mHiddenApiWarningShown");
                mHiddenApiWarningShown.setAccessible(true);
                mHiddenApiWarningShown.setBoolean(activityThread, true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 随机颜色
     */
    @ColorInt
    public static int getRandomColor() {
        Random random = new Random();
        int r = random.nextInt(256);
        int g = random.nextInt(256);
        int b = random.nextInt(256);
        return Color.argb(20, r, g, b);
    }

    /**
     * 判断是否是深颜色
     */
    public static boolean isDark(@ColorInt int color) {
        return androidx.core.graphics.ColorUtils.calculateLuminance(color) < 0.5;
    }

    /**
     * 打印日志堆栈
     */
    public static void printStackTrace(String msg) {
        try {
            throw new RuntimeException("抛出堆栈:" + msg);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("打印堆栈", "堆栈信息：", e);
        }
    }
}
