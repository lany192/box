package com.lany192.box.sample;

import androidx.appcompat.app.AppCompatDelegate;

import com.alibaba.android.arouter.launcher.ARouter;
import com.github.lany192.arch.BoxApplication;
import com.github.lany192.arch.utils.DeviceId;
import com.github.lany192.utils.KVUtils;
import com.hjq.toast.Toaster;
import com.jakewharton.processphoenix.ProcessPhoenix;
import com.lany192.box.router.lifecycle.ActivityLifecycle;
import com.lany192.box.sample.lancet.LancetTest;

import dagger.hilt.android.HiltAndroidApp;

@HiltAndroidApp
public class SampleApp extends BoxApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        if (ProcessPhoenix.isPhoenixProcess(this)) {
            return;
        }
        //初始化主题
        AppCompatDelegate.setDefaultNightMode(KVUtils.getInt(Constants.KEY_THEME_TYPE, AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM));

        initARouter();
        //注册Activity生命周期监听
        registerActivityLifecycleCallbacks(new ActivityLifecycle());
        // 处理RxJava返回的无人处理的错误
//        RxJavaPlugins.setErrorHandler(new ConsumerAdapter<>());

        ShortcutUtils.init(this);
        LancetTest.test();
        Toaster.show("设备id："+ DeviceId.getInstance().getDeviceId());
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
