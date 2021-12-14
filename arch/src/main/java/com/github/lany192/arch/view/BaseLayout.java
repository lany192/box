package com.github.lany192.arch.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

import com.elvishew.xlog.Logger;
import com.elvishew.xlog.XLog;

public abstract class BaseLayout extends FrameLayout implements DefaultLifecycleObserver {
    protected Logger.Builder log = XLog.tag(getClass().getSimpleName());

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

    @Override
    public void onCreate(@NonNull LifecycleOwner owner) {
        log.i("onCreate()");
    }

    @Override
    public void onStart(@NonNull LifecycleOwner owner) {
        log.i("onStart()");
    }

    @Override
    public void onResume(@NonNull LifecycleOwner owner) {
        log.i("onResume()");
    }

    @Override
    public void onPause(@NonNull LifecycleOwner owner) {
        log.i("onPause()");
    }

    @Override
    public void onStop(@NonNull LifecycleOwner owner) {
        log.i("onStop()");
    }

    @Override
    public void onDestroy(@NonNull LifecycleOwner owner) {
        log.i("onDestroy()");
    }
}
