package com.lany.box.sample;

import com.lany.box.Box;
import com.lany.box.sample.di.component.DaggerAppComponent;
import com.liulishuo.filedownloader.FileDownloader;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;

public class SampleApp extends DaggerApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        Box.of().init(this);
        FileDownloader.setupOnApplicationOnCreate(this);
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder().create(this);
    }
}
