package com.github.lany192.box.mvp;


import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

public interface BaseContract {

    interface View extends LifecycleOwner {

        void showLoading();

        void showLoadingDialog();

        void showLoadingDialog(CharSequence message);

        void cancelLoadingDialog();
    }

    interface Presenter extends LifecycleObserver {

    }

    interface Model extends LifecycleObserver {

    }
}
