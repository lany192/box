package com.github.lany192.box.mvp;


public interface BaseContract {

    interface View extends BaseView {

    }

    interface Presenter extends OnLifecycle {

    }

    interface Model extends OnLifecycle {

    }
}
