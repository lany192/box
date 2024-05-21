package com.lany192.box.user.provider;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.lany192.box.router.provider.BrowserProvider;

@Route(path = "/user/provider")
public class UserProviderImpl implements BrowserProvider {

    @Override
    public void init(Context context) {

    }

    @Override
    public void startBrowser(String title, String url) {
        BrowserRouter.start(title, url);
    }
}
