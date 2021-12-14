package com.github.lany192.arch.viewmodel;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;

public class LifecycleViewModel extends BaseViewModel implements DefaultLifecycleObserver {
    private final MutableLiveData<Boolean> loading = new MutableLiveData<>(true);
    /**
     * 是否执行过懒加载
     */
    private boolean lazyLoaded;

    public MutableLiveData<Boolean> getLoading() {
        return loading;
    }

    public void showLoading(boolean show) {
        loading.postValue(show);
    }

    /**
     * 如果需要懒加载，逻辑写在这里,只被调用一次
     */
    protected void onLazyLoad() {
        log.i("懒加载...");
    }

    @CallSuper
    @Override
    public void onResume(@NonNull LifecycleOwner owner) {
        if (!lazyLoaded) {
            lazyLoaded = true;
            onLazyLoad();
        }
        log.i("onResume");
    }

    @Override
    public void onCreate(@NonNull LifecycleOwner owner) {
        log.i("onCreate");
    }

    @Override
    public void onStart(@NonNull LifecycleOwner owner) {
        log.i("onStart");
    }

    @Override
    public void onPause(@NonNull LifecycleOwner owner) {
        log.i("onPause");
    }

    @Override
    public void onStop(@NonNull LifecycleOwner owner) {
        log.i("onStop");
    }

    @Override
    public void onDestroy(@NonNull LifecycleOwner owner) {
        log.i("onDestroy");
    }
}
