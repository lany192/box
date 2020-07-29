package com.github.lany192.box.mvp;

/**
 * fragment或者activity视图基类
 */
public interface PageView extends BaseView {

    void showLoadingDialog();

    void showLoadingDialog(CharSequence message);

    void cancelLoadingDialog();

    void finish();
}
