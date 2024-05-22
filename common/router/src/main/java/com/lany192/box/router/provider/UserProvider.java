package com.lany192.box.router.provider;


import com.alibaba.android.arouter.facade.template.IProvider;

public interface UserProvider extends IProvider {

    void startUser(String title, String url);
}
