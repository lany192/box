package com.github.lany192.box.mvp;


import androidx.annotation.CallSuper;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;

import com.elvishew.xlog.Logger;
import com.elvishew.xlog.XLog;
import com.github.lany192.box.event.NetWorkEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public abstract class BaseModel implements BaseContract.Model {
    protected Logger.Builder log = XLog.tag(getClass().getSimpleName());

    @CallSuper
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onCreate() {
        log.i("onCreate()");
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @CallSuper
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onStart() {
        log.i("onStart()");
    }

    @CallSuper
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume() {
        log.i("onResume()");
    }

    @CallSuper
    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPause() {
        log.i("onPause()");
    }

    @CallSuper
    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onStop() {
        log.i("onStop()");
    }

    @CallSuper
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy() {
        log.i("onDestroy()");
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(NetWorkEvent event) {
        //log.i("onEvent: 网络发生了变化");
    }

}
