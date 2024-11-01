package com.lany192.box.demo.ui.splash;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.callback.NavCallback;
import com.alibaba.android.arouter.launcher.ARouter;
import java.lang.Integer;
import java.lang.String;

/**
 *
 * 类位置：{@link com.lany192.box.demo.ui.splash.SplashActivity}
 * 自动生成,请勿编辑!
 */
public final class SplashRouter {
    /**
     *
     * 路由路径
     */
    public static final String PATH = "/ui/splash";

    /**
     * 构建Postcard
     */
    public static Postcard getPostcard() {
        Postcard postcard = ARouter.getInstance().build(PATH);
        return postcard;
    }

    /**
     * 启动器
     */
    public static void start() {
        Postcard postcard = getPostcard();
        postcard.navigation();
    }

    /**
     * 启动器
     *
     * @param callback 导航回调
     */
    public static void start(NavCallback callback) {
        Postcard postcard = getPostcard();
        postcard.navigation(null, callback);
    }

    /**
     * 启动器
     * @param context 上下文
     * @param callback 导航回调
     */
    public static void start(Context context, NavCallback callback) {
        Postcard postcard = getPostcard();
        postcard.navigation(context, callback);
    }

    /**
     * 启动器
     *
     * @param activity 上下文
     */
    public static void startForResult(Activity activity, Integer requestCode) {
        Postcard postcard = getPostcard();
        postcard.navigation(activity, requestCode);
    }

    /**
     * 启动器
     * @param activity 上下文
     * @param requestCode 请求码
     * @param callback 导航回调
     */
    public static void startForResult(Activity activity, Integer requestCode,
            NavCallback callback) {
        Postcard postcard = getPostcard();
        postcard.navigation(activity, requestCode, callback);
    }

    /**
     * 构建Postcard
     *
     * @param bundle 参数信息
     */
    public static Postcard getPostcard(Bundle bundle) {
        Postcard postcard = ARouter.getInstance().build(PATH);
        postcard.with(bundle);
        return postcard;
    }

    /**
     * 启动器
     *
     * @param bundle 参数信息
     */
    public static void start(Bundle bundle) {
        Postcard postcard = getPostcard(bundle);
        postcard.navigation();
    }

    /**
     * 启动器
     *
     * @param bundle 参数信息
     * @param callback 导航回调
     */
    public static void start(Bundle bundle, NavCallback callback) {
        Postcard postcard = getPostcard(bundle);
        postcard.navigation(null, callback);
    }

    /**
     * 启动器
     * @param context 上下文
     * @param bundle 参数信息
     * @param callback 导航回调
     */
    public static void start(Context context, Bundle bundle, NavCallback callback) {
        Postcard postcard = getPostcard(bundle);
        postcard.navigation(context, callback);
    }

    /**
     * 启动器
     *
     * @param activity 上下文
     * @param bundle 参数信息
     */
    public static void startForResult(Activity activity, Bundle bundle, Integer requestCode) {
        Postcard postcard = getPostcard(bundle);
        postcard.navigation(activity, requestCode);
    }

    /**
     * 启动器
     * @param activity 上下文
     * @param bundle 参数信息
     * @param requestCode 请求码
     * @param callback 导航回调
     */
    public static void startForResult(Activity activity, Bundle bundle, Integer requestCode,
            NavCallback callback) {
        Postcard postcard = getPostcard(bundle);
        postcard.navigation(activity, requestCode, callback);
    }
}
