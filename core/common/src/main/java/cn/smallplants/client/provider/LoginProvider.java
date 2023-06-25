package cn.smallplants.client.provider;


import android.os.Bundle;

import com.alibaba.android.arouter.facade.template.IProvider;

public interface LoginProvider extends IProvider {

    void startLogin();

    void skip(String path, Bundle bundle);
}
