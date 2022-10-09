package com.lany192.box.sample;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.graphics.drawable.Icon;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatDelegate;

import com.alibaba.android.arouter.launcher.ARouter;
import com.github.lany192.arch.BoxApplication;
import com.github.lany192.umeng.UmengConfig;
import com.github.lany192.umeng.UmengUtils;
import com.hjq.toast.ToastUtils;
import com.lany192.box.sample.lifecycle.ActivityLifecycle;
import com.lany192.box.sample.ui.settings.about.AboutActivity;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.HiltAndroidApp;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;

@HiltAndroidApp
public class SampleApp extends BoxApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            initShortcuts();
        }
        initARouter();
        //注册Activity生命周期监听
        registerActivityLifecycleCallbacks(new ActivityLifecycle());
        ToastUtils.setDebugMode(BuildConfig.DEBUG);
        // 处理RxJava返回的无人处理的错误
        RxJavaPlugins.setErrorHandler(new ConsumerAdapter<>());

        UmengConfig config = new UmengConfig();
        config.setAppId("xxxxxxx");
        config.setChannel("umeng");
        config.setMessageSecret("xxxxxxxxxxxxx");
        UmengUtils.preInit(this, config);
    }

    private void initARouter() {
        if (BuildConfig.DEBUG) {
            ARouter.openLog();
            ARouter.openDebug();
        }
        ARouter.init(this);
    }

    @TargetApi(Build.VERSION_CODES.N_MR1)
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initShortcuts() {
        List<ShortcutInfo> shortcuts = new ArrayList<>();
        Intent intent = new Intent(this, AboutActivity.class);
        intent.setAction(Intent.ACTION_VIEW);

        shortcuts.add(new ShortcutInfo.Builder(this, "id" + 0)
                .setShortLabel("搜索")
                .setLongLabel("拿返利找优惠券")
                .setIcon(Icon.createWithResource(this, R.drawable.android))
                .setIntent(intent)
                .build());

        shortcuts.add(new ShortcutInfo.Builder(this, "id" + 1)
                .setShortLabel("搜索2")
                .setLongLabel("拿返利找优惠券2")
                .setIcon(Icon.createWithResource(this, R.drawable.android))
                .setIntent(intent)
                .build());
        ShortcutManager shortcutManager = getSystemService(ShortcutManager.class);
        if (shortcutManager != null) {
            shortcutManager.setDynamicShortcuts(shortcuts);
        }
    }
}
