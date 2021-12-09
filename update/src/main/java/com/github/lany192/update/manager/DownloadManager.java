package com.github.lany192.update.manager;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.Toast;

import com.elvishew.xlog.Logger;
import com.elvishew.xlog.XLog;
import com.github.lany192.update.R;
import com.github.lany192.update.base.BaseHttpDownloadManager;
import com.github.lany192.update.config.UpdateConfig;
import com.github.lany192.update.dialog.AppUpdateDialog;
import com.github.lany192.update.service.DownloadService;
import com.github.lany192.update.utils.ApkUtil;
import com.github.lany192.update.utils.Constant;

import java.lang.ref.SoftReference;

public class DownloadManager {
    /**
     * 上下文
     */
    private static SoftReference<Context> context;
    private static DownloadManager manager;
    private final Logger.Builder log = XLog.tag(getClass().getSimpleName());
    /**
     * 要更新apk的下载地址
     */
    private String apkUrl = "";
    /**
     * apk下载好的名字 .apk 结尾
     */
    private String apkName = "";
    /**
     * apk 下载存放的位置
     */
    private String downloadPath;
    /**
     * 是否提示用户 "当前已是最新版本"
     * <p>
     * {@link #download()}
     */
    private boolean showNewerToast = false;
    /**
     * 通知栏的图标 资源路径
     */
    private int smallIcon = -1;
    /**
     * 整个库的一些配置属性，可以从这里配置
     */
    private UpdateConfig configuration;
    /**
     * 要更新apk的versionCode
     */
    private int apkVersionCode = Integer.MIN_VALUE;
    /**
     * 显示给用户的版本号
     */
    private String apkVersionName = "";
    /**
     * 更新描述
     */
    private String apkDescription = "";
    /**
     * 安装包大小 单位 M
     */
    private String apkSize = "";
    /**
     * 新安装包md5文件校验（32位)，校验重复下载
     */
    private String apkMD5 = "";
    /**
     * 当前下载状态
     */
    private boolean state = false;
    /**
     * 内置对话框
     */
    private AppUpdateDialog dialog;

    public static DownloadManager getInstance(Context context) {
        DownloadManager.context = new SoftReference<>(context);
        if (manager == null) {
            synchronized (DownloadManager.class) {
                if (manager == null) {
                    manager = new DownloadManager();
                }
            }
        }
        return manager;
    }

    /**
     * 供此依赖库自己使用.
     *
     * @return {@link DownloadManager}
     * @hide
     */
    public static DownloadManager getInstance() {
        return manager;
    }

    /**
     * 获取apk下载地址
     */
    public String getApkUrl() {
        return apkUrl;
    }

    /**
     * 设置apk下载地址
     */
    public DownloadManager setApkUrl(String apkUrl) {
        this.apkUrl = apkUrl;
        return this;
    }

    /**
     * 获取apk的VersionCode
     */
    public int getApkVersionCode() {
        return apkVersionCode;
    }

    /**
     * 设置apk的VersionCode
     */
    public DownloadManager setApkVersionCode(int apkVersionCode) {
        this.apkVersionCode = apkVersionCode;
        return this;
    }

    /**
     * 获取apk的名称
     */
    public String getApkName() {
        return apkName;
    }

    /**
     * 设置apk的名称
     */
    public DownloadManager setApkName(String apkName) {
        this.apkName = apkName;
        return this;
    }

    /**
     * 获取apk的保存路径
     */
    public String getDownloadPath() {
        return downloadPath;
    }

    /**
     * 设置apk的保存路径
     * 由于Android Q版本限制应用访问外部存储目录，所以不再支持设置存储目录
     * 使用的路径为:/storage/emulated/0/Android/data/ your packageName /cache
     */
    @Deprecated
    public DownloadManager setDownloadPath(String downloadPath) {
        return this;
    }

    /**
     * 获取是否提示用户"当前已是最新版本"
     */
    public boolean isShowNewerToast() {
        return showNewerToast;
    }

    /**
     * 设置是否提示用户"当前已是最新版本"
     */
    public DownloadManager setShowNewerToast(boolean showNewerToast) {
        this.showNewerToast = showNewerToast;
        return this;
    }

    /**
     * 获取通知栏图片资源id
     */
    public int getSmallIcon() {
        return smallIcon;
    }

    /**
     * 设置通知栏图片资源id
     */
    public DownloadManager setSmallIcon(int smallIcon) {
        this.smallIcon = smallIcon;
        return this;
    }

