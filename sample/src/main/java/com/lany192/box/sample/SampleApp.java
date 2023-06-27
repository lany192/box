package com.lany192.box.sample;

import androidx.appcompat.app.AppCompatDelegate;

import com.alibaba.android.arouter.launcher.ARouter;
import com.github.lany192.arch.BoxApplication;
import com.github.lany192.umeng.UmengConfig;
import com.github.lany192.umeng.UmengUtils;
import com.jakewharton.processphoenix.ProcessPhoenix;
import com.lany192.box.router.lifecycle.ActivityLifecycle;
import com.lany192.box.sample.lancet.LancetTest;

import dagger.hilt.android.HiltAndroidApp;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;

@HiltAndroidApp
public class SampleApp extends BoxApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        if (ProcessPhoenix.isPhoenixProcess(this)) {
            return;
        }
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);

        initARouter();
        //注册Activity生命周期监听
        registerActivityLifecycleCallbacks(new ActivityLifecycle());
        // 处理RxJava返回的无人处理的错误
        RxJavaPlugins.setErrorHandler(new ConsumerAdapter<>());

        UmengConfig config = new UmengConfig();
        config.setAppId("xxxxxxx");
        config.setChannel("umeng");
        config.setMessageSecret("xxxxxxxxxxxxx");
        UmengUtils.preInit(this, config);

        ShortcutUtils.init(this);
        LancetTest.test();
    }

    @Override
    public boolean debug() {
        return BuildConfig.DEBUG;
    }

    private void initARouter() {
        if (BuildConfig.DEBUG) {
            ARouter.openLog();
            ARouter.openDebug();
        }
        ARouter.init(this);
    }

}
