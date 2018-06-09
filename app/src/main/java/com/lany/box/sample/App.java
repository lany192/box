package com.lany.box.sample;

import com.lany.box.BaseDaggerApp;
import com.lany.box.sample.di.component.DaggerAppComponent;
import com.liulishuo.filedownloader.FileDownloader;

import dagger.android.AndroidInjector;

public class App extends BaseDaggerApp {

    @Override
    public void onCreate() {
        super.onCreate();
        FileDownloader.setupOnApplicationOnCreate(this);
    }

    @Override
    protected AndroidInjector<? extends BaseDaggerApp> applicationInjector() {
        return DaggerAppComponent.builder().create(this);
    }
}
