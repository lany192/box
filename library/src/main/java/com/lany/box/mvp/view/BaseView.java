package com.lany.box.mvp.view;

public interface BaseView {

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

    void finish();
}
