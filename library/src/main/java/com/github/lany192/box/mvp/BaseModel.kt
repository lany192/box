package com.github.lany192.box.mvp

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import com.elvishew.xlog.Logger
import com.elvishew.xlog.XLog
import com.github.lany192.box.event.NetWorkEvent
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

abstract class BaseModel : BaseContract.Model {
    protected var log: Logger.Builder = XLog.tag(javaClass.simpleName)

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    override fun onCreate() {
        log.i("onCreate()")
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this)
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    override fun onStart() {
        log.i("onStart()")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    override fun onResume() {
        log.i("onResume()")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    override fun onPause() {
        log.i("onPause()")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    override fun onStop() {
        log.i("onStop()")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    override fun onDestroy() {
        log.i("onDestroy()")
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this)
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: NetWorkEvent?) {
        //log.i("onEvent: 网络发生了变化");
    }
}