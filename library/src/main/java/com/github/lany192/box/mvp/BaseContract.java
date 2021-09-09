package com.github.lany192.box.mvp;


import androidx.lifecycle.LifecycleObserver;

public interface BaseContract {

    interface View extends BaseView {

    }

    interface Presenter extends LifecycleObserver {

    }

    interface Model extends LifecycleObserver {

    }
}
