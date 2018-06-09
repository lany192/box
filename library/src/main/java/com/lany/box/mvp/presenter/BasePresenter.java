package com.lany.box.mvp.presenter;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.elvishew.xlog.Logger;
import com.elvishew.xlog.XLog;
import com.lany.box.mvp.model.BaseModel;
import com.lany.box.mvp.view.BaseView;

public abstract class BasePresenter<V extends BaseView, M extends BaseModel> implements LifecycleObserver {
    protected final String TAG = this.getClass().getSimpleName();
    protected Logger.Builder log = XLog.tag(TAG);
    private V mView;
    private M mModel;

    public BasePresenter(V view, M model) {
        this.mView = view;
        this.mModel = model;
        if (view instanceof Fragment) {
            ((Fragment) view).getLifecycle().addObserver(model);
        } else if (view instanceof AppCompatActivity) {
            ((AppCompatActivity) view).getLifecycle().addObserver(model);
        } else {
            throw new IllegalArgumentException("The view must be an instance of Fragment or AppCompatActivity");
        }
    }

    public V getView() {
        return mView;
    }

    public M getModel() {
        return mModel;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onCreate() {
        log.i("onCreate()");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onStart() {
        log.i("onStart()");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume() {
        log.i("onResume()");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPause() {
        log.i("onPause()");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onStop() {
        log.i("onStop()");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy() {
        log.i("onDestroy()");
    }
}
