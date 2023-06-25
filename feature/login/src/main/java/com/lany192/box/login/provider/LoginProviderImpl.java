package com.lany192.box.login.provider;

import android.content.Context;
import android.content.Intent;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.lany192.box.login.ui.LoginActivity;
import com.lany192.box.router.provider.LoginProvider;


@Route(path = "/login/provider")
public class LoginProviderImpl implements LoginProvider {
    private Context context;

    @Override
    public void init(Context context) {
        this.context = context;
    }

    @Override
    public void startLogin() {
        context.startActivity(new Intent(context, LoginActivity.class));
    }
}
