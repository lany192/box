package com.lany192.box.hello.provider;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.lany192.box.hello.ui.HelloRouter;
import com.lany192.box.router.provider.HelloProvider;


@Route(path = "/hello/provider")
public class HelloProviderImpl implements HelloProvider {


    @Override
    public void init(Context context) {
    }


    @Override
    public void startHello() {
        HelloRouter.start();
    }
}
