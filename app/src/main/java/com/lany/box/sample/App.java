package com.lany.box.sample;

import com.lany.box.BaseApp;
import com.liulishuo.filedownloader.FileDownloader;

public class App extends BaseApp {

    @Override
    public void onCreate() {
        super.onCreate();
        FileDownloader.setupOnApplicationOnCreate(this);
    }
}
