package com.github.lany192.update.config;

import android.app.NotificationChannel;

import androidx.annotation.ColorInt;

import com.github.lany192.update.base.BaseHttpDownloadManager;
import com.github.lany192.update.listener.OnButtonClickListener;
import com.github.lany192.update.listener.OnDownloadListener;

import java.util.ArrayList;
import java.util.List;

public class UpdateConfiguration {
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
     * 按钮点击事件回调
     */
    private OnButtonClickListener onButtonClickListener;
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
     * 内置对话框背景图片资源id （图片规范参照demo中的示例图大小）
     */
    private int dialogImage = -1;
    /**
     * 内置对话框按钮的颜色
     */
    private int dialogButtonColor = -1;
    /**
     * 内置对话框按钮的文字颜色
     */
    private int dialogButtonTextColor = -1;
    /**
     * 内置对话框强制更新时进度条和文字的颜色
     */
    private int dialogProgressBarColor = -1;

    /**
     * 获取通知栏消息id
     */
    public int getNotifyId() {
        return notifyId;
    }

    /**
     * 设置通知栏消息id
     */
    public UpdateConfiguration setNotifyId(int notifyId) {
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
    public UpdateConfiguration setHttpManager(BaseHttpDownloadManager httpManager) {
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
    public UpdateConfiguration setOnDownloadListener(OnDownloadListener onDownloadListener) {
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
    public UpdateConfiguration setJumpInstallPage(boolean jumpInstallPage) {
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
    public UpdateConfiguration setNotificationChannel(NotificationChannel notificationChannel) {
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
    public UpdateConfiguration setShowNotification(boolean showNotification) {
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
    public UpdateConfiguration setForcedUpgrade(boolean forcedUpgrade) {
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
    public UpdateConfiguration setShowBgdToast(boolean showBgdToast) {
        this.showBgdToast = showBgdToast;
        return this;
    }

    /**
     * 获取内置对话框背景图片资源id
     */
    public int getDialogImage() {
        return dialogImage;
    }

    /**
     * 设置内置对话框背景图片资源id
     */
    public UpdateConfiguration setDialogImage(int dialogImage) {
        this.dialogImage = dialogImage;
        return this;
    }

    /**
     * 获取内置对话框按钮的颜色
     */
    public int getDialogButtonColor() {
        return dialogButtonColor;
    }

    /**
     * 设置内置对话框按钮的颜色
     */
    public UpdateConfiguration setDialogButtonColor(@ColorInt int dialogButtonColor) {
        this.dialogButtonColor = dialogButtonColor;
        return this;
    }

    /**
     * 获取内置对话框按钮文字的颜色
     */
    public int getDialogButtonTextColor() {
        return dialogButtonTextColor;
    }

    /**
     * 设置内置对话框按钮文字的颜色
     */
    public UpdateConfiguration setDialogButtonTextColor(int dialogButtonTextColor) {
        this.dialogButtonTextColor = dialogButtonTextColor;
        return this;
    }

    /**
     * 获取内置对话框强制更新时进度条和文字的颜色
     */
    public int getDialogProgressBarColor() {
        return dialogProgressBarColor;
    }

    /**
     * 设置内置对话框强制更新时进度条和文字的颜色
     */
    public UpdateConfiguration setDialogProgressBarColor(int dialogProgressBarColor) {
        this.dialogProgressBarColor = dialogProgressBarColor;
        return this;
    }

    /**
     * 设置内置对话框按钮点击事件监听
     */
    public UpdateConfiguration setButtonClickListener(OnButtonClickListener onButtonClickListener) {
        this.onButtonClickListener = onButtonClickListener;
        return this;
    }

    /**
     * 获取内置对话框按钮点击事件监听
     */
    public OnButtonClickListener getOnButtonClickListener() {
        return onButtonClickListener;
    }
}
