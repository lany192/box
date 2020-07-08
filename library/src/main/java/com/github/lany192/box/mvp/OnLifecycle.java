package com.github.lany192.box.mvp;

public interface OnLifecycle {

    void onCreate();

    void onStart();

    void onResume();

    void onPause();

    void onStop();

    void onDestroy();
}
