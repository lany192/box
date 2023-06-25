package cn.smallplants.client.model.v4;

import com.google.gson.annotations.SerializedName;

public class VersionInfo {
    //描述
    @SerializedName("description")
    private String description;
    //是否强制升级
    @SerializedName("forceUpdate")
    private boolean forceUpdate;
    //是否主动提醒
    @SerializedName("important")
    private boolean important;
    //apk安装包的url地址
    @SerializedName("apkUrl")
    private String apkUrl;
    //新版app版本号
    @SerializedName("versionCode")
    private int versionCode;
    //新版app版本名称
    @SerializedName("versionName")
    private String versionName;
    //安装包MD5值
    @SerializedName("apkMd5")
    private String apkMd5;

    public String getDescription() {
        return description;
    }

    public boolean isForceUpdate() {
        return forceUpdate;
    }

    public boolean isImportant() {
        return important;
    }

    public String getApkUrl() {
        return apkUrl;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public String getVersionName() {
        return versionName;
    }
}
