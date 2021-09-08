package com.github.lany192.box.sample;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.graphics.drawable.Icon;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.github.lany192.box.Box;
import com.github.lany192.box.sample.activity.HelloActivity;
import com.github.lany192.box.sample.di.component.DaggerAppComponent;
import com.liulishuo.filedownloader.FileDownloader;

import java.util.ArrayList;
import java.util.List;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;

public class SampleApp extends DaggerApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        Box.get().init(this, BuildConfig.DEBUG);
        FileDownloader.setupOnApplicationOnCreate(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            initShortcuts();
        }
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder().application(this).build();
    }

    @TargetApi(Build.VERSION_CODES.N_MR1)
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initShortcuts() {
        List<ShortcutInfo> shortcuts = new ArrayList<>();
        Intent intent = new Intent(this, HelloActivity.class);
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
