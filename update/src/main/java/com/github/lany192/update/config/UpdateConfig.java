package com.github.lany192.update.config;

import android.app.NotificationChannel;

import com.github.lany192.update.listener.OnDownloadListener;
import com.github.lany192.update.manager.BaseHttpDownloadManager;

import java.util.ArrayList;
import java.util.List;

public class UpdateConfig {
    /**
     * 通知栏id
     */
    private int notifyId = 1011;
    /**
     * 适配Android O的渠道通知
     */
    private NotificationChannel notificationChannel;
    /**
     * 用户自定义的下载管理
     */
    private BaseHttpDownloadManager httpManager;
    /**
     * 是否需要显示通知栏进度
     */
    private boolean showNotification = true;
    /**
     * 下载过程回调
     */
    private List<OnDownloadListener> onDownloadListeners = new ArrayList<>();

    /**
     * 下载完成是否自动弹出安装页面 (默认为true)
     */
    private boolean jumpInstallPage = true;
    /**
     * 下载开始时是否提示 "正在后台下载新版本…" (默认为true)
     */
    private boolean showBgdToast = true;
    /**
     * 是否强制升级(默认为false)
     */
    private boolean forcedUpgrade = false;

    /**
     * 获取通知栏消息id
     */
    public int getNotifyId() {
        return notifyId;
    }

    /**
     * 设置通知栏消息id
     */
    public UpdateConfig setNotifyId(int notifyId) {
        this.notifyId = notifyId;
        return this;
    }

    /**
     * 获取下载管理器
     */
    public BaseHttpDownloadManager getHttpManager() {
        return httpManager;
    }

    /**
     * 设置下载管理器
     */
    public UpdateConfig setHttpManager(BaseHttpDownloadManager httpManager) {
        this.httpManager = httpManager;
        return this;
    }

    /**
     * 获取下载监听器
     */
    public List<OnDownloadListener> getOnDownloadListener() {
        return onDownloadListeners;
    }

    /**
     * 设置下载监听器
     */
    public UpdateConfig setOnDownloadListener(OnDownloadListener onDownloadListener) {
        this.onDownloadListeners.add(onDownloadListener);
        return this;
    }

    /**
     * apk下载完成是否跳转至安装界面
     */
    public boolean isJumpInstallPage() {
        return jumpInstallPage;
    }

    /**
     * 设置apk下载完成是否跳转至安装界面
     */
    public UpdateConfig setJumpInstallPage(boolean jumpInstallPage) {
        this.jumpInstallPage = jumpInstallPage;
        return this;
    }

    /**
     * 获取Android O的通知渠道
     */
    public NotificationChannel getNotificationChannel() {
        return notificationChannel;
    }

    /**
     * 设置Android O的通知渠道
     */
    public UpdateConfig setNotificationChannel(NotificationChannel notificationChannel) {
        this.notificationChannel = notificationChannel;
        return this;
    }

    /**
     * 是否在通知栏显示信息
     */
    public boolean isShowNotification() {
        return showNotification;
    }

    /**
     * 设置是否在通知栏显示信息
     */
    public UpdateConfig setShowNotification(boolean showNotification) {
        this.showNotification = showNotification;
        return this;
    }

    /**
     * 是否强制升级
     */
    public boolean isForcedUpgrade() {
        return forcedUpgrade;
    }

    /**
     * 设置是否强制升级
     */
    public UpdateConfig setForcedUpgrade(boolean forcedUpgrade) {
        this.forcedUpgrade = forcedUpgrade;
        return this;
    }

    /**
     * 是否提示 "正在后台下载新版本…"
     */
    public boolean isShowBgdToast() {
        return showBgdToast;
    }

    /**
     * 设置是否提示 "正在后台下载新版本…"
     */
    public UpdateConfig setShowBgdToast(boolean showBgdToast) {
        this.showBgdToast = showBgdToast;
        return this;
    }
}
