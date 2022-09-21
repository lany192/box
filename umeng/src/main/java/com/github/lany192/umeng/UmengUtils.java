package com.github.lany192.umeng;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.elvishew.xlog.XLog;
import com.github.lany192.interfaces.SimpleActivityLifecycleCallbacks;
import com.github.lany192.utils.ContextUtils;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;

import java.util.Map;

/**
 * 代表友盟SDK工具类。
 */
public class UmengUtils {
    private static UmengConfig mConfig;
    private static Application mApplication;
    private static boolean init;

    /**
     * 预初始化，已添加子进程中初始化sdk。
     * 使用场景：用户未同意隐私政策协议授权时，延迟初始化
     */
    public static void preInit(Application application, UmengConfig config) {
        mApplication = application;
        mConfig = config;
        UMConfigure.preInit(application, config.getAppId(), config.getChannel());
    }

    /**
     * 初始化友盟统计和推送
     */
    public static void init(Context context) {
        new Thread(() -> {
            UMConfigure.init(context, mConfig.getAppId(), mConfig.getChannel(), UMConfigure.DEVICE_TYPE_PHONE, mConfig.getMessageSecret());
            UMConfigure.setEncryptEnabled(true);
            MobclickAgent.setDebugMode(mConfig.isDebug());
            MobclickAgent.setCatchUncaughtExceptions(!mConfig.isDebug());
            UMConfigure.setLogEnabled(mConfig.isDebug());
            init = true;
        }).start();
        mApplication.registerActivityLifecycleCallbacks(new SimpleActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
                String simpleName = activity.getClass().getSimpleName();
                UmengUtils.onPageStart(simpleName);
            }

            @Override
            public void onActivityDestroyed(@NonNull Activity activity) {
                String simpleName = activity.getClass().getSimpleName();
                UmengUtils.onPageEnd(simpleName);
            }
        });
    }

    /**
     * 统计页面开始。
     *
     * @param name 页面名称
     */
    public static void onPageStart(String name) {
        if (init) {
            if (TextUtils.isEmpty(name)) {
                return;
            }
            if (!mConfig.isDebug()) {
                MobclickAgent.onPageStart(name);
            }
        }
    }

    /**
     * 统计页面结束。
     *
     * @param name 页面名称
     */
    public static void onPageEnd(String name) {
        if (init) {
            if (TextUtils.isEmpty(name)) {
                return;
            }
            if (!mConfig.isDebug()) {
                MobclickAgent.onPageEnd(name);
            }
        }
    }

    /**
     * 发布友盟事件。
     */
    public static void event(String eventId) {
        if (init) {
            if (mConfig.isDebug()) {
                XLog.d("eventId:%s", eventId);
                return;
            }
            if (TextUtils.isEmpty(eventId)) {
                return;
            }
            MobclickAgent.onEvent(ContextUtils.getContext(), eventId);
        }
    }

    /**
     * 发布友盟事件。
     *
     * @param eventId 事件 ID
     * @param value   参数值
     */
    public static void event(String eventId, String value) {
        if (init) {
            if (TextUtils.isEmpty(eventId)) {
                return;
            }
            MobclickAgent.onEvent(ContextUtils.getContext(), eventId, value);
        }
    }

    /**
     * 发布友盟事件。
     *
     * @param eventId 事件 ID
     * @param params  事件参数表
     */
    public static void event(String eventId, Map<String, String> params) {
        if (init) {
            if (TextUtils.isEmpty(eventId)) {
                return;
            }
            if (!mConfig.isDebug()) {
                XLog.d("eventId:%s, params:%s", eventId, params);
                MobclickAgent.onEvent(ContextUtils.getContext(), eventId, params);
            }
        }
    }
}
