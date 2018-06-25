package com.lany.box.sample;

import com.lany.box.DaggerApp;
import com.lany.box.sample.di.component.DaggerAppComponent;
import com.liulishuo.filedownloader.FileDownloader;

import dagger.android.AndroidInjector;

public class SampleApp extends DaggerApp {

    @Override
    public void onCreate() {
        super.onCreate();
        FileDownloader.setupOnApplicationOnCreate(this);
    }

    @Override
    protected AndroidInjector<? extends DaggerApp> applicationInjector() {
        return DaggerAppComponent.builder().create(this);
    }
}
