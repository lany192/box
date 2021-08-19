package com.github.lany192.box.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

import com.elvishew.xlog.Logger;
import com.elvishew.xlog.XLog;

public abstract class BaseLayout extends FrameLayout implements LifecycleObserver {
    protected final String TAG = this.getClass().getSimpleName();
    protected Logger.Builder log = XLog.tag(TAG);

    public BaseLayout(@NonNull Context context) {
        this(context, null, 0);
    }

    public BaseLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View view = View.inflate(getContext(), getLayoutId(), this);
        init(attrs);
    }

    @LayoutRes
    public abstract int getLayoutId();

    public abstract void init(@Nullable AttributeSet attrs);

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
