package com.lany192.box.sample.provider;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.lany192.box.browser.ui.BrowserRouter;
import com.lany192.box.hello.ui.HelloRouter;
import com.lany192.box.login.ui.LoginRouter;
import com.lany192.box.math.ui.MathRouter;
import com.lany192.box.router.provider.RouterProvider;

@Route(path = "/router/provider")
public class RouterProviderImpl implements RouterProvider {

    @Override
    public void init(Context context) {

    }

    @Override
    public void startBrowser(String title, String url) {
        BrowserRouter.start(title, url);
    }

    @Override
    public void startHello() {
        HelloRouter.start();
    }

    @Override
    public void startLogin() {
        LoginRouter.start();
    }

    @Override
    public void startMath() {
        MathRouter.start();
    }
}
