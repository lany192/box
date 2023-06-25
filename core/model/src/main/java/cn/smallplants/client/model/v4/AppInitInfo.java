package cn.smallplants.client.model.v4;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import cn.smallplants.client.model.response.Share;

//app初始化配置
public class AppInitInfo implements Serializable {
    //是否挂维护
    @SerializedName("maintenance")
    private boolean maintenance;

    //挂维护理由
    @SerializedName("reason")
    private String reason;

    //分享App信息
    @SerializedName("share")
    private Share share;

    //关于我们h5地址
    @SerializedName("aboutUrl")
    private String aboutUrl;

    //用户协议h5地址
    @SerializedName("agreementUrl")
    private String agreementUrl;

    //隐私协议h5地址
    @SerializedName("privacyUrl")
    private String privacyUrl;

    //商务合作h5地址
    @SerializedName("businessUrl")
    private String businessUrl;

    //是否支持第三方授权
    @SerializedName("supportThirdAuth")
    private boolean supportThirdAuth;

    //用户权限
    @SerializedName("permission")
    private UserPermission permission;

    public boolean isMaintenance() {
        return maintenance;
    }

    public void setMaintenance(boolean maintenance) {
        this.maintenance = maintenance;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Share getShare() {
        return share;
    }

    public void setShare(Share share) {
        this.share = share;
    }

    public String getAboutUrl() {
        return aboutUrl;
    }

    public void setAboutUrl(String aboutUrl) {
        this.aboutUrl = aboutUrl;
    }

    public String getAgreementUrl() {
        return agreementUrl;
    }

    public void setAgreementUrl(String agreementUrl) {
        this.agreementUrl = agreementUrl;
    }

    public String getPrivacyUrl() {
        return privacyUrl;
    }

    public void setPrivacyUrl(String privacyUrl) {
        this.privacyUrl = privacyUrl;
    }

    public String getBusinessUrl() {
        return businessUrl;
    }

    public void setBusinessUrl(String businessUrl) {
        this.businessUrl = businessUrl;
    }

    public boolean isSupportThirdAuth() {
        return supportThirdAuth;
    }

    public void setSupportThirdAuth(boolean supportThirdAuth) {
        this.supportThirdAuth = supportThirdAuth;
    }

    public UserPermission getPermission() {
        return permission;
    }

    public void setPermission(UserPermission permission) {
        this.permission = permission;
    }
}
