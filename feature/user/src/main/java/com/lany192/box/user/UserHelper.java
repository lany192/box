package com.lany192.box.user;

import android.text.TextUtils;
import android.util.Log;

import com.github.lany192.utils.JsonUtils;
import com.github.lany192.utils.KVUtils;
import com.hjq.toast.Toaster;

public class UserHelper {
    private volatile static UserHelper instance;
    private final String TAG = getClass().getSimpleName();
    private final String KEY_USER_INFO = "user_info";
    private final String KEY_TOKEN = "user_token";

    private UserHelper() {
    }

    public static UserHelper getInstance() {
        if (instance == null) {
            synchronized (UserHelper.class) {
                if (instance == null) {
                    instance = new UserHelper();
                }
            }
        }
        return instance;
    }

    public void login(UserInfo data) {
//        KVUtils.putString(KEY_TOKEN, data.getToken());
//        KVUtils.putInt(KEY_LOGIN_TYPE, data.getType());
//        save(data.getUserInfo());
//        EventBus.getDefault().post(new LoginEvent());
        Toaster.show("登录成功");
    }

    public void save(UserInfo info) {
        if (info == null) {
            return;
        }
        KVUtils.putString(KEY_USER_INFO, JsonUtils.object2json(info));
    }

    public void logout() {
        KVUtils.putString(KEY_USER_INFO, "");
        KVUtils.putString(KEY_TOKEN, "");
//        EventBus.getDefault().post(new LogoutEvent());
    }

    public String getToken() {
//        return KVUtils.getString(KEY_TOKEN);
        return "user_token_test_xxxxxxxxx";
    }

    public UserInfo getUserInfo() {
        String json = KVUtils.getString(KEY_USER_INFO);
        return JsonUtils.json2object(json, UserInfo.class);
    }

    public boolean isLogin() {
        String token = getToken();
        Log.i(TAG, "isLogin: token==" + token + "  uid:" + getUserId());
        return !TextUtils.isEmpty(token) && getUserId() > 0;
    }

    public boolean isSelf(long userId) {
        return userId > 0 && getUserId() == userId;
    }

    public long getUserId() {
        UserInfo info = getUserInfo();
        if (info != null) {
            return info.getUserId();
        } else {
            return 0;
        }
    }

    public String getPhone() {
        UserInfo info = getUserInfo();
        if (info != null) {
            return info.getPhone();
        } else {
            return "";
        }
    }

    public String getAvatar() {
        UserInfo info = getUserInfo();
        if (info != null) {
            return info.getAvatar();
        } else {
            return "";
        }
    }

    public String getBirthday() {
        UserInfo info = getUserInfo();
        if (info != null && !TextUtils.isEmpty(info.getNickname())) {
            return info.getBirthday();
        }
        return "2000-01-01";
    }

    public String getNickname() {
        UserInfo info = getUserInfo();
        if (info != null && !TextUtils.isEmpty(info.getNickname())) {
            return info.getNickname();
        }
        return "";
    }

    public String getSignature() {
        UserInfo info = getUserInfo();
        if (info != null && !TextUtils.isEmpty(info.getSignature())) {
            return info.getSignature();
        }
        return "";
    }
}
