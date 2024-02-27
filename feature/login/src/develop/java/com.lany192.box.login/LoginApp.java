package com.lany192.box.login;

import androidx.appcompat.app.AppCompatDelegate;

import com.alibaba.android.arouter.launcher.ARouter;
import com.github.lany192.arch.BoxApplication;
import com.hjq.toast.Toaster;
import com.jakewharton.processphoenix.ProcessPhoenix;
import com.lany192.box.router.lifecycle.ActivityLifecycle;

import dagger.hilt.android.HiltAndroidApp;

@HiltAndroidApp
public class LoginApp extends BoxApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        if (ProcessPhoenix.isPhoenixProcess(this)) {
            return;
        }
        registerActivityLifecycleCallbacks(new ActivityLifecycle());
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        initARouter();
        Toaster.show("开发模式");
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
