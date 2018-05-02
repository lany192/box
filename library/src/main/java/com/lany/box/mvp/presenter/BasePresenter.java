package com.lany.box.mvp.presenter;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.elvishew.xlog.XLog;
import com.lany.box.mvp.model.BaseModel;

public abstract class BasePresenter implements LifecycleObserver {
    protected final String TAG = this.getClass().getSimpleName();

    public BasePresenter(Fragment view, BaseModel model) {
        view.getLifecycle().addObserver(model);
    }

    public BasePresenter(AppCompatActivity view, BaseModel model) {
        view.getLifecycle().addObserver(model);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onCreate() {
        XLog.tag(TAG).i("onCreate()");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onStart() {
        XLog.tag(TAG).i("onStart()");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume() {
        XLog.tag(TAG).i("onResume()");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPause() {
        XLog.tag(TAG).i("onPause()");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onStop() {
        XLog.tag(TAG).i("onStop()");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy() {
        XLog.tag(TAG).i("onDestroy()");
    }
}
