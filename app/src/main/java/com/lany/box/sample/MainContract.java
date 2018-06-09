package com.lany.box.sample;

import com.lany.box.mvp.view.BaseView;

public interface MainContract {

    interface View extends BaseView {
        void sayHello(String hello);
    }

    interface Presenter {
        void sayClick();
    }

    interface Model {
        String getContent();
    }
}
