package cn.smallplants.client.provider;


import com.alibaba.android.arouter.facade.template.IProvider;

import cn.smallplants.client.model.response.UserDetail;
import cn.smallplants.client.model.v4.LoginDetail;

public interface UserProvider extends IProvider {

    void showQRCodeDialog();

    void startCloseAccount();

    void startUserInfo();

    void startNickname(String nickname);

    void startSignature(String signature);

    boolean isLogin();

    void login(LoginDetail token);

    long getUserId();

    String getToken();

    boolean isSelf(long userId);

    void save(UserDetail info);

    UserDetail getUser();

    String getSignature();

    void logout();

    String getNickname();

    String getAvatar();

    long getProvinceId();

    long getAreaId();

    long getCityId();

    String getAddress();

    String getBirthday();

    String getSex();

    int getSexType();

    long getCentCount();

    long getFansCount();

    long getFollowCount();

    long getLikeCount();
}
