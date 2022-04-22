package com.github.lany192.arch.viewmodel

import androidx.annotation.CallSuper
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.elvishew.xlog.Logger
import com.elvishew.xlog.XLog
import com.github.lany192.arch.event.NetWorkEvent
import com.github.lany192.arch.items.ViewState
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

open class LifecycleViewModel : ViewModel(), DefaultLifecycleObserver {
    @JvmField
    protected var log: Logger.Builder = XLog.tag(javaClass.name)

    /**
     * 观察界面基础状态
     */
    val viewState = MutableLiveData(ViewState.CONTENT)

    /**
     * 观察对话框状态
     */
    val loadingState = MutableLiveData(false)

    /**
     * 是否执行过懒加载
     */
    private var lazyLoaded = false

    @CallSuper
    override fun onCreate(owner: LifecycleOwner) {
        log.i("onCreate")
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this)
        }
    }

    @CallSuper
    override fun onResume(owner: LifecycleOwner) {
        log.i("onResume")
        if (!lazyLoaded) {
            lazyLoaded = true
            onLazyLoad()
        }
    }

    @CallSuper
    override fun onDestroy(owner: LifecycleOwner) {
        log.i("onDestroy")
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this)
        }
    }

    fun showLoadingDialog() {
        if (loadingState.hasActiveObservers()) {
            loadingState.postValue(true)
        } else {
            log.e("没有发现可用的观察者")
        }
    }

    fun cancelLoadingDialog() {
        if (loadingState.hasActiveObservers()) {
            loadingState.postValue(false)
        } else {
            log.e("没有发现可用的观察者")
        }
    }

    fun showContentView() {
        if (viewState.hasActiveObservers()) {
            viewState.postValue(ViewState.CONTENT)
        } else {
            log.e("没有发现可用的观察者")
        }
    }

    fun showErrorView() {
        if (viewState.hasActiveObservers()) {
            viewState.postValue(ViewState.ERROR)
        } else {
            log.e("没有发现可用的观察者")
        }
    }

    fun showLoadingView() {
        if (viewState.hasActiveObservers()) {
            viewState.postValue(ViewState.LOADING)
        } else {
            log.e("没有发现可用的观察者")
        }
    }

    fun showNetworkView() {
        if (viewState.hasActiveObservers()) {
            viewState.postValue(ViewState.NETWORK)
        } else {
            log.e("没有发现可用的观察者")
        }
    }

    fun showEmptyView() {
        if (viewState.hasActiveObservers()) {
            viewState.postValue(ViewState.EMPTY)
        } else {
            log.e("没有发现可用的观察者")
        }
    }

    /**
     * 如果需要懒加载，逻辑写在这里,只被调用一次
     */
    @CallSuper
    protected open fun onLazyLoad() {
        log.i("懒加载...")
    }

    @Subscribe
    fun onEvent(event: NetWorkEvent?) {
    }

}