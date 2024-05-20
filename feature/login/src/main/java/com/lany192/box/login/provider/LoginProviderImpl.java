package com.lany192.box.login.provider;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.lany192.box.login.ui.LoginRouter;
import com.lany192.box.router.provider.LoginProvider;


@Route(path = "/login/provider")
public class LoginProviderImpl implements LoginProvider {

    @Override
    public void startLogin() {
        LoginRouter.start();
    }

    @Override
    public void init(Context context) {

    }
}