    /**
     * 获取这个库的额外配置信息
     *
     * @see UpdateConfig
     */
    public UpdateConfig getConfiguration() {
        return configuration;
    }

    /**
     * 设置这个库的额外配置信息
     *
     * @see UpdateConfig
     */
    public DownloadManager setConfiguration(UpdateConfig configuration) {
        this.configuration = configuration;
        return this;
    }

    /**
     * 获取apk的versionName
     */
    public String getApkVersionName() {
        return apkVersionName;
    }

    /**
     * 设置apk的versionName
     */
    public DownloadManager setApkVersionName(String apkVersionName) {
        this.apkVersionName = apkVersionName;
        return this;
    }

    /**
     * 获取新版本描述信息
     */
    public String getApkDescription() {
        return apkDescription;
    }

    /**
     * 设置新版本描述信息
     */
    public DownloadManager setApkDescription(String apkDescription) {
        this.apkDescription = apkDescription;
        return this;
    }

    /**
     * 获取新版本文件大小
     */
    public String getApkSize() {
        return apkSize;
    }

    /**
     * 设置新版本文件大小
     */
    public DownloadManager setApkSize(String apkSize) {
        this.apkSize = apkSize;
        return this;
    }

    /**
     * 新安装包md5文件校验
     */
    public String getApkMD5() {
        return apkMD5;
    }

    /**
     * 新安装包md5文件校验
     */
    public DownloadManager setApkMD5(String apkMD5) {
        this.apkMD5 = apkMD5;
        return this;
    }

    /**
     * 设置当前状态
     *
     * @hide
     */
    public void setState(boolean state) {
        this.state = state;
    }

    /**
     * 当前是否正在下载
     */
    public boolean isDownloading() {
        return state;
    }

    /**
     * 获取内置对话框
     */
    public AppUpdateDialog getDefaultDialog() {
        return dialog;
    }

    /**
     * 开始下载
     */
    public void download() {
        if (!checkParams()) {
            //参数设置出错....
            return;
        }
        if (checkVersionCode()) {
            context.get().startService(new Intent(context.get(), DownloadService.class));
        } else {
            //对版本进行判断，是否显示升级对话框
            if (apkVersionCode > ApkUtil.getVersionCode(context.get())) {
                dialog = new AppUpdateDialog();
                dialog.show(context.get());
            } else {
                if (showNewerToast) {
                    Toast.makeText(context.get(), R.string.latest_version, Toast.LENGTH_SHORT).show();
                }
                log.e("当前已是最新版本");
            }
        }
    }

    /**
     * 取消下载
     */
    public void cancel() {
        if (configuration == null) {
            log.e("还未开始下载");
            return;
        }
        BaseHttpDownloadManager httpManager = configuration.getHttpManager();
        if (httpManager == null) {
            log.e("还未开始下载");
            return;
        }
        httpManager.cancel();
    }

    /**
     * 检查参数
     */
    private boolean checkParams() {
        if (TextUtils.isEmpty(apkUrl)) {
            log.e("apkUrl can not be empty!");
            return false;
        }
        if (TextUtils.isEmpty(apkName)) {
            log.e("apkName can not be empty!");
            return false;
        }
        if (!apkName.endsWith(Constant.APK_SUFFIX)) {
            log.e("apkName must endsWith .apk!");
            return false;
        }
        downloadPath = context.get().getExternalCacheDir().getPath();
        if (smallIcon == -1) {
            log.e("smallIcon can not be empty!");
            return false;
        }
        //加载用户设置的authorities
        Constant.AUTHORITIES = context.get().getPackageName() + ".fileProvider";
        //如果用户没有进行配置，则使用默认的配置
        if (configuration == null) {
            configuration = new UpdateConfig();
        }
        return true;
    }

    /**
     * 检查设置的{@link this#apkVersionCode} 如果不是默认值则使用内置的对话框
     * 如果是默认值{@link Integer#MIN_VALUE}直接启动服务下载
     */
    private boolean checkVersionCode() {
        if (apkVersionCode == Integer.MIN_VALUE) {
            return true;
        }
        //设置了 VersionCode 则库中进行对话框逻辑处理
        if (TextUtils.isEmpty(apkDescription)) {
            log.e("apkDescription can not be empty!");
        }
        return false;
    }

    /**
     * 释放资源
     */
    public void release() {
        context.clear();
        context = null;
        manager = null;
        if (configuration != null) {
            configuration.getOnDownloadListener().clear();
        }
    }
}
