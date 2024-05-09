package com.lany192.box.math.provider;

import android.content.Context;

import com.alibaba.android.arouter.MathRouter;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.lany192.box.router.provider.MathProvider;


@Route(path = "/math/provider")
public class MathProviderImpl implements MathProvider {

    @Override
    public void init(Context context) {

    }

    @Override
    public void startMath() {
        MathRouter.startMath();
    }
}
