package com.github.lany192.arch.viewmodel

import androidx.annotation.CallSuper
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.github.lany192.arch.event.NetWorkEvent
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

open class LifecycleViewModel : BaseViewModel(), DefaultLifecycleObserver {
    /**
     * 是否执行过懒加载
     */
    private var lazyLoaded = false

    @CallSuper
    override fun onCreate(owner: LifecycleOwner) {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this)
        }
    }

    @CallSuper
    override fun onResume(owner: LifecycleOwner) {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this)
        }
        if (!lazyLoaded) {
            lazyLoaded = true
            onLazyLoad()
        }
    }

    @CallSuper
    override fun onDestroy(owner: LifecycleOwner) {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this)
        }
    }

    /**
     * 如果需要懒加载，逻辑写在这里,只被调用一次
     */
    @CallSuper
    protected open fun onLazyLoad() {
        log.i("懒加载...")
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: NetWorkEvent) {

    }
}