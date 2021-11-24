package com.github.lany192.box.sample.ui.main;

import com.elvishew.xlog.XLog;

import javax.inject.Inject;

public class MainPresenter implements MainContract.Presenter {

    @Inject
    public MainPresenter() {
        XLog.i("MainPresenter执行了");
    }

    @Override
    public void hello() {
        XLog.i("MainPresenter执行了hello");
    }
}
