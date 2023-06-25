package com.lany192.box.hello.provider;

import android.content.Context;
import android.content.Intent;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.lany192.box.hello.ui.HelloActivity;
import com.lany192.box.router.provider.HelloProvider;


@Route(path = "/hello/provider")
public class HelloProviderImpl implements HelloProvider {

    private Context context;

    @Override
    public void init(Context context) {
        this.context = context;
    }


    @Override
    public void startHello() {
        context.startActivity(new Intent(context, HelloActivity.class));
    }
}
