package com.lany.box.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.lany.box.BaseApp;

public class NetUtils {

    /**
     * 判断当前网络是否可用
     */
    public static boolean isNetWorkAvailable() {
        ConnectivityManager manager = (ConnectivityManager) BaseApp.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager != null) {
            NetworkInfo info = manager.getActiveNetworkInfo();
            return info != null && info.isConnected();
        } else {
            return false;
        }
    }

    /**
     * 判断当前是移动网络环境
     */
    public static boolean isNetWorkAvailableButNoWifi(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetInfo != null && activeNetInfo.isConnected()) {
            if (activeNetInfo.getState() == NetworkInfo.State.CONNECTED) {
                return activeNetInfo.getType() != ConnectivityManager.TYPE_WIFI;
            }
        }
        return false;
    }

    /**
     * 判断当前是否为wifi网络环境
     */
    public static boolean isWifi(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetInfo != null && activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI;
    }
}
