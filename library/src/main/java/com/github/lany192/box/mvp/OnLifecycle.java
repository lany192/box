package com.github.lany192.box.mvp;

import androidx.lifecycle.LifecycleObserver;

public interface OnLifecycle extends LifecycleObserver {

    void onCreate();

    void onStart();

    void onResume();

    void onPause();

    void onStop();

    void onDestroy();
}
