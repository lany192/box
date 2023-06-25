package cn.smallplants.client.model.v4;

import com.google.gson.annotations.SerializedName;

public class AppUpdate {
    //客户端类型
    @SerializedName("client")
    private String client;
    //是否有新版本
    @SerializedName("enable")
    private boolean enable;
    //版本信息
    @SerializedName("info")
    private VersionInfo info;

    public String getClient() {
        return client;
    }

    public boolean isEnable() {
        return enable;
    }

    public VersionInfo getInfo() {
        return info;
    }
}
