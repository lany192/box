package com.github.lany192.arch.viewmodel;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;

import com.github.lany192.arch.event.NetWorkEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

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

    @Subscribe
    public void onEvent(NetWorkEvent event) {

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

    @CallSuper
    @Override
    public void onCreate(@NonNull LifecycleOwner owner) {
        log.i("onCreate");
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
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

    @CallSuper
    @Override
    public void onDestroy(@NonNull LifecycleOwner owner) {
        log.i("onDestroy");
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
}
