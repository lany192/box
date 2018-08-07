package com.lany.box.mvp.model;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;

import com.elvishew.xlog.Logger;
import com.elvishew.xlog.XLog;
import com.lany.box.event.NetWorkEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public abstract class BaseModel implements LifecycleObserver {
    protected final String TAG = this.getClass().getSimpleName();
    protected Logger.Builder log = XLog.tag(TAG);

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onCreate() {
        log.i("onCreate()");
        EventBus.getDefault().register(this);
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
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onEvent(NetWorkEvent event) {
        log.i("onEvent: 网络发生了变化");
    }

}
