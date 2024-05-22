package com.lany192.box.user.provider;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.github.lany192.utils.KVUtils;
import com.lany192.box.router.provider.UserProvider;
import com.lany192.box.user.ui.UserRouter;

@Route(path = "/user/provider")
public class UserProviderImpl implements UserProvider {

    @Override
    public void init(Context context) {

    }

    @Override
    public void startUser(String title, String url) {
        UserRouter.start(title, url);
    }

    @Override
    public String getUserId() {
        return KVUtils.getString("key_user_id");
    }
}
