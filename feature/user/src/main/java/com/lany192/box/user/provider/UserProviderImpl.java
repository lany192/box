package com.lany192.box.user.provider;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.lany192.box.router.provider.UserProvider;
import com.lany192.box.user.UserHelper;
import com.lany192.box.user.ui.UserInfoRouter;

@Route(path = "/user/provider")
public class UserProviderImpl implements UserProvider {

    @Override
    public void init(Context context) {

    }

    @Override
    public void startUserInfo() {
        UserInfoRouter.start();
    }

    @Override
    public long getUserId() {
        return UserHelper.getInstance().getUserId();
    }

    @Override
    public String getToken() {
        return UserHelper.getInstance().getToken();
    }

    @Override
    public boolean isLogin() {
        return UserHelper.getInstance().isLogin();
    }
}
