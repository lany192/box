package com.github.lany192.box.mvp

import androidx.lifecycle.LifecycleObserver

interface OnLifecycle : LifecycleObserver {
    fun onCreate()
    fun onStart()
    fun onResume()
    fun onPause()
    fun onStop()
    fun onDestroy()
}