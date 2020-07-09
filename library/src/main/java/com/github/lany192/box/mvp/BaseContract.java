package com.github.lany192.box.mvp;


public interface BaseContract {

    interface View extends BaseView, OnLifecycle {

    }

    interface Presenter extends OnLifecycle {

    }
}
