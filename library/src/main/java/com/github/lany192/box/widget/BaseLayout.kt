package com.github.lany192.box.widget

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.AttrRes
import androidx.annotation.LayoutRes
import androidx.annotation.RequiresApi
import androidx.annotation.StyleRes
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import butterknife.ButterKnife
import com.elvishew.xlog.Logger
import com.elvishew.xlog.XLog

abstract class BaseLayout : FrameLayout, LifecycleObserver {
    protected var log: Logger.Builder = XLog.tag(javaClass.simpleName)

    constructor(context: Context) : super(context) {
        initView(null)
    }

    constructor(
        context: Context,
        attrs: AttributeSet?
    ) : super(context, attrs) {
        initView(attrs)
    }

    constructor(
        context: Context,
        attrs: AttributeSet?,
        @AttrRes defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
        initView(attrs)
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    constructor(
        context: Context,
        attrs: AttributeSet?,
        @AttrRes defStyleAttr: Int,
        @StyleRes defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
        initView(attrs)
    }

    private fun initView(attrs: AttributeSet?) {
        val view = View.inflate(context, layoutId, this)
        ButterKnife.bind(this, view)
        init(attrs)
    }

    @get:LayoutRes
    abstract val layoutId: Int

    abstract fun init(attrs: AttributeSet?)

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        log.i("onCreate()")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart() {
        log.i("onStart()")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        log.i("onResume()")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
        log.i("onPause()")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop() {
        log.i("onStop()")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        log.i("onDestroy()")
    }
}