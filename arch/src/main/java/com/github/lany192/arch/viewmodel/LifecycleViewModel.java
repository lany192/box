package com.github.lany192.arch.viewmodel;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.elvishew.xlog.Logger;
import com.elvishew.xlog.XLog;
import com.github.lany192.arch.event.NetWorkEvent;
import com.github.lany192.arch.items.ViewState;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class LifecycleViewModel extends ViewModel implements DefaultLifecycleObserver {
    protected Logger.Builder log = XLog.tag(getClass().getName());
    /**
     * 观察界面基础状态
     */
    private final MutableLiveData<ViewState> viewState = new MutableLiveData<>(ViewState.LOADING);
    /**
     * 观察对话框状态
     */
    private final MutableLiveData<Boolean> loadingState = new MutableLiveData<>(false);
    /**
     * 是否执行过懒加载
     */
    private boolean lazyLoaded;

    public MutableLiveData<ViewState> getViewState() {
        return viewState;
    }

    public MutableLiveData<Boolean> getLoadingState() {
        return loadingState;
    }

    public void showViewState(ViewState state) {
        viewState.postValue(state);
    }

    public void showLoadingDialog() {
        if (loadingState.hasActiveObservers()) {
            loadingState.postValue(true);
        } else {
            log.e("没有发现可用的观察者");
        }
    }

    public void cancelLoadingDialog() {
        if (loadingState.hasActiveObservers()) {
            loadingState.postValue(false);
        } else {
            log.e("没有发现可用的观察者");
        }
    }

    public void showContentView() {
        if (viewState.hasActiveObservers()) {
            viewState.postValue(ViewState.CONTENT);
        } else {
            log.e("没有发现可用的观察者");
        }
    }

    public void showErrorView() {
        if (viewState.hasActiveObservers()) {
            viewState.postValue(ViewState.ERROR);
        } else {
            log.e("没有发现可用的观察者");
        }
    }

    public void showLoadingView() {
        if (viewState.hasActiveObservers()) {
            viewState.postValue(ViewState.LOADING);
        } else {
            log.e("没有发现可用的观察者");
        }
    }

    public void showNetworkView() {
        if (viewState.hasActiveObservers()) {
            viewState.postValue(ViewState.NETWORK);
        } else {
            log.e("没有发现可用的观察者");
        }
    }

    public void showEmptyView() {
        if (viewState.hasActiveObservers()) {
            viewState.postValue(ViewState.EMPTY);
        } else {
            log.e("没有发现可用的观察者");
        }
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

    @CallSuper
    @Override
    public void onDestroy(@NonNull LifecycleOwner owner) {
        log.i("onDestroy");
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
}
