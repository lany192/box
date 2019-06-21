package com.github.lany192.box.sample;

import com.github.lany192.box.Box;
import com.github.lany192.box.sample.di.component.DaggerAppComponent;
import com.liulishuo.filedownloader.FileDownloader;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;

public class SampleApp extends DaggerApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        Box.of().init(this, BuildConfig.DEBUG);
        FileDownloader.setupOnApplicationOnCreate(this);
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder().create(this);
    }
}
