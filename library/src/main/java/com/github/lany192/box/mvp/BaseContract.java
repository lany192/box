package com.github.lany192.box.mvp;


public interface BaseContract {

    interface View extends BaseView {

    }

    interface Presenter {

        void onCreate();

        void onStart();

        void onResume();

        void onPause();

        void onStop();

        void onDestroy();
    }
}
