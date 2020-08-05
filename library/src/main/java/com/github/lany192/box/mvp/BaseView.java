package com.github.lany192.box.mvp;

import androidx.lifecycle.LifecycleOwner;

public interface BaseView extends LifecycleOwner {

    void showEmpty();

    void showEmpty(String msg);

    void showContent();

    void showNoWifi();

    void showError();

    void showError(String msg);

    void showLoading();

    void showLoadingDialog();

    void showLoadingDialog(CharSequence message);

    void cancelLoadingDialog();
}
