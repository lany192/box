package com.lany.box.sample;

import com.lany.box.BaseApp;
import com.lany.box.sample.di.component.DaggerAppComponent;
import com.liulishuo.filedownloader.FileDownloader;

import dagger.android.AndroidInjector;

public class SampleApp extends BaseApp {

    @Override
    public void onCreate() {
        super.onCreate();
        FileDownloader.setupOnApplicationOnCreate(this);
    }

    @Override
    protected AndroidInjector<? extends BaseApp> applicationInjector() {
        return DaggerAppComponent.builder().create(this);
    }
}
