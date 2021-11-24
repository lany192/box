package com.github.lany192.box.sample.ui.main;

import com.github.lany192.box.mvp.BaseView;

public interface MainContract {

    interface View extends BaseView {
    }

    interface Presenter {
        void hello();
    }
}
