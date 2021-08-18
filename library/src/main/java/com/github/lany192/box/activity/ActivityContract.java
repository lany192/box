package com.github.lany192.box.activity;


import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

import com.github.lany192.box.mvp.BaseContract;

public interface ActivityContract {

    interface View extends BaseContract.View {
        void showEmpty();

        void showEmpty(String msg);

        void showContent();

        void showNoWifi();

        void showError();

        void showError(String msg);
    }

    interface Presenter extends LifecycleObserver {

    }

    interface Model extends LifecycleObserver {

    }
}
