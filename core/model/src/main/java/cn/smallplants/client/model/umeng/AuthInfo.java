package cn.smallplants.client.model.umeng;

import com.google.gson.annotations.SerializedName;


public class AuthInfo {
    @SerializedName("openid")
    String openId;
    @SerializedName("access_token")
    String accessToken;

    @SerializedName("country")
    String country;
    @SerializedName("unionid")
    String unionid;
    @SerializedName("gender")
    String gender;
    @SerializedName("city")
    String city;
    @SerializedName("language")
    String language;
    @SerializedName("profile_image_url")
    String profile_image_url;
    @SerializedName("uid")
    String uid;
    @SerializedName("province")
    String province;
    @SerializedName("screen_name")
    String screen_name;
    @SerializedName("name")
    String name;
    @SerializedName("iconurl")
    String iconurl;
    @SerializedName("expiration")
    String expiration;
    @SerializedName("expires_in")
    String expires_in;
    @SerializedName("refreshToken")
    String refreshToken;

    public String getOpenId() {
        return openId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getCountry() {
        return country;
    }

    public String getUnionid() {
        return unionid;
    }

    public String getGender() {
        return gender;
    }

    public String getCity() {
        return city;
    }

    public String getLanguage() {
        return language;
    }

    public String getProfile_image_url() {
        return profile_image_url;
    }

    public String getUid() {
        return uid;
    }

    public String getProvince() {
        return province;
    }

    public String getScreen_name() {
        return screen_name;
    }

    public String getName() {
        return name;
    }

    public String getIconurl() {
        return iconurl;
    }

    public String getExpiration() {
        return expiration;
    }

    public String getExpires_in() {
        return expires_in;
    }

    public String getRefreshToken() {
        return refreshToken;
    }
}
