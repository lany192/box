package cn.smallplants.client.model.v4;

import com.google.gson.annotations.SerializedName;

import cn.smallplants.client.model.response.UserDetail;

public class LoginDetail {
    //用户信息
    @SerializedName("user")
    UserDetail user;

    //登录类型
    @SerializedName("type")
    int type;

    //登录token
    @SerializedName("token")
    String token;

    //是否第一次登录/注册
    @SerializedName("firstLogin")
    boolean firstLogin;

    public UserDetail getUser() {
        return user;
    }

    public void setUser(UserDetail user) {
        this.user = user;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isFirstLogin() {
        return firstLogin;
    }

    public void setFirstLogin(boolean firstLogin) {
        this.firstLogin = firstLogin;
    }
}
